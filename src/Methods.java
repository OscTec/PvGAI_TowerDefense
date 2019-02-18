import processing.core.PApplet;
import processing.core.PVector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Methods {

    protected static PVector findMinion(PVector pos, ArrayList<Minion> targets) {
        PVector seekThis = new PVector();
        double lowestDistance = Settings.maxDistance;
        for (Minion m : targets) {
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

    public static boolean areAllTrue(ArrayList<Simulation> array) {
        for (Simulation b : array) if (!b.simFinished()) return false;
        return true;
    }


    public static ArrayList<Minion> buildScoredMinions(ArrayList<Minion> minList) {
        int scoreTotal = 0;
        ArrayList<Minion> sortedMinions = new ArrayList<>();
        for (Minion m : minList) {
            scoreTotal += m.getDamageDealt();
        }
        for (Minion m : minList) {
            m.setFitness((m.getDamageDealt() / scoreTotal) * 100);
        }
        for (Minion m : minList) {
            for (int x = 0; x <= m.getFitness(); x++) {
                sortedMinions.add(m);
            }
        }
        return sortedMinions;
    }

    public static Minion breedMinions(PApplet p, Minion m1, Minion m2) {
        int newHealth = ((int) deepClone(m1.getHealth()) + (int) deepClone(m2.getHealth())) / 2;
        int newSpeed = ((int) deepClone(m1.getSpeed()) + (int) deepClone(m2.getSpeed())) / 2;
        int newRange = ((int) deepClone(m1.getRange()) + (int) deepClone(m2.getRange())) / 2;
        int newDamage = ((int) deepClone(m1.getDamage()) + (int) deepClone(m2.getDamage())) / 2;
        float newAtkSpeed = ((float) deepClone(m1.getAtkSpeed()) + (float) deepClone(m2.getAtkSpeed())) / 2;


        Minion offSpring = new Minion(p, newHealth, newSpeed, newRange, newDamage, newAtkSpeed);
        return offSpring;
    }

    static void mutateMinions(PApplet p, ArrayList<Minion> minList, float mutLevel) {
        for (Minion m : minList) {

            int pointsChanged = 0;
            int pointsToChange = (int) Math.floor(25 / mutLevel);
            //boolean statChanged = false;
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
                    m.setAtkSpeed(m.getAtkSpeed() + 10);
                    pointsChanged--;
                }
            }


        }

    }

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

    public static Minion copyMinion(PApplet p, Minion m) {
        int bMaxHealth = (int) deepClone(m.getHealth());
        int bMaxSpeed = (int) deepClone(m.getSpeed());
        int bRange = (int) deepClone(m.getRange());
        int bDamage = (int) deepClone(m.getDamage());
        float bFireRate = (float) deepClone(m.getAtkSpeed());
        int bDamageDealt = (int) deepClone(m.getDamageDealt());

        return new Minion(p, bMaxHealth, bMaxSpeed, bRange, bDamage, bFireRate, bDamageDealt);
    }

    static ArrayList<Minion> copyMinions(PApplet p, ArrayList<Minion> ListOfMinions) {
        ArrayList<Minion> newMinionList = new ArrayList<>();
        for(Minion m: ListOfMinions) {
            newMinionList.add(copyMinion(p, m));
        }
        return newMinionList;
    }


}
