import processing.core.PApplet;

public class otherAI {
    private PApplet p;
    private int maxPoints = 25;
    private int aiPointsUsed = 0;

    otherAI(PApplet p) {
        this.p = p;
    }

    void assignPoints() {
        while(aiPointsUsed < maxPoints) {
            float r = p.random(69);
            if(r < 5 && Stats.getpSpdPoints() + 1 <= 5) {//Minion Speed
                Stats.setpSpdPoints(1);
                aiPointsUsed++;
            }
            if(5 <= r && r < 25 && Stats.getpHltPoints() + 1 <= 20) {//Minion Health
                Stats.setpHltPoints(1);
                aiPointsUsed++;
            }
            if(25 <= r && r < 45 && Stats.getpDmgPoints() + 1 <= 20) {// Minion Damage
                Stats.setpDmgPoints(1);
                aiPointsUsed++;
            }
            if(45 <= r && r < 65 && Stats.getpRngPoints() + 1 <= 20) {// Minion Range
                Stats.setpRngPoints(1);
                aiPointsUsed++;
            }
            if(65 <= r && r < 68 && Stats.getpAtsPoints() + 1 <= 3) {//Minion atk speed
                Stats.setpAtsPoints(1);
                aiPointsUsed++;
            }
        }
    }


    /*
    void assignPoints() {
        while(aiPointsUsed < maxPoints) {
            float r = p.random(76);
            if(r < 5 && Stats.getPlayerMinionSpeed() + 1 <= 5) {//Minion Speed
                Stats.setPlayerMinionSpeed(1);
                aiPointsUsed++;
            }
            if(5 <= r && r < 25 && Stats.getPlayerMinionHealth() + 10 <= 200) {//Minion Health
                Stats.setPlayerMinionHealth(10);
                aiPointsUsed++;
            }
            if(25 <= r && r < 45 && Stats.getPlayerMinionDamage() + 10 <= 200) {// Minion Damage
                Stats.setPlayerMinionDamage(10);
                aiPointsUsed++;
            }
            if(45 <= r && r < 65 && Stats.getPlayerMinionRange() + 10 <= 200) {// Minion Range
                Stats.setPlayerMinionRange(10);
                aiPointsUsed++;
            }
            if(65 <= r && r < 75 && Stats.getPlayerMinionAtkSpeed() + 1 < 10) {//Minion atk speed
                Stats.setPlayerMinionAtkSpeed(1);
                aiPointsUsed++;
            }
        }
    }
    */
}
