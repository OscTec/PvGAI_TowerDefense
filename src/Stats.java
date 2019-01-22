public class Stats {
    private static int minionSpawnRate = 5;

    private static int playerMinionHealth;
    private static int playerMinionSpeed;
    private static int playerMinionRange;
    private static int playerMinionDamage;
    private static float playerMinionAtkSpeed;

    private static int aiMinionHealth;
    private static int aiMinionSpeed;
    private static int aiMinionRange;
    private static int aiMinionDamage;
    private static float aiMinionAtkSpeed;

    private static int towerHealth = 500;
    private static int towerDamage = 10;
    private static int towerRange = 100;
    private static float towerAtkSpeed = 2;

    static int getMinionSpawnRate() {
        return minionSpawnRate;
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


}
