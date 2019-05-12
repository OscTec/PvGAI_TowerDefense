public class Stats {
    private static boolean playerWon = false;
    private static boolean aiWon = false;

    private static int minionSpawnRate = 5;
    private static int maxSkillPoints = 25;

    private static int hltInc =10;
    private static int spdInc = 1;
    private static int rngInc = 10;
    private static int dmgInc = 10;
    private static int atsInc = 1;

    private static int pHltPoints = 1;
    private static int pSpdPoints = 1;
    private static int pRngPoints = 1;
    private static int pDmgPoints = 1;
    private static int pAtsPoints = 1;

    private static int aiHltPoints = 1;
    private static int aiSpdPoints = 1;
    private static int aiRngPoints = 1;
    private static int aiDmgPoints = 1;
    private static int aiAtsPoints = 1;
    /*
    private static int playerMinionHealth = 10;
    private static int playerMinionSpeed = 1;
    private static int playerMinionRange = 10;
    private static int playerMinionDamage = 10;
    private static float playerMinionAtkSpeed = 1;
    */
    private static int aiMinionHealth = 10;
    private static int aiMinionSpeed = 1;
    private static int aiMinionRange = 10;
    private static int aiMinionDamage = 10;
    private static float aiMinionAtkSpeed  = 1;

    private static int towerHealth = 500;
    private static int towerDamage = 100;
    private static int towerRange = 100;
    private static float towerAtkSpeed = 0.5f;

    private static int hqHealth = 2000;

    static void reset() {
        /*
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
        */
        pHltPoints = 1;
        pSpdPoints = 1;
        pRngPoints = 1;
        pDmgPoints = 1;
        pAtsPoints = 1;

        aiHltPoints = 1;
        aiSpdPoints = 1;
        aiRngPoints = 1;
        aiDmgPoints = 1;
        aiAtsPoints = 1;
    }

    static boolean getPlayerWon() { return playerWon;}

    static void setPlayerWon(boolean value) {playerWon = value;}

    static boolean getAiWon() { return aiWon;}

    static void setAiWon(boolean value) {aiWon = value;}

    static int getMinionSpawnRate() {
        return minionSpawnRate;
    }
    /*
    static int getPlayerMinionHealth() { return playerMinionHealth; }

    static void setPlayerMinionHealth(int value) { playerMinionHealth = playerMinionHealth + value; }

    static int getPlayerMinionSpeed() { return playerMinionSpeed; }

    static void setPlayerMinionSpeed(int value) { playerMinionSpeed = playerMinionSpeed + value;}

    static int getPlayerMinionRange() { return playerMinionRange; }

    static void setPlayerMinionRange(int value) { playerMinionRange = playerMinionRange + value;}

    static  int getPlayerMinionDamage() { return playerMinionDamage; }

    static void setPlayerMinionDamage(int value) { playerMinionDamage = playerMinionDamage + value;}

    static float getPlayerMinionAtkSpeed() { return playerMinionAtkSpeed; }

    static void setPlayerMinionAtkSpeed(float value) { playerMinionAtkSpeed = playerMinionAtkSpeed + value;}
    */

    static int getAiMinionHealth() { return aiMinionHealth; }

    static void setAiMinionHealth(int value) { aiMinionHealth = aiMinionHealth + value; }

    static void setAiMinionHealthValue(int value) { aiMinionHealth = value; }

    static int getAiMinionSpeed() { return aiMinionSpeed; }

    static void setAiMinionSpeed(int value) { aiMinionSpeed = aiMinionSpeed + value;}

    static void setAiMinionSpeedValue(int value) { aiMinionSpeed = value;}

    static int getAiMinionRange() { return aiMinionRange; }

    static void setAiMinionRange(int value) { aiMinionRange = aiMinionRange + value;}

    static void setAiMinionRangeValue(int value) { aiMinionRange = value;}

    static int getAiMinionDamage() { return aiMinionDamage; }

    static void setAiMinionDamage(int value) { aiMinionDamage = aiMinionDamage + value;}

    static void setAiMinionDamageValue(int value) { aiMinionDamage = value;}

    static float getAiMinionAtkSpeed() { return aiMinionAtkSpeed; }

    static void setAiMinionAtkSpeed(float value) { aiMinionAtkSpeed = aiMinionAtkSpeed + value;}

    static void setAiMinionAtkSpeedValue(float value) { aiMinionAtkSpeed = value;}

    static int getTowerHealth() { return towerHealth; }

    static int getTowerDamage() { return towerDamage; }

    static int getTowerRange() { return towerRange; }

    static float getTowerAtkSpeed() { return towerAtkSpeed; }

    static int getHqHealth() {return hqHealth;}

    static void makeAiMinionHealth(int value) {aiMinionHealth = value;}

    static void makeAiMinionSpeed(int value) {aiMinionSpeed = value;}

    static void makeAiMinionRange(int value) {aiMinionRange = value;}

    static void makeAiMinionDamage(int value) {aiMinionDamage = value;}

    static void makeAiMinionAtkSpeed(float value) {aiMinionAtkSpeed = value;}

    static int getHltInc() {return hltInc;}
    static int getSpdInc() {return spdInc;}
    static int getRngInc() {return rngInc;}
    static int getDmgInc() {return dmgInc;}
    static int getAtsInc() {return atsInc;}

    static int getpHltPoints() {return pHltPoints;}
    static int getpSpdPoints() {return pSpdPoints;}
    static int getpRngPoints() {return pRngPoints;}
    static int getpDmgPoints() {return pDmgPoints;}
    static int getpAtsPoints() {return pAtsPoints;}

    static void setpHltPoints(int value) {pHltPoints += value;}
    static void setpSpdPoints(int value) {pSpdPoints += value;}
    static void setpRngPoints(int value) {pRngPoints += value;}
    static void setpDmgPoints(int value) {pDmgPoints += value;}
    static void setpAtsPoints(int value) {pAtsPoints += value;}

    static int getAiHltPoints() {return aiHltPoints;}
    static int getAiSpdPoints() {return aiSpdPoints;}
    static int getAiRngPoints() {return aiRngPoints;}
    static int getAiDmgPoints() {return aiDmgPoints;}
    static int getAiAtsPoints() {return aiAtsPoints;}

    static void setAiHltPoints(int value) {aiHltPoints = value;}
    static void setAiSpdPoints(int value) {aiSpdPoints = value;}
    static void setAiRngPoints(int value) {aiRngPoints = value;}
    static void setAiDmgPoints(int value) {aiDmgPoints = value;}
    static void setAiAtsPoints(int value) {aiAtsPoints = value;}

    static int getMaxSkillPoints() {return maxSkillPoints;}




}
