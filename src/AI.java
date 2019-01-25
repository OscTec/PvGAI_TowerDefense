import processing.core.PApplet;

public class AI {
    private PApplet p;
    private int maxPoints = 25;
    private int aiPointsUsed = 0;
    private Minion minion1;
    private Minion minion2;

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
                aiPointsUsed++;
            }
            if(65 <= r && r < 75 && Stats.getAiMinionAtkSpeed() + 1 < 10) {//Minion atk speed
                Stats.setAiMinionAtkSpeed(1);
                aiPointsUsed++;
            }
        }
    }

    boolean testMinion(Minion test) {
        if (minion1 == null) {
            return true;
        } else {
            if(test.getDamageDealt() > minion1.getDamageDealt()) {
                return true;
            } else {
                return false;
            }
        }
    }

    void saveMinion(PApplet p, int health, int speed, int range, int damage, float atkSpeed) {
        minion1 = new Minion(p, health, speed, range, damage, atkSpeed);
    }

    void modStats() {
        float r = p.random(100);
        boolean statModded = false;
        if(r < 20 && minion1.getSpeed() - 1 >= 1) {//Minion Speed
            Stats.makeAiMinionSpeed(minion1.getSpeed() - 1);;
            aiPointsUsed--;
            statModded = true;
        }
        if(20 <= r && r < 40 && minion1.getHealth() - 10 >= 10) {//Minion Health
            Stats.makeAiMinionHealth(minion1.getHealth() - 10);
            aiPointsUsed--;
            statModded = true;
        }
        if(40 <= r && r < 60 && minion1.getDamage() - 10 >= 10) {// Minion Damage
            Stats.makeAiMinionDamage(minion1.getDamage() - 10);
            aiPointsUsed--;
            statModded = true;
        }
        if(60 <= r && r < 80 && minion1.getRange() - 10 >= 10) {// Minion Range
            Stats.makeAiMinionRange(minion1.getRange() - 10);
            aiPointsUsed--;
            statModded = true;
        }
        if(80 <= r && r < 100 && minion1.getAtkSpeed() - 1 >= 1) {//Minion atk speed
            Stats.makeAiMinionAtkSpeed(minion1.getAtkSpeed() - 1);
            aiPointsUsed--;
            statModded = true;
        }

        if(statModded) {
            r = p.random(100);
            if(r < 20 && minion1.getSpeed() + 1 <= 5) {//Minion Speed
                Stats.makeAiMinionSpeed(minion1.getSpeed() + 1);
                aiPointsUsed++;
            }
            if(20 <= r && r < 40 && minion1.getHealth() + 10 <= 200) {//Minion Health
                Stats.makeAiMinionHealth(minion1.getHealth() + 10);
                aiPointsUsed++;
            }
            if(40 <= r && r < 60 && minion1.getDamage() + 10 <= 200) {// Minion Damage
                Stats.makeAiMinionDamage(minion1.getDamage() + 10);
                aiPointsUsed++;
            }
            if(60 <= r && r < 80 && minion1.getRange() + 10 <= 200) {// Minion Range
                Stats.makeAiMinionRange(minion1.getRange() + 10);
                aiPointsUsed++;
            }
            if(80 <= r && r < 100 && minion1.getAtkSpeed() + 1 < 10) {//Minion atk speed
                Stats.makeAiMinionAtkSpeed(minion1.getAtkSpeed() + 1);
                aiPointsUsed++;
            }

        }

    }
}
