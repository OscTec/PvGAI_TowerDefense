import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class GeneticAlgorithm implements Runnable {
    private Thread t;
    private String threadName = "Sim";

    private boolean running = true;
    PApplet p;
    private ArrayList<Simulation> sims = new ArrayList<>();
    private int numOfSims = 2;
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

    public void run() {
        //System.out.println("Running " +  threadName );
        //buildSimulation();
        while (running) {
            runSims();
        }
        System.out.println("Finished sims");
        Environment.unpause();

    }

    void start () {
        //System.out.println("Starting simulation");
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
            for (Minion m : oldGen) {
                if (bestMinion == null || m.getDamageDealt() > bestMinion.getDamageDealt()) {
                    bestMinion = Methods.copyMinion(p, m);
                    System.out.println(bestMinion.getHealth() + " " + bestMinion.getSpeed() + " " + bestMinion.getRange() + " " + bestMinion.getDamage() + " " + bestMinion.getAtkSpeed());
                }
            }
            Stats.setAiMinionHealthValue(bestMinion.getHealth());
            Stats.setAiMinionSpeedValue(bestMinion.getSpeed());
            Stats.setAiMinionRangeValue(bestMinion.getRange());
            Stats.setAiMinionDamageValue(bestMinion.getDamage());
            Stats.setAiMinionAtkSpeedValue(bestMinion.getAtkSpeed());
            ArrayList<Minion> midGen;

            System.out.println("Best Minion has: " + bestMinion.getHealth() + " " + bestMinion.getDamage() + " " + bestMinion.getSpeed() + " " + bestMinion.getRange() + " " + bestMinion.getAtkSpeed());

            midGen = Methods.buildScoredMinions(oldGen);
            for (int counter = 0; counter <= (numOfSims * 3); counter++) {
                float x = p.random(midGen.size());
                float y = p.random(midGen.size());
                newGen.add(Methods.breedMinions(p, midGen.get((int) x), midGen.get((int) y)));
            }
            Methods.mutateMinions(p, newGen, 5);
            oldGen.clear();
            midGen.clear();
            oldGen = Methods.copyMinions(p, newGen);
            newGen.clear();
            running = false;
            Environment.unpause();
        } else {
            for (Simulation sim : sims) {
                sim.tick();
            }
        }
    }

    void buildSimulation() {
        System.out.println("BuildSimulation");
        if(oldGen.isEmpty()) {
            for (int counter = 0; counter <= numOfSims; counter++) {
                Headquarters leftHQ = copyHQ(p, playerHQ.getPos(), playerHQ.getCurrentHealth(), playerHQ.getPlayer());
                Headquarters rightHQ = copyHQ(p, aiHQ.getPos(), aiHQ.getCurrentHealth(), aiHQ.getPlayer());
                sims.add(new Simulation(p, playerTowers, aiTowers, leftHQ, rightHQ, topLanePoints, midLanePoints, btmLanePoints));
            }
        } else {
            for (int counter = 0; counter <= oldGen.size(); counter = counter + 3) {
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
