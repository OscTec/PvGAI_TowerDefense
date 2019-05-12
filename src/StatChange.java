import processing.core.PApplet;

class StatChange {

    PApplet p;
    private int maxPoints = 25;
    private static int playerPointsUsed = 5;
    private Stopwatch sw = new Stopwatch();

    StatChange(PApplet p) {
        this.p = p;
    }

    void tick() {
        keyPressed();
    }

    void keyPressed() {
        if (p.keyPressed && sw.elapsedTime() >= 0.1f) {
            if (p.key == 'q' || p.key == 'Q') {
                playerMinionHealthDecrease();
            }
            if (p.key == 'w' || p.key == 'W') {
                playerMinionHealthIncrease();
            }
            if (p.key == 'e' || p.key == 'E') {
                playerMinionDamageDecrease();
            }
            if (p.key == 'r' || p.key == 'R') {
                playerMinionDamageIncrease();
            }
            if (p.key == 't' || p.key == 'T') {
                playerMinionSpeedDecrease();
            }
            if (p.key == 'y' || p.key == 'Y') {
                playerMinionSpeedIncrease();
            }
            if (p.key == 'u' || p.key == 'U') {
                playerMinionRangeDecrease();
            }
            if (p.key == 'i' || p.key == 'I') {
                playerMinionRangeIncrease();
            }
            if (p.key == 'o' || p.key == 'O') {
                playerMinionAtkSpeedDecrease();
            }
            if (p.key == 'p' || p.key == 'P') {
                playerMinionAtkSpeedIncrease();
            }
            sw.reset();
        }
    }

    void playerMinionHealthDecrease() {
        if(Stats.getpHltPoints() - 1 >= 1 ) {
            Stats.setpHltPoints(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionHealthIncrease() {
        if(Stats.getpHltPoints() + 1 <= 20 && playerPointsUsed < maxPoints) {
            Stats.setpHltPoints(1);
            playerPointsUsed++;
        }
    }

    void playerMinionDamageDecrease() {
        if(Stats.getpDmgPoints() - 1 >= 1) {
            Stats.setpDmgPoints(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionDamageIncrease() {
        if(Stats.getpDmgPoints() + 1 <= 20 && playerPointsUsed < maxPoints) {
            Stats.setpDmgPoints(1);
            playerPointsUsed++;
        }
    }

    void playerMinionSpeedDecrease() {
        if(Stats.getpSpdPoints() - 1 >= 1) {
            Stats.setpSpdPoints(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionSpeedIncrease() {
        if(Stats.getpSpdPoints() + 1 <= 5 && playerPointsUsed < maxPoints) {
            Stats.setpSpdPoints(1);
            playerPointsUsed++;
        }
    }

    void playerMinionRangeDecrease() {
        if(Stats.getpRngPoints() - 1 >= 1) {
            Stats.setpRngPoints(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionRangeIncrease() {
        if(Stats.getpRngPoints() + 1 <= 20 && playerPointsUsed < maxPoints) {
            Stats.setpRngPoints(1);
            playerPointsUsed++;
        }
    }

    void playerMinionAtkSpeedDecrease() {
        if(Stats.getpAtsPoints() - 1 >= 1) {
            Stats.setpAtsPoints(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionAtkSpeedIncrease() {
        if(Stats.getpAtsPoints() + 1 <= 3 && playerPointsUsed < maxPoints) {
            Stats.setpAtsPoints(1);
            playerPointsUsed++;
        }
    }

    /*
    void playerMinionHealthDecrease() {
        if(Stats.getPlayerMinionHealth() - 10 >= 10 ) {
            Stats.setPlayerMinionHealth(-10);
            playerPointsUsed--;
        }
    }

    void playerMinionHealthIncrease() {
        if(Stats.getPlayerMinionHealth() + 10 <= 200 && playerPointsUsed < maxPoints) {
            Stats.setPlayerMinionHealth(10);
            playerPointsUsed++;
        }
    }

    void playerMinionDamageDecrease() {
        if(Stats.getPlayerMinionDamage() - 10 >= 10) {
            Stats.setPlayerMinionDamage(-10);
            playerPointsUsed--;
        }
    }

    void playerMinionDamageIncrease() {
        if(Stats.getPlayerMinionDamage() + 10 <= 200 && playerPointsUsed < maxPoints) {
            Stats.setPlayerMinionDamage(10);
            playerPointsUsed++;
        }
    }

    void playerMinionSpeedDecrease() {
        if(Stats.getPlayerMinionSpeed() - 1 >= 1) {
            Stats.setPlayerMinionSpeed(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionSpeedIncrease() {
        if(Stats.getPlayerMinionSpeed() + 1 <= 5 && playerPointsUsed < maxPoints) {
            Stats.setPlayerMinionSpeed(1);
            playerPointsUsed++;
        }
    }

    void playerMinionRangeDecrease() {
        if(Stats.getPlayerMinionRange() - 10 >= 10) {
            Stats.setPlayerMinionRange(-10);
            playerPointsUsed--;
        }
    }

    void playerMinionRangeIncrease() {
        if(Stats.getPlayerMinionRange() + 10 <= 200 && playerPointsUsed < maxPoints) {
            Stats.setPlayerMinionRange(10);
            playerPointsUsed++;
        }
    }

    void playerMinionAtkSpeedDecrease() {
        if(Stats.getPlayerMinionAtkSpeed() - 1 >= 1) {
            Stats.setPlayerMinionAtkSpeed(-1);
            playerPointsUsed--;
        }
    }

    void playerMinionAtkSpeedIncrease() {
        if(Stats.getPlayerMinionAtkSpeed() + 1 <= 10 && playerPointsUsed < maxPoints) {
            Stats.setPlayerMinionAtkSpeed(1);
            playerPointsUsed++;
        }
    }
    */

    public static int getPlayerPointsUsed() {
        return playerPointsUsed;
    }

    public static void resetPoints() {
        playerPointsUsed = 0;
    }
}
