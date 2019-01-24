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
    //private PVector mouse;
    private Stopwatch sw = new Stopwatch();
    private int minionSpawnRate = Stats.getMinionSpawnRate();
    //protected static ArrayList<Hero> heroes = new ArrayList<>();
    //static ArrayList<Minion> minions = new ArrayList<>();

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


    void setup(PApplet p) {
        this.p = p;
        sc = new StatChange(p);
        buildTopLane();
        buildMidLane();
        buildBtmLane();
        //buildHeroes();
        buildMinions();
        buildTowers();
        buildHQ();

    }

    void tick() {
        d.drawLanes(p);
        d.drawStats(p);
        sc.tick();
        if(playerHQ.checkDead()) {
            Stats.setAiWon(true);
            System.out.println("AI Wins");
        } else {
            playerHQ.tick();
        }

        if(aiHQ.checkDead()) {
            Stats.setPlayerWon(true);
            System.out.println("Player Wins");
        } else {
            aiHQ.tick();
        }
//        mouse();
//        for (Hero h : heroes) {
//            if (h.checkDead()) {
//                heroes.remove(h);
//                return;
//            }
//            h.tick(p, mouse);
//        }

        for (Minion m : playerMinions) {
            if (m.checkDead()) {
                playerMinions.remove(m);
                return;
            }
            m.tick(p);


        }

        for (Minion m : aiMinions) {
            if (m.checkDead()) {
                aiMinions.remove(m);
                return;
            }
            m.tick(p);
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
        for (PVector pos: topLanePoints) {
            d.drawWaypoint(p, pos);
        }
        for (PVector pos: midLanePoints) {
            d.drawWaypoint(p, pos);
        }
        for (PVector pos: btmLanePoints) {
            d.drawWaypoint(p, pos);
        }
        if(sw.elapsedTime() >= minionSpawnRate) {
            buildMinions();
            sw.reset();
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
        d.drawFrameRate(p);
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
        //Spawn AI minions
        aiMinions.add(new Minion(p, new PVector(p.width - 130, p.height/2f), topLanePoints, false));
        aiMinions.add(new Minion(p, new PVector(p.width - 100, p.height/2f), midLanePoints, false));
        aiMinions.add(new Minion(p, new PVector(p.width - 130, p.height/2f), btmLanePoints, false));
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
        topLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f - p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.1f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.25f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.5f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.75f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.9f, p.height*0.15f));
        topLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f - p.height*0.1f));
    }

    private void buildMidLane() {
        midLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.25f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.5f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.75f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f));
    }

    private void buildBtmLane() {
        btmLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f + p.height*0.1f));
        btmLanePoints.add(new PVector(p.width*0.1f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.25f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.5f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.75f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.9f, p.height*0.85f));
        btmLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f + p.height*0.1f));
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
}
