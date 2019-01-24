import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

class Minion {
    private ArrayList<PVector> wayPoints;
    private int waypointIndex = 0;
    //private ArrayList<Projectile> projectiles = new ArrayList<>();
    private PVector pos;
    private PVector velocity;
    private PVector acceleration;
    private Display d = new Display();
    private PApplet p;
    private float theta;
    private float r;
    private float maxForce;
    private float maxSpeed;
    private int currentHealth;
    private int maxHealth;
    private float fireRate;
    private int range;
    private int damage;
    private boolean player;
    private Stopwatch sw = new Stopwatch();

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
        checkDamage();
        movement();
        seek(giveTarget());
        shoot();
        //d.drawHero(p, pos, theta, r, currentHealth, maxHealth);
        d.drawMinion(p, pos, currentHealth, maxHealth, range);
    }

    boolean checkDead() {
        if (currentHealth <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void checkDamage() {
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

    private void shoot() {
        if (player) {
            PVector target;
            float Distance;
            PVector tTarget = Methods.findTarget(pos, Environment.getAiTowers());
            float tDistance = PVector.dist(tTarget, pos);

            PVector mTarget = Methods.findMinion(pos, Environment.getAiMinions());
            float mDistance = PVector.dist(mTarget, pos);

            PVector hTarget = Environment.getAiHQ().getPos();
            float hDistance = PVector.dist(hTarget, pos);

            if(tDistance <= mDistance) {
                Distance = tDistance;
                target = tTarget;
            } else {
                Distance = mDistance;
                target = mTarget;
            }
            if(hDistance <= Distance) {
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

            if(tDistance <= mDistance) {
                Distance = tDistance;
                target = tTarget;
            } else {
                Distance = mDistance;
                target = mTarget;
            }

            if (Distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                Environment.addAiProjectile(pos, Methods.seek(pos, target), damage);
                sw.reset();
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

    PVector getPos() {
        return pos;
    }

}