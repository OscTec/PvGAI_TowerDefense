public class Stats {
    private static int minionSpawnRate = 5;

    private static int playerMinionHealth = 100;
    private static int playerMinionSpeed = 2;
    private static int playerMinionRange = 50;
    private static int playerMinionDamage = 100;
    private static float playerMinionAtkSpeed = 2;

    private static int aiMinionHealth = 100;
    private static int aiMinionSpeed = 2;
    private static int aiMinionRange = 50;
    private static int aiMinionDamage = 100;
    private static float aiMinionAtkSpeed  = 2;

    private static int towerHealth = 500;
    private static int towerDamage = 10;
    private static int towerRange = 100;
    private static float towerAtkSpeed = 2;

    private static int hqHealth = 2000;

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
