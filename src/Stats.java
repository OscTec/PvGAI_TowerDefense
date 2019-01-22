public class Stats {
    private int playerMinionHealth;
    private int playerMinionSpeed;
    private int playerMinionRange;
    private int playerMinionDamage;
    private float playerMinionAtkSpeed;

    private int aiMinionHealth;
    private int aiMinionSpeed;
    private int aiMinionRange;
    private int aiMinionDamage;
    private float aiMinionAtkSpeed;

    private static int towerHealth = 50;
    private static int towerDamage = 10;
    private static int towerRange = 100;
    private static float towerAtkSpeed = 2;

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
