public class Stats {
    private static boolean playerWon = false;
    private static boolean aiWon = false;

    private static int minionSpawnRate = 5;

    private static int playerMinionHealth = 10;
    private static int playerMinionSpeed = 1;
    private static int playerMinionRange = 10;
    private static int playerMinionDamage = 10;
    private static float playerMinionAtkSpeed = 1;

    private static int aiMinionHealth = 10;
    private static int aiMinionSpeed = 1;
    private static int aiMinionRange = 10;
    private static int aiMinionDamage = 10;
    private static float aiMinionAtkSpeed  = 1;

    private static int towerHealth = 500;
    private static int towerDamage = 10;
    private static int towerRange = 100;
    private static float towerAtkSpeed = 2;

    private static int hqHealth = 2000;

    static void reset() {
        playerMinionHealth = 10;
        playerMinionSpeed = 1;
        playerMinionRange = 10;
        playerMinionDamage = 10;
        playerMinionAtkSpeed = 1;

        aiMinionHealth = 10;
        aiMinionSpeed = 1;
        aiMinionRange = 10;
        aiMinionDamage = 10;
        aiMinionAtkSpeed = 1;
    }

    static boolean getPlayerWon() { return playerWon;}

    static void setPlayerWon(boolean value) {playerWon = value;}

    static boolean getAiWon() { return aiWon;}

    static void setAiWon(boolean value) {aiWon = value;}

    static int getMinionSpawnRate() {
        return minionSpawnRate;
    }

    static int getPlayerMinionHealth() {
        return playerMinionHealth;
    }

    static void setPlayerMinionHealth(int value) { playerMinionHealth = playerMinionHealth + value; }

    static int getPlayerMinionSpeed() {
        return playerMinionSpeed;
    }

    static void setPlayerMinionSpeed(int value) { playerMinionSpeed = playerMinionSpeed + value;}

    static int getPlayerMinionRange() {
        return playerMinionRange;
    }

    static void setPlayerMinionRange(int value) { playerMinionRange = playerMinionRange + value;}

    static  int getPlayerMinionDamage() {
        return playerMinionDamage;
    }

    static void setPlayerMinionDamage(int value) { playerMinionDamage = playerMinionDamage + value;}

    static float getPlayerMinionAtkSpeed() {
        return playerMinionAtkSpeed;
    }

    static void setPlayerMinionAtkSpeed(float value) { playerMinionAtkSpeed = playerMinionAtkSpeed + value;}

    static int getAiMinionHealth() {
        return aiMinionHealth;
    }

    static int getAiMinionSpeed() {
        return aiMinionSpeed;
    }

    static int getAiMinionRange() {
        return aiMinionRange;
    }

    static int getAiMinionDamage() {
        return aiMinionDamage;
    }

    static float getAiMinionAtkSpeed() {
        return aiMinionAtkSpeed;
    }

    static int getTowerHealth() {
        return towerHealth;
    }

    static int getTowerDamage() {
        return towerDamage;
    }

    static int getTowerRange() {
        return towerRange;
    }

    static float getTowerAtkSpeed() {
        return towerAtkSpeed;
    }

    static int getHqHealth() {return hqHealth;}


}
