import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Random;

class Minion {
    private ArrayList<PVector> wayPoints;
    private int waypointIndex = 0;
    private int damageDealt = 0;
    private int shotsFired = 0;
    //private ArrayList<Projectile> projectiles = new ArrayList<>();
    private PVector pos;
    private PVector velocity;
    private PVector acceleration;
    private Display d = new Display();
    private PApplet p;
    private float theta;
    private float r;
    private float maxForce;
    //private int maxSpeed;
    private int currentHealth;
    //private int maxHealth;
    //private float fireRate;
    //private int range;
    //private int damage;
    private boolean player;
    private Stopwatch sw = new Stopwatch();

    //minion stats
    private int dmgPoints = 1;
    private int hltPoints = 1;
    private int rngPoints = 1;
    private int spdPoints = 1;
    private int atsPoints = 1;


    private boolean thisSimulation = false;

    private int pointsUsed = 0;
    private int maxPoints = 25;

    private int tickCounter = 0;
    private int lastShotTick = 0;
    //private ArrayList<Projectile> leftPro;

    //private ArrayList<Projectile> rightPro;
    private Simulation sim;
    private float fitness;
    //To make copy

    /*
    Minion(PApplet p, int maxHealth, int maxSpeed, int range, int damage, float fireRate){
        this.p = p;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.range = range;
        this.damage = damage;
        this.fireRate = fireRate;

    }

    Minion(PApplet p, int maxHealth, int maxSpeed, int range, int damage, float fireRate, int damageDealt){
        this.p = p;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.range = range;
        this.damage = damage;
        this.fireRate = fireRate;
        this.damageDealt = damageDealt;
    }

    Minion(PApplet p, PVector pos, ArrayList points, Simulation sim, boolean player) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        maxForce = 1f;
        this.sim = sim;
        thisSimulation = true;
        if(player) {
            this.maxHealth = Stats.getPlayerMinionHealth();
            this.maxSpeed = Stats.getPlayerMinionSpeed();
            this.range = Stats.getPlayerMinionRange();
            this.damage = Stats.getPlayerMinionDamage();
            this.fireRate = Stats.getPlayerMinionAtkSpeed();
        }
        if(!player) {

            //maxHealth = 0;
            maxSpeed = 1;
            fireRate = 1;
            damage = 10;
            maxHealth = 10;
            range = 10;
            assignPoints();
            waypointIndex = points.size()-1;
        }
        currentHealth = maxHealth;
    }

    Minion(PApplet p, PVector pos, ArrayList points, Simulation sim, boolean player, int health, int speed, float fireRate, int range, int damage) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        maxForce = 1f;
        this.sim = sim;
        thisSimulation = true;
        if(player) {
            this.maxHealth = Stats.getPlayerMinionHealth();
            this.maxSpeed = Stats.getPlayerMinionSpeed();
            this.range = Stats.getPlayerMinionRange();
            this.damage = Stats.getPlayerMinionDamage();
            this.fireRate = Stats.getPlayerMinionAtkSpeed();
        }
        if(!player) {

            //maxHealth = health;
            this.maxSpeed = speed;
            this.fireRate = fireRate;
            this.damage = damage;
            this.maxHealth = health;
            this.range = range;
            //assignPoints();
            waypointIndex = points.size()-1;
        }
        currentHealth = maxHealth;
    }

    Minion(PApplet p, PVector pos, ArrayList points, boolean player) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        //maxSpeed = 2;
        maxForce = 1f;

        if(player) {
            this.maxHealth = Stats.getPlayerMinionHealth();
            this.maxSpeed = Stats.getPlayerMinionSpeed();
            this.range = Stats.getPlayerMinionRange();
            this.damage = Stats.getPlayerMinionDamage();
            this.fireRate = Stats.getPlayerMinionAtkSpeed();
        }
        if(!player) {
            this.maxHealth = Stats.getAiMinionHealth();
            this.maxSpeed = Stats.getAiMinionSpeed();
            this.range = Stats.getAiMinionRange();
            this.damage = Stats.getAiMinionDamage();
            this.fireRate = Stats.getAiMinionAtkSpeed();
            waypointIndex = points.size()-1;
        }
        currentHealth = maxHealth;
    }

    */

    Minion(PApplet p, int hltPoints, int spdPoints, int rngPoints, int dmgPoints, int atsPoints){
        this.p = p;
        this.hltPoints = hltPoints;
        this.spdPoints = spdPoints;
        this.rngPoints = rngPoints;
        this.dmgPoints = dmgPoints;
        this.atsPoints = atsPoints;
        currentHealth = hltPoints * Stats.getHltInc();
    }

    Minion(PApplet p, int hltPoints, int spdPoints, int rngPoints, int dmgPoints, int atsPoints, int damageDealt){
        this.p = p;
        this.hltPoints = hltPoints;
        this.spdPoints = spdPoints;
        this.rngPoints = rngPoints;
        this.dmgPoints = dmgPoints;
        this.atsPoints = atsPoints;
        this.damageDealt = damageDealt;
        currentHealth = hltPoints * Stats.getHltInc();
    }

    Minion(PApplet p, PVector pos, ArrayList points, Simulation sim, boolean player) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        maxForce = 1f;
        this.sim = sim;
        thisSimulation = true;
        if(player) {
            this.hltPoints = Stats.getpHltPoints();
            this.spdPoints = Stats.getpSpdPoints();
            this.rngPoints = Stats.getpRngPoints();
            this.dmgPoints = Stats.getpDmgPoints();
            this.atsPoints = Stats.getpAtsPoints();
        }
        if(!player) {

            //maxHealth = 0;
            spdPoints = 1;
            atsPoints = 1;
            dmgPoints = 1;
            hltPoints = 1;
            rngPoints = 1;
            pointsUsed = 5;
            assignPoints();
            waypointIndex = points.size()-1;
        }
        currentHealth = (hltPoints*Stats.getHltInc());
    }

    Minion(PApplet p, PVector pos, ArrayList points, Simulation sim, boolean player, int hltPoints, int spdPoints, int atsPoints, int rngPoints, int dmgPoints) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        maxForce = 1f;
        this.sim = sim;
        thisSimulation = true;
        if(player) {
            this.hltPoints = hltPoints;
            this.spdPoints = spdPoints;
            this.atsPoints = atsPoints;
            this.rngPoints = rngPoints;
            this.dmgPoints = dmgPoints;
        }
        if(!player) {
            this.hltPoints = hltPoints;
            this.spdPoints = spdPoints;
            this.atsPoints = atsPoints;
            this.rngPoints = rngPoints;
            this.dmgPoints = dmgPoints;
            waypointIndex = points.size()-1;
        }
        currentHealth = (hltPoints*Stats.getHltInc());
    }
    //Real minions
    Minion(PApplet p, PVector pos, ArrayList points, boolean player) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        //maxSpeed = 2;
        maxForce = 1f;

        if(player) {
            this.hltPoints = Stats.getpHltPoints();
            this.spdPoints = Stats.getpSpdPoints();
            this.atsPoints = Stats.getpAtsPoints();
            this.rngPoints = Stats.getpRngPoints();
            this.dmgPoints = Stats.getpDmgPoints();

        }
        if(!player) {
            this.hltPoints = Stats.getAiHltPoints();
            this.spdPoints = Stats.getAiSpdPoints();
            this.atsPoints = Stats.getAiAtsPoints();
            this.rngPoints = Stats.getAiRngPoints();
            this.dmgPoints = Stats.getAiDmgPoints();
            //System.out.println(hltPoints + " " + spdPoints + " " + atsPoints + " " + rngPoints + " " + dmgPoints);
            waypointIndex = points.size()-1;
        }
        currentHealth = (hltPoints*Stats.getHltInc());
    }

    void tick(PApplet p) {
        if(thisSimulation) {
            tickCounter++;
        }
        checkDamage();
        movement();
        seek(giveTarget());
        shoot();
    }

    void drawMinion() {
        d.drawMinion(p, pos, currentHealth, (hltPoints*Stats.getHltInc()), (rngPoints*Stats.getRngInc()));
    }

    boolean checkDead() {
        if (currentHealth <= 0 || tickCounter >= 100000) {
            if(thisSimulation && !player) {
                damageDealt = dmgPoints*shotsFired*Stats.getDmgInc();
                //damageDealt = damage*shotsFired;
                //System.out.println("Tick till dead: " + tickCounter);
                System.out.println("Damage dealt: " + damageDealt + " " +  hltPoints + " " + dmgPoints + " " + spdPoints + " "  + rngPoints + " " + atsPoints);
            }
            return true;
        } else {
            damageDealt = dmgPoints*shotsFired*Stats.getDmgInc();
            //damageDealt = damage*shotsFired;
            return false;
        }
    }

    private void checkDamage() {
        if(thisSimulation) {
            if (player) {
                Projectile hit = Methods.collisionCheck(pos, sim.rightProjectiles);
                if (hit != null) {
                    //System.out.println("Player minion hit");

                    //System.out.println("Minion's health " + currentHealth + " damage done " + hit.getDamage() + " ID " + r);
                    currentHealth = currentHealth - hit.getDamage();
                    sim.rightProjectiles.remove(hit);
                }
            } else {
                Projectile hit = Methods.collisionCheck(pos, sim.leftProjectiles);
                if (hit != null) {
                    //System.out.println("AI minion hit");
                    currentHealth = currentHealth - hit.getDamage();
                    sim.leftProjectiles.remove(hit);
                }
            }


        } else {
            if (player) {
                Projectile hit = Methods.collisionCheck(pos, Environment.getAiProjectiles());
                if (hit != null) {
                    //System.out.println("Player minion hit");
                    //float r = p.random(2000);
                    //System.out.println("Minion's health " + currentHealth + " damage done " + hit.getDamage() + " ID " + r);
                    currentHealth = currentHealth - hit.getDamage();
                    Environment.getAiProjectiles().remove(hit);
                }
            } else {
                Projectile hit = Methods.collisionCheck(pos, Environment.getPlayerProjectiles());
                if (hit != null) {
                    //System.out.println("AI minion hit");
                    currentHealth = currentHealth - hit.getDamage();
                    Environment.getPlayerProjectiles().remove(hit);
                }
            }
        }

    }

    private void shoot() {
        if(thisSimulation) {
            if (player) {
                PVector target;
                float Distance;
                PVector tTarget = Methods.findTarget(pos, sim.rightTowers);
                float tDistance = PVector.dist(tTarget, pos);

                PVector mTarget = Methods.findMinion(pos, sim.rightMinions);
                float mDistance = PVector.dist(mTarget, pos);

                PVector hTarget = sim.rightHQ.getPos();
                float hDistance = PVector.dist(hTarget, pos);

                if (tDistance <= mDistance) {
                    Distance = tDistance;
                    target = tTarget;
                } else {
                    Distance = mDistance;
                    target = mTarget;
                }
                if (hDistance <= Distance) {
                    Distance = hDistance;
                    target = hTarget;
                    currentHealth = 0;
                }

                if (Distance <= (rngPoints*Stats.getRngInc()) && (lastShotTick + (120/(atsPoints*Stats.getAtsInc())) <= tickCounter || lastShotTick == 0)) {
                    shotsFired++;
                    lastShotTick = tickCounter;
                    //Environment.addPlayerProjectile(pos, Methods.seek(pos, target), damage);
                    sim.addLeftProjectile(pos, Methods.seek(pos, target), dmgPoints*Stats.getDmgInc());
                    sw.reset();
                }
                /* old way tied to seconds
                if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    shotsFired++;
                    //Environment.addPlayerProjectile(pos, Methods.seek(pos, target), damage);
                    sim.addLeftProjectile(pos, Methods.seek(pos, target), damage);
                    sw.reset();
                }
                */
            } else {
                PVector target;
                float Distance;
                PVector tTarget = Methods.findTarget(pos, sim.leftTowers);
                float tDistance = PVector.dist(tTarget, pos);

                PVector mTarget = Methods.findMinion(pos, sim.leftMinions);
                float mDistance = PVector.dist(mTarget, pos);

                PVector hTarget = sim.leftHQ.getPos();
                float hDistance = PVector.dist(hTarget, pos);

                if (tDistance <= mDistance) {
                    Distance = tDistance;
                    target = tTarget;
                } else {
                    Distance = mDistance;
                    target = mTarget;
                }
                if (hDistance <= Distance) {
                    Distance = hDistance;
                    target = hTarget;
                    currentHealth = 0;
                }
                if (Distance <= (rngPoints*Stats.getRngInc()) && (lastShotTick + (120/(atsPoints*Stats.getAtsInc())) <= tickCounter || lastShotTick == 0)) {
                    shotsFired++;
                    lastShotTick = tickCounter;
                    //Environment.addPlayerProjectile(pos, Methods.seek(pos, target), damage);
                    sim.addRightProjectile(pos, Methods.seek(pos, target), dmgPoints*Stats.getDmgInc());
                    sw.reset();
                }
                /*
                if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    shotsFired++;
                    //Environment.addAiProjectile(pos, Methods.seek(pos, target), damage);
                    sim.addRightProjectile(pos, Methods.seek(pos, target), damage);
                    sw.reset();
                }
                */
            }
        //This is for none sims
        } else {
            if (player) {
                PVector target;
                float Distance;
                PVector tTarget = Methods.findTarget(pos, Environment.getAiTowers());
                float tDistance = PVector.dist(tTarget, pos);

                PVector mTarget = Methods.findMinion(pos, Environment.getAiMinions());
                float mDistance = PVector.dist(mTarget, pos);

                PVector hTarget = Environment.getAiHQ().getPos();
                float hDistance = PVector.dist(hTarget, pos);

                if (tDistance <= mDistance) {
                    Distance = tDistance;
                    target = tTarget;
                } else {
                    Distance = mDistance;
                    target = mTarget;
                }
                if (hDistance <= Distance) {
                    Distance = hDistance;
                    target = hTarget;
                }

                if (Distance <= (rngPoints*Stats.getRngInc()) && sw.elapsedTime() >= 2 / ((atsPoints+0.1)*Stats.getAtsInc())) {
                    Environment.addPlayerProjectile(pos, Methods.seek(pos, target), dmgPoints*Stats.getDmgInc());
                    sw.reset();
                    System.out.println("Best Minion has: Health " + hltPoints + " Damage " + dmgPoints + " Speed " + spdPoints + " Range " + rngPoints + " Atk Speed " + atsPoints);
                    //System.out.println(atsPoints);
                }
            } else {
                PVector target;
                float Distance;
                PVector tTarget = Methods.findTarget(pos, Environment.getPlayerTowers());
                float tDistance = PVector.dist(tTarget, pos);

                PVector mTarget = Methods.findMinion(pos, Environment.getPlayerMinions());
                float mDistance = PVector.dist(mTarget, pos);

                PVector hTarget = Environment.getPlayerHQ().getPos();
                float hDistance = PVector.dist(hTarget, pos);

                if (tDistance <= mDistance) {
                    Distance = tDistance;
                    target = tTarget;
                } else {
                    Distance = mDistance;
                    target = mTarget;
                }
                if (hDistance <= Distance) {
                    Distance = hDistance;
                    target = hTarget;
                }


                if (Distance <= (rngPoints*Stats.getRngInc()) && sw.elapsedTime() >= 2 / ((atsPoints+0.1)*Stats.getAtsInc())) {
                    shotsFired++;
                    Environment.addAiProjectile(pos, Methods.seek(pos, target), dmgPoints*Stats.getDmgInc());
                    sw.reset();
                    System.out.println(atsPoints);
                }
            }
        }
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(spdPoints*Stats.getSpdInc());
        pos.add(velocity);
        acceleration.mult(0);
    }

    private void seek(PVector target) {
        PVector desired = PVector.sub(target, pos);
        desired.setMag(spdPoints*Stats.getSpdInc());
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }



    private void applyForce(PVector force) {
        acceleration.add(force);
    }

    private PVector giveTarget() {
        PVector seekThis = new PVector(p.width / 2, p.height / 2);
        //double lowestDistance = Settings.maxDistance;
        PVector closestMinionPos;
        PVector closestTowerPos;
        PVector closestTarget;
        if(thisSimulation) {
            if (player) {
                closestTowerPos = Methods.findTarget(pos, sim.rightTowers);
                closestMinionPos = Methods.findMinion(pos, sim.rightMinions);
                if(pos.dist(closestTowerPos) <= pos.dist(closestMinionPos)) {
                    closestTarget = closestTowerPos;
                } else {
                    closestTarget = closestMinionPos;
                }
                if(pos.dist(Environment.getAiHQ().getPos()) <= pos.dist(closestTarget)){
                    closestTarget = Environment.getAiHQ().getPos();
                }
            } else {
                closestTowerPos = Methods.findTarget(pos, sim.leftTowers);
                closestMinionPos = Methods.findMinion(pos, sim.leftMinions);
                if(pos.dist(closestTowerPos) <= pos.dist(closestMinionPos)) {
                    closestTarget = closestTowerPos;
                } else {
                    closestTarget = closestMinionPos;
                }
                if(pos.dist(Environment.getPlayerHQ().getPos()) <= pos.dist(closestTarget)){
                    closestTarget = Environment.getPlayerHQ().getPos();
                }
            }
        } else {
            if (player) {
                closestTowerPos = Methods.findTarget(pos, Environment.getAiTowers());
                closestMinionPos = Methods.findMinion(pos, Environment.getAiMinions());
                if(pos.dist(closestTowerPos) <= pos.dist(closestMinionPos)) {
                    closestTarget = closestTowerPos;
                } else {
                    closestTarget = closestMinionPos;
                }
                if(pos.dist(Environment.getAiHQ().getPos()) <= pos.dist(closestTarget)){
                    closestTarget = Environment.getAiHQ().getPos();
                }
            } else {
                closestTowerPos = Methods.findTarget(pos, Environment.getPlayerTowers());
                closestMinionPos = Methods.findMinion(pos, Environment.getPlayerMinions());
                if(pos.dist(closestTowerPos) <= pos.dist(closestMinionPos)) {
                    closestTarget = closestTowerPos;
                } else {
                    closestTarget = closestMinionPos;
                }
                if(pos.dist(Environment.getPlayerHQ().getPos()) <= pos.dist(closestTarget)){
                    closestTarget = Environment.getPlayerHQ().getPos();
                }
            }
        }

        float distance = PVector.dist(pos, closestTarget);
        if (distance <= (rngPoints*Stats.getRngInc())) {
            seekThis = pos;
        } else {
            if (Settings.chasePoints) {
                seekThis = followLane();
            }
        }
        return seekThis;
    }

    private PVector followLane() {
        if (player) {
            if (PVector.dist(pos, wayPoints.get(waypointIndex)) < 5f) {
                if (waypointIndex < wayPoints.size() - 1) {
                    //System.out.println(waypointIndex);
                    waypointIndex++;
                }
            }
        } else {
            if (PVector.dist(pos, wayPoints.get(waypointIndex)) < 5f) {
                if (waypointIndex > 0) {
                    waypointIndex--;
                }
            }
        }
        return wayPoints.get(waypointIndex);
    }
    private void assignPoints() {
        while(pointsUsed < maxPoints) {
            Random rand = new Random();
            int r = rand.nextInt(69);
            //float r = p.random(69);
            if(r < 5 && spdPoints + 1 <= 5) {//Minion Speed
                spdPoints++;
                //maxSpeed = maxSpeed + 1;
                pointsUsed++;
            }
            if(5 <= r && r < 25 && hltPoints + 1 <= 20) {//Minion Health
                hltPoints++;
                //maxHealth = maxHealth + 10;
                pointsUsed++;
            }
            if(25 <= r && r < 45 && dmgPoints + 1 <= 20) {// Minion Damage
                dmgPoints++;
                //damage = damage + 10;
                pointsUsed++;
            }
            if(45 <= r && r < 65 && rngPoints + 1 <= 20) {// Minion Range
                rngPoints++;
                //range = range + 10;
                pointsUsed++;
            }
            if(65 <= r && r < 68 && atsPoints + 1 < 3) {//Minion atk speed
                atsPoints++;
                //fireRate = fireRate + 1;
                pointsUsed++;
            }
        }
    }
    /*
    private void assignPoints() {
        while(pointsUsed < maxPoints) {
            float r = p.random(76);
            if(r < 5 && Stats.getAiMinionSpeed() + 1 <= 5) {//Minion Speed
                spdPoints++;
                //maxSpeed = maxSpeed + 1;
                pointsUsed++;
            }
            if(5 <= r && r < 25 && Stats.getAiMinionHealth() + 10 <= 200) {//Minion Health
                hltPoints++;
                //maxHealth = maxHealth + 10;
                pointsUsed++;
            }
            if(25 <= r && r < 45 && Stats.getAiMinionDamage() + 10 <= 200) {// Minion Damage
                dmgPoints++;
                //damage = damage + 10;
                pointsUsed++;
            }
            if(45 <= r && r < 65 && Stats.getAiMinionRange() + 10 <= 200) {// Minion Range
                rngPoints++;
                //range = range + 10;
                pointsUsed++;
            }
            if(65 <= r && r < 75 && Stats.getAiMinionAtkSpeed() + 1 < 10) {//Minion atk speed
                atsPoints++;
                //fireRate = fireRate + 1;
                pointsUsed++;
            }
        }
    }
    */

    //void setHealth(int value) {maxHealth = value;}
    //void setDamage(int value) {damage = value;}
    //void setMaxSpeed(int value) {maxSpeed = value;}
    //void setRange(int value) {range = value;}
    //void setAtkSpeed(float value) {fireRate = value;}



    //int getHealth() {return maxHealth;}

    //int getSpeed() {return maxSpeed;}

    //int getRange() {return range;}

    //int getDamage() {return damage;}

    //float getAtkSpeed() {return fireRate;}

    void setFitness(float value) {fitness = value;}

    float getFitness() {return fitness;}

    void resetDamageDealt() {damageDealt = 0;}

    PVector getPos() { return pos; }

    int getDamageDealt() { return damageDealt;}

    int getDmgPoints() { return dmgPoints;}
    void setDmgPoints(int value) {dmgPoints = value;}
    int getHltPoints() { return hltPoints;}
    void setHltPoints(int value) {hltPoints = value;}
    int getRngPoints() { return rngPoints;}
    void setRngPoints(int value) {rngPoints = value;}
    int getAtsPoints() { return atsPoints;}
    void setAtsPoints(int value) {atsPoints = value;}
    int getSpdPoints() { return spdPoints;}
    void setSpdPoints(int value) {spdPoints = value;}



}