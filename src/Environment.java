import processing.core.PApplet;
import processing.core.PVector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class Environment {
    private PApplet p;
    private Display d = new Display();
    private StatChange sc;
    private AI ai;
    private otherAI leftAI;
    static boolean pause = false;
    //private PVector mouse;
    private Stopwatch sw = new Stopwatch();
    private Stopwatch pauseSW = new Stopwatch();
    private Stopwatch waitTimer = new Stopwatch();
    private int minionSpawnRate = Stats.getMinionSpawnRate();
    private boolean leftMinionsDead = true;
    private boolean rightMinionsDead = true;

    private static ArrayList<Minion> playerMinions = new ArrayList<>();
    private static ArrayList<Minion> aiMinions = new ArrayList<>();
    private static ArrayList<Tower> playerTowers = new ArrayList<>();
    private static ArrayList<Tower> aiTowers = new ArrayList<>();

    private static Headquarters playerHQ;
    private static Headquarters aiHQ;
    //static ArrayList<Point> topLanePoints = new ArrayList<>();
    //private static ArrayList<Projectile> projectiles = new ArrayList<>();
    private static ArrayList<Projectile> playerProjectiles = new ArrayList<>();
    private static ArrayList<Projectile> aiProjectiles = new ArrayList<>();

    private ArrayList<PVector> topLanePoints= new ArrayList<>();
    private ArrayList<PVector> midLanePoints= new ArrayList<>();
    private ArrayList<PVector> btmLanePoints= new ArrayList<>();

    private boolean leftAISet = false;

    ArrayList<Simulation> sims = new ArrayList<>();
    Minion bestMinion;


    void setup(PApplet p) {
        this.p = p;
        sc = new StatChange(p);
        ai = new AI(p);
        ai.assignPoints();


        buildTopLane();
        buildMidLane();
        buildBtmLane();
        //buildHeroes();
        //buildMinions();
        buildTowers();
        buildHQ();
    }

    void reset() {
        playerProjectiles.clear();
        aiProjectiles.clear();
        topLanePoints.clear();
        midLanePoints.clear();
        btmLanePoints.clear();
        playerMinions.clear();
        aiMinions.clear();
        playerTowers.clear();
        aiTowers.clear();
        leftAISet = false;
        StatChange.resetPoints();
        playerHQ = null;
        aiHQ = null;
        ai = null;
        sw.reset();
    }

    void tick() {
        checkPause();
        if(pause){
            for(Simulation sim: sims) {
                if(sim.simFinished()) {
                    if(bestMinion == null || sim.getBestMinion().getDamageDealt() > bestMinion.getDamageDealt()) {
                        bestMinion = sim.getBestMinion();
                        System.out.println(bestMinion.getHealth() + " " + bestMinion.getSpeed() + " " + bestMinion.getRange() + " " + bestMinion.getDamage() + " " + bestMinion.getAtkSpeed());
                    }
                }
                if(Methods.areAllTrue(sims)) {
                    Stats.setAiMinionHealthValue(bestMinion.getHealth());
                    Stats.setAiMinionSpeedValue(bestMinion.getSpeed());
                    Stats.setAiMinionRangeValue(bestMinion.getRange());
                    Stats.setAiMinionDamageValue(bestMinion.getDamage());
                    Stats.setAiMinionAtkSpeedValue(bestMinion.getAtkSpeed());
                    unpause();
                } else {
                    sim.tick();
                }

            }
        }
        if(!pause) {


            if (!Settings.aiVai) {
                sc.tick();
            }
            setLeftAI();
            if (playerHQ.checkDead()) {
                Stats.setAiWon(true);
                System.out.println("AI Wins");
            } else {
                playerHQ.tick();
            }

            if (aiHQ.checkDead()) {
                Stats.setPlayerWon(true);
                System.out.println("Player Wins");
            } else {
                aiHQ.tick();
            }
            if (Settings.display) {
                d.drawFrameRate(p);
                //p.background(0);
                d.drawLanes(p);
                d.drawStats(p);
                if (!playerHQ.checkDead()) {
                    playerHQ.drawHQ();
                }
                if (!aiHQ.checkDead()) {
                    aiHQ.drawHQ();
                }
                for (Tower t : playerTowers) {
                    t.drawTower();
                }
                for (Tower t : aiTowers) {
                    t.drawTower();
                }
                for (Minion m : playerMinions) {
                    m.drawMinion();
                }
                for (Minion m : aiMinions) {
                    m.drawMinion();
                }
                for (PVector pos : topLanePoints) {
                    d.drawWaypoint(p, pos);
                }
                for (PVector pos : midLanePoints) {
                    d.drawWaypoint(p, pos);
                }
                for (PVector pos : btmLanePoints) {
                    d.drawWaypoint(p, pos);
                }
                for (Projectile pro : playerProjectiles) {
                    pro.drawProjectile(p);
                }
                for (Projectile pro : aiProjectiles) {
                    pro.drawProjectile(p);
                }
                d.drawSkillBar(p);

            }


//            if (sw.elapsedTime() >= minionSpawnRate && waitTimer.elapsedTime() >= 5f) {
//                buildMinions();
//                sw.reset();
//            }
            if (waitTimer.elapsedTime() >= 5f && leftMinionsDead && rightMinionsDead) {
                buildMinions();
                //sw.reset();
            }
            for (Projectile pro : playerProjectiles) {
                if (!pro.projectileAlive()) {
                    playerProjectiles.remove(pro);
                    return;
                } else {
                    pro.tick(p);
                }
            }
            for (Projectile pro : aiProjectiles) {
                if (!pro.projectileAlive()) {
                    aiProjectiles.remove(pro);
                    return;
                } else {
                    pro.tick(p);
                }
            }

            for (Tower t : playerTowers) {
                if (t.checkDead()) {
                    playerTowers.remove(t);
                    return;
                }
                t.tick();
            }
            for (Tower t : aiTowers) {
                if (t.checkDead()) {
                    aiTowers.remove(t);
                    return;
                }
                t.tick();
            }
            if(playerMinions.isEmpty()) {
                leftMinionsDead = true;
            }
            if(aiMinions.isEmpty()) {
                rightMinionsDead = true;
            }

            for (Minion m : playerMinions) {

                if (m.checkDead()) {
                    playerMinions.remove(m);
                    return;
                }
                //m.drawMinion();
                m.tick(p);
            }
            for (Minion m : aiMinions) {

                if (m.checkDead()) {
                    if (ai.testMinion(m)) {
                        int health = (int) deepClone(m.getHealth());
                        int speed = (int) deepClone(m.getSpeed());
                        int range = (int) deepClone(m.getRange());
                        int damage = (int) deepClone(m.getDamage());
                        float atkSpeed = (float) deepClone(m.getAtkSpeed());
                        ai.saveMinion(p, health, speed, range, damage, atkSpeed);
                        ai.modStats();
                    }
                    aiMinions.remove(m);
                    return;
                }
                //m.drawMinion();
                m.tick(p);
            }
        }

    }

    void buildSimulation() {

        for (int counter = 0; counter <= 4 ; counter++) {
//            ArrayList<Tower> leftTowers = new ArrayList<>();
//            ArrayList<Tower> rightTowers = new ArrayList<>();
//            for (Tower t: playerTowers) {
//                leftTowers.add(copyTower(p, t.getPos(), t.getCurrentHealth(), t.getPlayer()));
//            }
//            for (Tower t: aiTowers) {
//                rightTowers.add(copyTower(p, t.getPos(), t.getCurrentHealth(), t.getPlayer()));
//            }
            Headquarters leftHQ = copyHQ(p, playerHQ.getPos(), playerHQ.getCurrentHealth(), playerHQ.getPlayer());
            Headquarters rightHQ = copyHQ(p, aiHQ.getPos(), aiHQ.getCurrentHealth(), aiHQ.getPlayer());
            sims.add(new Simulation(p, playerTowers, aiTowers, leftHQ, rightHQ, topLanePoints, midLanePoints, btmLanePoints));
        }

    }

    void checkPause() {
        if(p.keyPressed && pauseSW.elapsedTime() >= 0.2f) {
            if(p.key == 'l' || p.key == 'L') {
                if (pause) {
                    pause = false;
                } else {
                    pause = true;
                    buildSimulation();
                }
                pauseSW.reset();
            }
        }
    }

    private void setLeftAI() {
        if(Settings.aiVai && !leftAISet) {
            leftAI = new otherAI(p);
            leftAI.assignPoints();
            leftAISet = true;
        }

    }

//    private void buildHeroes() {
//        heroes.add(new Hero(p, new PVector(100, p.height/2), topLanePoints));
//        heroes.add(new Hero(p, new PVector(100, p.height/2), midLanePoints));
//        heroes.add(new Hero(p, new PVector(100, p.height/2), btmLanePoints));
//    }

    private void buildMinions() {
        //Spawn player minions
        playerMinions.add(new Minion(p, new PVector(100, p.height/2f), topLanePoints, true));
        playerMinions.add(new Minion(p, new PVector(100, p.height/2f), midLanePoints, true));
        playerMinions.add(new Minion(p, new PVector(100, p.height/2f), btmLanePoints, true));
        leftMinionsDead = false;
        //Spawn AI minions
        aiMinions.add(new Minion(p, new PVector(p.width - 130, p.height/2f), topLanePoints, false));
        aiMinions.add(new Minion(p, new PVector(p.width - 100, p.height/2f), midLanePoints, false));
        aiMinions.add(new Minion(p, new PVector(p.width - 130, p.height/2f), btmLanePoints, false));
        rightMinionsDead = false;
    }

    private void buildTowers() {
        //playerTowers.add(new Tower(p, new PVector(p.width*0.5f, p.height*0.1f), 10,10,50,2f));
        //aiTowers.add(new Tower(p, new PVector(p.width*0.5f, p.height*0.1f), 10,10,70,1f));
        //aiTowers.add(new Tower(p, new PVector(p.width*0.5f, p.height*0.5f), 10,10,70,1f));
        //aiTowers.add(new Tower(p, new PVector(p.width*0.5f, p.height*0.9f), 10,10,70,1f));

        playerTowers.add(new Tower(p, new PVector(p.width*0.1f, p.height*0.15f), true));
        playerTowers.add(new Tower(p, new PVector(p.width*0.3f, p.height*0.15f), true));
        playerTowers.add(new Tower(p, new PVector(p.width*0.2f, p.height*0.5f), true));
        playerTowers.add(new Tower(p, new PVector(p.width*0.4f, p.height*0.5f), true));
        playerTowers.add(new Tower(p, new PVector(p.width*0.1f, p.height*0.85f), true));
        playerTowers.add(new Tower(p, new PVector(p.width*0.3f, p.height*0.85f), true));

        aiTowers.add(new Tower(p, new PVector(p.width*0.9f, p.height*0.15f), false));
        aiTowers.add(new Tower(p, new PVector(p.width*0.7f, p.height*0.15f), false));
        aiTowers.add(new Tower(p, new PVector(p.width*0.8f, p.height*0.5f), false));
        aiTowers.add(new Tower(p, new PVector(p.width*0.6f, p.height*0.5f), false));
        aiTowers.add(new Tower(p, new PVector(p.width*0.9f, p.height*0.85f), false));
        aiTowers.add(new Tower(p, new PVector(p.width*0.7f, p.height*0.85f), false));

    }

    private void buildHQ() {
        playerHQ = new Headquarters(p, new PVector(p.width*0.1f, p.height*0.5f), true);
        aiHQ = new Headquarters(p, new PVector(p.width*0.9f, p.height*0.5f), false);
    }

    private void buildTopLane() {
        topLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f - 10));
        topLanePoints.add(new PVector(p.width*0.1f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.25f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.5f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.75f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.9f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f - 10));
    }

    private void buildMidLane() {
        midLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.25f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.5f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.75f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f));
    }

    private void buildBtmLane() {
        btmLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f + 10));
        btmLanePoints.add(new PVector(p.width*0.1f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.25f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.5f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.75f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.9f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f + 10));
    }

//    private void mouse() {
//        mouse = new PVector(p.mouseX, p.mouseY);
//        d.drawMouse(p, mouse);
//    }

    static void addAiProjectile(PVector position, PVector velocity, int damage) {
        PVector bPos = (PVector) deepClone(position);
        PVector bVel = (PVector) deepClone(velocity);
        aiProjectiles.add(new Projectile(bPos, bVel, damage));
    }

    static void addPlayerProjectile(PVector position, PVector velocity, int damage) {
        PVector bPos = (PVector) deepClone(position);
        PVector bVel = (PVector) deepClone(velocity);
        playerProjectiles.add(new Projectile(bPos, bVel, damage));
    }

//    Tower copyTower(PApplet p, PVector pos, int currentHealth, boolean player) {
//        PVector bPos = (PVector) deepClone(pos);
//        int bHealth = (int) deepClone(currentHealth);
//        boolean bPlayer = (boolean) deepClone(player);
//        return new Tower(p, bPos, bPlayer, bHealth);
//    }

    Headquarters copyHQ(PApplet p, PVector pos, int currentHealth, boolean player) {
        PVector bPos = (PVector) deepClone(pos);
        boolean bPlayer = (boolean) deepClone(player);
        int bHealth = (int) deepClone(currentHealth);
        return new Headquarters(p, bPos, bPlayer, bHealth);
    }

    static ArrayList<Minion> getPlayerMinions() {return playerMinions;}

    static ArrayList<Minion> getAiMinions() {return aiMinions;}

    static  ArrayList<Projectile> getPlayerProjectiles() {return playerProjectiles;}

    static ArrayList<Projectile> getAiProjectiles() {return  aiProjectiles;}

    static ArrayList<Tower> getPlayerTowers() {return playerTowers;}

    static ArrayList<Tower> getAiTowers() {return aiTowers;}

    static Headquarters getPlayerHQ() {return playerHQ;}

    static Headquarters getAiHQ() {return aiHQ;}

    private static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void unpause() {
        pause = false;
    }
}
