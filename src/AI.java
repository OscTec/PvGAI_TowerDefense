import processing.core.PApplet;

public class AI {
    private PApplet p;
    private int maxPoints = 25;
    private int aiPointsUsed = 0;

    AI(PApplet p) {
        this.p = p;
    }

    void assignPoints() {
        while(aiPointsUsed < maxPoints) {
            float r = p.random(76);
            if(r < 5 && Stats.getAiMinionSpeed() + 1 <= 5) {//Minion Speed
                Stats.setAiMinionSpeed(1);
                aiPointsUsed++;
            }
            if(5 <= r && r < 25 && Stats.getAiMinionHealth() + 10 <= 200) {//Minion Health
                Stats.setAiMinionHealth(10);
                aiPointsUsed++;
            }
            if(25 <= r && r < 45 && Stats.getAiMinionDamage() + 10 <= 200) {// Minion Damage
                Stats.setAiMinionDamage(10);
                aiPointsUsed++;
            }
            if(45 <= r && r < 65 && Stats.getAiMinionRange() + 10 <= 200) {// Minion Range
                Stats.setAiMinionRange(10);
            }
            if(65 <= r && r < 75 && Stats.getAiMinionAtkSpeed() + 1 < 10) {//Minion atk speed
                Stats.setAiMinionAtkSpeed(1);
            }
        }
    }

    void tick() {

    }
}
