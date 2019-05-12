import processing.core.PApplet;
import processing.core.PVector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class Methods {

    protected static PVector findMinion(PVector pos, ArrayList<Minion> targets) {
        PVector seekThis = new PVector();
        double lowestDistance = Settings.maxDistance;
        for (Minion m : targets) {
            if(m == null) {
                return seekThis;
            }
            float d = pos.dist(m.getPos());

            if (d < lowestDistance) {
                lowestDistance = d;
                seekThis = m.getPos();

            }
        }
        return seekThis;
    }


    protected static PVector findTarget(PVector pos, ArrayList<Tower> targets) {
        PVector seekThis = new PVector();
        double lowestDistance = Settings.maxDistance;
        for (Tower t : targets) {
            float d = pos.dist(t.getPos());
            if (d < lowestDistance) {
                lowestDistance = d;
                seekThis = t.getPos();

            }
        }
        return seekThis;
    }

    protected static PVector seek(PVector pos, PVector target) {
        PVector desired = PVector.sub(target, pos);
        return desired;
    }

    protected static Projectile collisionCheck(PVector pos, ArrayList<Projectile> pro) {
        //boolean hit = false;
        for (Projectile i : pro) {
            //System.out.println(this.position);
            if ((i.position.x <= pos.x + 10 && i.position.x >= pos.x - 10) && (i.position.y <= pos.y + 10 && i.position.y >= pos.y - 10)) {
                //hit = true;

                //currentHealth = currentHealth - i.getDamage();
                //System.out.println("Health: " + currentHealth);
                //Environment.getAiProjectiles().remove(i);
                return i;
            }
        }
        return null;

    }

    static boolean areAllTrue(ArrayList<Simulation> array) {
        for (Simulation b : array) if (!b.simFinished()) return false;
        return true;
    }


    static ArrayList<Minion> buildScoredMinions(ArrayList<Minion> minList) {
        int scoreTotal = 0;
        ArrayList<Minion> sortedMinions = new ArrayList<>();
        for (Minion m : minList) {
            scoreTotal += m.getDamageDealt();
        }
        for (Minion m : minList) {
            if(m.getDamageDealt() == 0) {
                m.setFitness(0);
            } else {
                m.setFitness((m.getDamageDealt() / scoreTotal) * 100);
            }
        }
        for (Minion m : minList) {
            for (int x = 0; x <= m.getFitness(); x++) {
                sortedMinions.add(m);
            }
        }
        return sortedMinions;
    }

    static Minion breedMinions(PApplet p, Minion m1, Minion m2) {
        int newHltPoints = roundDown((int) deepClone((m1.getHltPoints() + m2.getHltPoints())/2));
        int newSpdPoints = roundDown((int) deepClone((m1.getSpdPoints() + m2.getSpdPoints())/2));
        int newRngPoints = roundDown((int) deepClone((m1.getRngPoints() + m2.getRngPoints())/2));
        int newDmgPoints = roundDown((int) deepClone((m1.getDmgPoints() + m2.getDmgPoints())/2));
        int newAtsPoints = roundDown((int) deepClone((m1.getAtsPoints() + m2.getAtsPoints())/2));

        if(newHltPoints + newSpdPoints + newRngPoints + newDmgPoints + newAtsPoints < Stats.getMaxSkillPoints()) {
            while(newHltPoints + newSpdPoints + newRngPoints + newDmgPoints + newAtsPoints < Stats.getMaxSkillPoints()) {
                //float r = p.random(69);
                Random rand = new Random();
                int r = rand.nextInt(69);
                if (r < 5 && newSpdPoints + 1 <= 5) {//Minion Speed
                    newSpdPoints++;
                }
                if (5 <= r && r < 25 && newHltPoints + 1 <= 20) {//Minion Health
                    newHltPoints++;
                }
                if (25 <= r && r < 45 && newDmgPoints + 1 <= 20) {// Minion Damage
                    newDmgPoints++;
                }
                if (45 <= r && r < 65 && newRngPoints + 1 <= 20) {// Minion Range
                    newRngPoints++;
                }
                if (65 <= r && r < 68 && newAtsPoints + 1 <= 3) {//Minion atk speed
                    newAtsPoints++;
                }
            }
        }

        /*
        int newHealth = roundDown(((int) deepClone(m1.getHealth()) + (int) deepClone(m2.getHealth())) / 2);//
        int newSpeed = roundDown((int) deepClone(m1.getSpeed()) + (int) deepClone(m2.getSpeed()) / 2);
        int newRange = roundDown((int) deepClone(m1.getRange()) + (int) deepClone(m2.getRange()) / 2);
        int newDamage = roundDown(((int) deepClone(m1.getDamage()) + (int) deepClone(m2.getDamage()) / 2));
        float newAtkSpeed = roundDown((((float) deepClone(m1.getAtkSpeed()) + (float) deepClone(m2.getAtkSpeed()))) / 2);
        */

        Minion offSpring = new Minion(p, newHltPoints, newSpdPoints, newRngPoints, newDmgPoints, newAtsPoints);
        return offSpring;
    }




    static int roundDown(int num) {
        if(num%5 == 0 && num%10 != 0) {
            return num-5;
        } else {
            return num;
        }
    }

    static float roundDown(float num) {
        if(num%0.5f == 0 && num%1 != 0) {
            return num-0.5f;
        }
        return num;
    }

    static void mutateMinions(PApplet p, ArrayList<Minion> minList, float mutLevel) {
        for (Minion m : minList) {
            int pointsChanged = 0;
            int pointsToChange = (int) Math.floor(25 * mutLevel);
            //boolean statChanged = false;
            while (pointsChanged < pointsToChange) {
                //float r = p.random(69);
                Random rand = new Random();
                int r = rand.nextInt(69);
                if (r < 5 && m.getSpdPoints() - 1 >= 1) {//Minion Speed
                    m.setSpdPoints(m.getSpdPoints() - 1);
                    pointsChanged++;
                }
                if (5 <= r && r < 25 && m.getHltPoints() - 1 >= 1) {//Minion Health
                    m.setHltPoints(m.getHltPoints() - 1);
                    pointsChanged++;
                }
                if (25 <= r && r < 45 && m.getDmgPoints() - 1 >= 1) {// Minion Damage
                    m.setDmgPoints(m.getDmgPoints() - 1);
                    pointsChanged++;
                }
                if (45 <= r && r < 65 && m.getRngPoints() - 1 >= 1) {// Minion Range
                    m.setRngPoints(m.getRngPoints() - 1);
                    pointsChanged++;
                }
                if (65 <= r && r < 68 && m.getAtsPoints() - 1 >= 1) {//Minion atk speed
                    m.setAtsPoints(m.getAtsPoints() - 1);
                    pointsChanged++;
                }
            }
            while (pointsChanged > 0) {
                //float r = p.random(69);
                Random rand = new Random();
                int r = rand.nextInt(69);
                if (r < 5 && m.getSpdPoints() + 1 <= 5) {//Minion Speed
                    m.setSpdPoints(m.getSpdPoints() + 1);
                    pointsChanged--;
                }
                if (5 <= r && r < 25 && m.getHltPoints() + 1 <= 20) {//Minion Health
                    m.setHltPoints(m.getHltPoints() + 1);
                    pointsChanged--;
                }
                if (25 <= r && r < 45 && m.getDmgPoints() + 1 <= 20) {// Minion Damage
                    m.setDmgPoints(m.getDmgPoints() + 1);
                    pointsChanged--;
                }
                if (45 <= r && r < 65 && m.getRngPoints() + 1 <= 20) {// Minion Range
                    m.setRngPoints(m.getRngPoints() + 1);
                    pointsChanged--;
                }
                if (65 <= r && r < 68 && m.getAtsPoints() + 1 <= 3) {//Minion atk speed
                    m.setAtsPoints(m.getAtsPoints() + 1);
                    pointsChanged--;
                }
            }
        }
    }



    /*
            while (pointsChanged < pointsToChange) {
                float r = p.random(76);
                if (r < 5 && m.getSpeed() - 1 >= 1) {//Minion Speed
                    m.setMaxSpeed(m.getSpeed() - 1);
                    pointsChanged++;
                }
                if (5 <= r && r < 25 && m.getHealth() - 10 >= 10) {//Minion Health
                    m.setHealth(m.getHealth() - 10);
                    pointsChanged++;
                }
                if (25 <= r && r < 45 && m.getDamage() - 10 >= 10) {// Minion Damage
                    m.setDamage(m.getDamage() - 10);
                    pointsChanged++;
                }
                if (45 <= r && r < 65 && m.getRange() - 10 >= 10) {// Minion Range
                    m.setRange(m.getRange() - 10);
                    pointsChanged++;
                }
                if (65 <= r && r < 75 && m.getAtkSpeed() - 1 >= 1) {//Minion atk speed
                    m.setAtkSpeed(m.getAtkSpeed() - 1);
                    pointsChanged++;
                }
            }
            while (pointsChanged > 0) {
                float r = p.random(76);
                if (r < 5 && m.getSpeed() + 1 <= 5) {//Minion Speed
                    m.setMaxSpeed(m.getSpeed() + 1);
                    pointsChanged--;
                }
                if (5 <= r && r < 25 && m.getHealth() + 10 <= 200) {//Minion Health
                    m.setHealth(m.getHealth() + 10);
                    pointsChanged--;
                }
                if (25 <= r && r < 45 && m.getDamage() + 10 <= 200) {// Minion Damage
                    m.setDamage(m.getDamage() + 10);
                    pointsChanged--;
                }
                if (45 <= r && r < 65 && m.getRange() + 10 <= 200) {// Minion Range
                    m.setRange(m.getRange() + 10);
                    pointsChanged--;
                }
                if (65 <= r && r < 75 && m.getAtkSpeed() + 1 <= 10) {//Minion atk speed
                    m.setAtkSpeed(m.getAtkSpeed() + 1);
                    pointsChanged--;
                }
            }
            */

    static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Minion copyMinion(PApplet p, Minion m) {

        int bHltPoints = (int) deepClone(m.getHltPoints());
        int bSpdPoints = (int) deepClone(m.getSpdPoints());
        int bRngPoints = (int) deepClone(m.getRngPoints());
        int bDmgPoints = (int) deepClone(m.getDmgPoints());
        int bAtsPoints = (int) deepClone(m.getAtsPoints());
        int bDamageDealt = (int) deepClone(m.getDamageDealt());

        return new Minion(p, bHltPoints, bSpdPoints, bRngPoints, bDmgPoints, bAtsPoints, bDamageDealt);

        /*
        int bMaxHealth = (int) deepClone(m.getHealth());
        int bMaxSpeed = (int) deepClone(m.getSpeed());
        int bRange = (int) deepClone(m.getRange());
        int bDamage = (int) deepClone(m.getDamage());
        float bFireRate = (float) deepClone(m.getAtkSpeed());
        int bDamageDealt = (int) deepClone(m.getDamageDealt());

        return new Minion(p, bMaxHealth, bMaxSpeed, bRange, bDamage, bFireRate, bDamageDealt);
        */
    }

    static ArrayList<Minion> copyMinions(PApplet p, ArrayList<Minion> ListOfMinions) {
        ArrayList<Minion> newMinionList = new ArrayList<>();
        for(Minion m: ListOfMinions) {
            newMinionList.add(copyMinion(p, m));
        }
        return newMinionList;
    }


}
