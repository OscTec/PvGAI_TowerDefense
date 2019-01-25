import processing.core.PApplet;

class StatChange {

    PApplet p;
    private int maxPoints = 25;
    private int playerPointsUsed = 0;

    StatChange(PApplet p) {
        this.p = p;
    }

    void tick() {
        keyPressed();
    }

    void keyPressed() {
        if (p.keyPressed) {
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
        }
    }

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
}
