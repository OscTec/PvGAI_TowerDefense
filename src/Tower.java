import processing.core.PApplet;
import processing.core.PVector;

import java.io.Serializable;

public class Tower {
    private Display d = new Display();
    private PApplet p;
    private PVector pos;
    private int currentHealth;
    private int maxHealth;
    private int damage;
    private int range;
    private float fireRate;
    private boolean player;
    private Stopwatch sw = new Stopwatch();
    private boolean thisSimulation = false;
    private Simulation sim;


    Tower(PApplet p, PVector pos, int health, int damage, int range, float fireRate) {
        this.p = p;
        this.pos = pos;
        this.maxHealth = health;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.currentHealth = maxHealth;
    }

    //sim tower
    Tower(PApplet p, Simulation sim, PVector pos, boolean player, int currentHealth) {
        this.p = p;
        this.pos = pos;
        this.player = player;
        this.maxHealth = Stats.getTowerHealth();
        this.damage = Stats.getTowerDamage();
        this.range = Stats.getTowerRange();
        this.fireRate = Stats.getTowerAtkSpeed();
        this.currentHealth = currentHealth;
        thisSimulation = true;
        this.sim = sim;
    }

    //normal tower
    Tower(PApplet p, PVector pos, boolean player) {
        this.p = p;
        this.pos = pos;
        this.player = player;
        this.maxHealth = Stats.getTowerHealth();
        this.damage = Stats.getTowerDamage();
        this.range = Stats.getTowerRange();
        this.fireRate = Stats.getTowerAtkSpeed();
        this.currentHealth = maxHealth;
    }

    void tick() {
        checkDamage();
        if (thisSimulation) {
            //d.drawTower(p, pos, currentHealth, maxHealth, range);
        }

        PVector target = setTarget();
        float distance = PVector.dist(target, pos);
        if (thisSimulation) {
            if (player) {
                if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    sim.addLeftProjectile(pos, seek(target), damage);
                    sw.reset();
                }
            } else {
                if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    sim.addRightProjectile(pos, seek(target), damage);
                    sw.reset();
                }
            }
        } else {
            if (player) {
                if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    Environment.addPlayerProjectile(pos, seek(target), damage);
                    sw.reset();
                }
            } else {
                if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    Environment.addAiProjectile(pos, seek(target), damage);
                    sw.reset();
                }
            }
        }

    }

    void drawTower() {
        d.drawTower(p, pos, currentHealth, maxHealth, range);
    }

    private PVector setTarget() {
        PVector seekThis = new PVector(p.width / 2, p.height / 2);
        double lowestDistance = Settings.maxDistance;
        if(thisSimulation) {
            if (player) {
                for (Minion m : sim.rightMinions) {
                    float d = pos.dist(m.getPos());
                    if (d < lowestDistance) {
                        lowestDistance = d;
                        seekThis = m.getPos();
                    }
                }
            } else {
                for (Minion m : sim.leftMinions) {
                    float d = pos.dist(m.getPos());
                    if (d < lowestDistance) {
                        lowestDistance = d;
                        seekThis = m.getPos();

                    }
                }
            }
        } else {


            if (player) {
                for (Minion m : Environment.getAiMinions()) {
                    float d = pos.dist(m.getPos());
                    if (d < lowestDistance) {
                        lowestDistance = d;
                        seekThis = m.getPos();
                    }
                }
            } else {
                for (Minion m : Environment.getPlayerMinions()) {
                    float d = pos.dist(m.getPos());
                    if (d < lowestDistance) {
                        lowestDistance = d;
                        seekThis = m.getPos();

                    }
                }
            }
        }
        return seekThis;
    }

//    PVector setTarget(PVector pos, ArrayList<Hero> target) {
//        PVector seekThis = new PVector(p.width / 2, p.height / 2);
//        double lowestDistance = Settings.maxDistance;
//        for (Hero h : target) {
//            float d = pos.dist(h.position);
//            if (d < lowestDistance) {
//                lowestDistance = d;
//                seekThis = h.position;
//
//            }
//        }
//        return seekThis;
//
//    }

    private PVector seek(PVector target) {
        //PVector desired = PVector.sub(target, pos);
        //desired.setMag(maxSpeed);
        //PVector steer = PVector.sub(desired, velocity);
        //steer.limit(maxForce);
        //applyForce(steer);
        //return desired;
        return PVector.sub(target, pos);
    }

    boolean checkDead() {
        if (currentHealth <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void checkDamage() {
        if(thisSimulation) {
            if (player) {
                Projectile hit = Methods.collisionCheck(pos, sim.rightProjectiles);
                if (hit != null) {
                    currentHealth = currentHealth - hit.getDamage();
                    sim.rightProjectiles.remove(hit);
                }
            } else {
                Projectile hit = Methods.collisionCheck(pos, sim.leftProjectiles);
                if (hit != null) {
                    currentHealth = currentHealth - hit.getDamage();
                    sim.leftProjectiles.remove(hit);
                }
            }
        } else {
            if (player) {
                Projectile hit = Methods.collisionCheck(pos, Environment.getAiProjectiles());
                if (hit != null) {
                    currentHealth = currentHealth - hit.getDamage();
                    Environment.getAiProjectiles().remove(hit);
                }
            } else {
                Projectile hit = Methods.collisionCheck(pos, Environment.getPlayerProjectiles());
                if (hit != null) {
                    currentHealth = currentHealth - hit.getDamage();
                    Environment.getPlayerProjectiles().remove(hit);
                }
            }
        }

    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean getPlayer() {return player;}

    PVector getPos() {return pos;}

}


