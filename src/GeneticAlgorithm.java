import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class GeneticAlgorithm implements Runnable {
    private Thread t;
    private String threadName = "Sim";

    //boolean running = false;
    private int timesRun = 0;
    PApplet p;
    private ArrayList<Simulation> sims = new ArrayList<>();
    private int numOfSims = 30;
    private Minion bestMinion;
    private ArrayList<Minion> oldGen = new ArrayList<>();
    private ArrayList<Minion> newGen = new ArrayList<>();

    private Headquarters playerHQ;
    private Headquarters aiHQ;
    private ArrayList<PVector> topLanePoints;
    private ArrayList<PVector> midLanePoints;
    private ArrayList<PVector> btmLanePoints;
    private ArrayList<Tower> playerTowers;
    private ArrayList<Tower> aiTowers;

    private Stopwatch sw = new Stopwatch();
    private Stopwatch simWatch = new Stopwatch();



    GeneticAlgorithm(PApplet p, ArrayList<PVector> topLanePoints, ArrayList<PVector> midLanePoints, ArrayList<PVector> btmLanePoints, Headquarters playerHQ, Headquarters aiHQ, ArrayList<Tower> playerTowers, ArrayList<Tower> aiTowers) {
        this.p = p;
        this.topLanePoints = topLanePoints;
        this.midLanePoints = midLanePoints;
        this.btmLanePoints = btmLanePoints;
        this.playerHQ = playerHQ;
        this.aiHQ = aiHQ;
        this.playerTowers = playerTowers;
        this.aiTowers = aiTowers;
        buildSimulation();

    }
    /*
    public void run() {
        System.out.println("Sim run method");
        //while(Environment.gameRunning) {
            //System.out.println("Game running");
            if(!running && timesRun <= Environment.spawnCounter) {
                buildSimulation();
                running = true;
            }
            while (running) {
                runSims();
            }

            //buildsims
            //set running to true
            //call runSims

        //}

        System.out.println("Finished sims");
        //Environment.unpause();

    }
    */

    public void run() {
        while(Environment.gameRunning) {
            if(sw.elapsedTime() >= 5f) {
                System.out.println(Environment.simsRunning);
                sw.reset();
            }
            //System.out.println(Environment.simsRunning);
            //System.out.println("Game running");
            if(Environment.simsRunning) {
                System.out.println(Environment.simsRunning);
                while(Environment.simsRunning) {
                    if(simWatch.elapsedTime() >= 1f) {
                        System.out.println("Sim running");
                        simWatch.reset();
                    }

                    runSims();
                }
            }

        }

    }

    void start () {
        System.out.println("Starting simulation");
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    void runSims() {
        if (Methods.areAllTrue(sims)) {
            for (Simulation sim : sims) {
                for (Minion m : sim.getFinihsedMinions()) {
                    oldGen.add(Methods.copyMinion(p, m));
                }
            }
            //comment out  between comments to have elite minion stats stay on
            if(bestMinion != null) {
                bestMinion.resetDamageDealt();
            }
            //
            for (Minion m : oldGen) {
                if (bestMinion == null || m.getDamageDealt() > bestMinion.getDamageDealt()) {
                    bestMinion = Methods.copyMinion(p, m);
                }
            }

            Stats.setAiHltPoints(bestMinion.getHltPoints());
            Stats.setAiSpdPoints(bestMinion.getSpdPoints());
            Stats.setAiRngPoints(bestMinion.getRngPoints());
            Stats.setAiDmgPoints(bestMinion.getDmgPoints());
            Stats.setAiAtsPoints(bestMinion.getAtsPoints());
            /*
            Stats.setAiMinionHealthValue(bestMinion.getHltPoints()*Stats.getHltInc());
            Stats.setAiMinionSpeedValue(bestMinion.getSpdPoints()*Stats.getSpdInc());
            Stats.setAiMinionRangeValue(bestMinion.getRngPoints()*Stats.getRngInc());
            Stats.setAiMinionDamageValue(bestMinion.getDmgPoints()*Stats.getDmgInc());
            Stats.setAiMinionAtkSpeedValue(bestMinion.getAtsPoints()*Stats.getAtsInc());
            */
            /*
            Stats.setAiMinionHealthValue(bestMinion.getHealth());
            Stats.setAiMinionSpeedValue(bestMinion.getSpeed());
            Stats.setAiMinionRangeValue(bestMinion.getRange());
            Stats.setAiMinionDamageValue(bestMinion.getDamage());
            Stats.setAiMinionAtkSpeedValue(bestMinion.getAtkSpeed());
            */
            ArrayList<Minion> midGen;

            System.out.println("Best Minion has: " + bestMinion.getHltPoints() + " " + bestMinion.getDmgPoints() + " " + bestMinion.getSpdPoints() + " " + bestMinion.getRngPoints() + " " + bestMinion.getAtsPoints());

            midGen = Methods.buildScoredMinions(oldGen);
            for (int counter = 0; counter <= (numOfSims * 3); counter++) {
                float x = p.random(midGen.size());
                float y = p.random(midGen.size());
                newGen.add(Methods.breedMinions(p, midGen.get((int) x), midGen.get((int) y)));
            }
            Methods.mutateMinions(p, newGen, 0.05f);
            oldGen.clear();
            midGen.clear();
            oldGen = Methods.copyMinions(p, newGen);
            newGen.clear();
            sims.clear();
            timesRun++;
            Environment.simsRunning = false;
            //running = false;
            System.out.println("Sims finished");
            //System.out.println("Player damage " + Stats.getpDmgPoints() + " AI Damage " + Stats.getAiDmgPoints());
            //Environment.unpause();
        } else {
            for (Simulation sim : sims) {
                sim.tick();
                //System.out.println("Sim tick");
            }
        }
    }

    void buildSimulation() {
        System.out.println("BuildSimulation");
        if(oldGen.isEmpty()) {
            for (int counter = 0; counter < numOfSims; counter++) {
                Headquarters leftHQ = copyHQ(p, playerHQ.getPos(), playerHQ.getCurrentHealth(), playerHQ.getPlayer());
                Headquarters rightHQ = copyHQ(p, aiHQ.getPos(), aiHQ.getCurrentHealth(), aiHQ.getPlayer());
                sims.add(new Simulation(p, playerTowers, aiTowers, leftHQ, rightHQ, topLanePoints, midLanePoints, btmLanePoints));
            }
        } else {
            for (int counter = 0; counter < oldGen.size() - 1; counter = counter + 3) {
                Headquarters leftHQ = copyHQ(p, playerHQ.getPos(), playerHQ.getCurrentHealth(), playerHQ.getPlayer());
                Headquarters rightHQ = copyHQ(p, aiHQ.getPos(), aiHQ.getCurrentHealth(), aiHQ.getPlayer());
                sims.add(new Simulation(p, playerTowers, aiTowers, leftHQ, rightHQ, topLanePoints, midLanePoints, btmLanePoints, oldGen.get(counter), oldGen.get(counter+1), oldGen.get(counter+2)));
            }
        }
    }

    Headquarters copyHQ(PApplet p, PVector pos, int currentHealth, boolean player) {
        PVector bPos = (PVector) Methods.deepClone(pos);
        boolean bPlayer = (boolean) Methods.deepClone(player);
        int bHealth = (int) Methods.deepClone(currentHealth);
        return new Headquarters(p, bPos, bPlayer, bHealth);
    }
}
