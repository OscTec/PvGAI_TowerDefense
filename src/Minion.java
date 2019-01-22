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
    private int maxHealth = 100;
    private float fireRate = 2;
    private int range = 50;
    private boolean player;
    private Stopwatch sw = new Stopwatch();

    Minion(PApplet p, PVector pos, ArrayList points, Boolean player) {
        this.p = p;
        this.wayPoints = points;
        this.player = player;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        this.pos = pos;
        r = 6;
        maxSpeed = 2;
        maxForce = 1f;
        currentHealth = maxHealth;
        if(!player) {
            waypointIndex = points.size()-1;
        }
    }

    void tick(PApplet p) {
        checkDamage();
        movement();
        seek(giveTarget());
        shoot();
        d.drawHero(p, pos, theta, r, currentHealth, maxHealth);
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

    private void shoot() {
        if (player) {
            PVector target = Methods.findTarget(pos, Environment.getAiTowers());
            float distance = PVector.dist(target, pos);
            if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                Environment.addPlayerProjectile(pos, Methods.seek(pos, target));
                sw.reset();
            }
        } else {
            PVector target = Methods.findTarget(pos, Environment.getPlayerTowers());
            float distance = PVector.dist(target, pos);
            if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
                Environment.addAiProjectile(pos, Methods.seek(pos, target));
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
        PVector closestTowerPos;
        if (player) {
            closestTowerPos = Methods.findTarget(pos, Environment.getAiTowers());
        } else {
            closestTowerPos = Methods.findTarget(pos, Environment.getPlayerTowers());
        }
        float distance = PVector.dist(pos, closestTowerPos);
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

}