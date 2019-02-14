import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

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
    private int maxSpeed;
    private int currentHealth;
    private int maxHealth;
    private float fireRate;
    private int range;
    private int damage;
    private boolean player;
    private Stopwatch sw = new Stopwatch();
    private boolean thisSimulation = false;

    private int pointsUsed = 0;
    private int maxPoints = 25;
    //private ArrayList<Projectile> leftPro;

    //private ArrayList<Projectile> rightPro;
    private Simulation sim;
    private float fitness;
    //To make copy
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
    //right side minions
    Minion(PApplet p, PVector pos ,ArrayList<PVector> points, Simulation sim, int maxHealth, int maxSpeed, int range, int damage, float fireRate){
        this.p = p;
        this.wayPoints = points;
        this.pos = pos;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.range = range;
        this.damage = damage;
        this.fireRate = fireRate;
        //this.leftPro = sim.leftProjectiles;
        //this.rightPro = sim.rightProjectiles;
        waypointIndex = points.size()-1;
        player = false;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        r = 6;
        maxForce = 1f;
        thisSimulation = true;
        this.sim = sim;
        currentHealth = maxHealth;
        System.out.println(currentHealth);
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

            maxHealth = 0;
            maxSpeed = 1;
            fireRate = 1;
            damage = 10;
            maxHealth = 10;
            assignPoints();
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

    void tick(PApplet p) {
        if(thisSimulation) {
            //System.out.println(pos);
            //System.out.println(pos);
            d.drawMinion(p, pos, currentHealth, maxHealth, range);
        }

        checkDamage();
        movement();
        seek(giveTarget());
        shoot();
        //d.drawHero(p, pos, theta, r, currentHealth, maxHealth);
        //d.drawMinion(p, pos, currentHealth, maxHealth, range);
    }

    void drawMinion() {
        d.drawMinion(p, pos, currentHealth, maxHealth, range);
    }

    boolean checkDead() {
        if (currentHealth <= 0) {
            if(thisSimulation && !player) {
                damageDealt = damage*shotsFired;
                System.out.println("Damage dealt: " + damageDealt + " " +  maxHealth + " " + damage + " " + maxSpeed + " "  + range + " " + fireRate);
            }
            return true;
        } else {
            damageDealt = damage*shotsFired;
            return false;
        }
    }

    private void checkDamage() {
        if(thisSimulation) {
            if (player) {
                Projectile hit = Methods.collisionCheck(pos, sim.rightProjectiles);
                if (hit != null) {
                    //System.out.println("Player minion hit");
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
                }

                if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    shotsFired++;
                    //Environment.addPlayerProjectile(pos, Methods.seek(pos, target), damage);
                    sim.addLeftProjectile(pos, Methods.seek(pos, target), damage);
                    sw.reset();
                }
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
                }
                if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    shotsFired++;
                    //Environment.addAiProjectile(pos, Methods.seek(pos, target), damage);
                    sim.addRightProjectile(pos, Methods.seek(pos, target), damage);
                    sw.reset();
                }
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

                if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    Environment.addPlayerProjectile(pos, Methods.seek(pos, target), damage);
                    sw.reset();
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


                if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                    shotsFired++;
                    Environment.addAiProjectile(pos, Methods.seek(pos, target), damage);
                    sw.reset();
                }
            }
        }
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        pos.add(velocity);
        acceleration.mult(0);
    }

    private void seek(PVector target) {
        PVector desired = PVector.sub(target, pos);
        desired.setMag(maxSpeed);
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
        float distance = PVector.dist(pos, closestTarget);
        if (distance <= range) {
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

    void assignPoints() {
        while(pointsUsed < maxPoints) {
            float r = p.random(76);
            if(r < 5 && Stats.getAiMinionSpeed() + 1 <= 5) {//Minion Speed
                maxSpeed = maxSpeed + 1;
                pointsUsed++;
            }
            if(5 <= r && r < 25 && Stats.getAiMinionHealth() + 10 <= 200) {//Minion Health
                maxHealth = maxHealth + 10;
                pointsUsed++;
            }
            if(25 <= r && r < 45 && Stats.getAiMinionDamage() + 10 <= 200) {// Minion Damage
                damage = damage + 10;
                pointsUsed++;
            }
            if(45 <= r && r < 65 && Stats.getAiMinionRange() + 10 <= 200) {// Minion Range
                range = range + 10;
                pointsUsed++;
            }
            if(65 <= r && r < 75 && Stats.getAiMinionAtkSpeed() + 1 < 10) {//Minion atk speed
                fireRate = fireRate + 1;
                pointsUsed++;
            }
        }
    }

    

    int getHealth() {return maxHealth;}

    int getSpeed() {return maxSpeed;}

    int getRange() {return range;}

    int getDamage() {return damage;}

    float getAtkSpeed() {return fireRate;}

    void setFitness(float value) {fitness = value;}

    float getFitness() {return fitness;}


    PVector getPos() {
        return pos;
    }

    int getDamageDealt() { return damageDealt;}

}