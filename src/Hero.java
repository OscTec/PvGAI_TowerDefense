import processing.core.PApplet;
import processing.core.PVector;
import java.io.*;
import java.util.ArrayList;

class Hero implements Serializable {
    private ArrayList<PVector> wayPoints;
    private int waypointIndex = 0;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    protected PVector position;
    private PVector velocity;
    private PVector acceleration;
    private Display d = new Display();
    private PApplet p;
    private float theta;
    private float r;
    private float maxForce;
    private float maxSpeed;
    private int currentCoolDown = 50;
    private int maxCoolDown = 50;
    private int currentHealth;
    private int maxHealth = 100;
    //private int health = 100;



    Hero() {
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        position = new PVector(100, 100);
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    Hero(PApplet p, PVector pos, ArrayList points) {
        this.p = p;
        wayPoints = points;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
        //position = new PVector(p.random(p.width), p.random(p.height));
        position = pos;
        r = 6;
        maxSpeed = 2;
        maxForce = 1f;
        currentHealth = maxHealth;
    }

    Hero(PApplet p, PVector pos) {
        this.p = p;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, -2);
        position = pos;
        r = 6;
        maxSpeed = 1;
        maxForce = 0.1f;
    }

    private Hero(PVector position, PVector velocity, float theta) {
        acceleration = new PVector(0, 0);
        this.velocity = velocity;
        this.position = position;
        this.theta = theta;
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    void collisionCheck() {
        boolean hit = false;
        for (Projectile i : Environment.getProjectiles()) {
            //System.out.println(this.position);
            if ((i.position.x <= this.position.x + 10 && i.position.x >= this.position.x - 10) && (i.position.y <= this.position.y + 10 && i.position.y >= this.position.y - 10)) {
                //hit = true;

                currentHealth = currentHealth - i.getDamage();
                System.out.println("Health: " + currentHealth);
                Environment.getProjectiles().remove(i);
                return;
            } else {
                //hit = false;
            }
        }
        //return hit;
    }

    boolean checkDead() {
        if (currentHealth <= 0) {
            return true;
        } else {
            return false;
        }
    }

    void tick(PApplet p, PVector target) {
        collisionCheck();
        movement();
        seek(giveTarget(target));

//        if (currentCoolDown < 0) {
//            shoot();
//            currentCoolDown = maxCoolDown;
//        } else {
//            currentCoolDown--;
//        }

//        for (int i = 0; i < projectiles.size(); i++) {
//            if (!projectiles.get(i).projectileAlive()) {
//                projectiles.remove(i);
//            } else {
//                projectiles.get(i).tick(p);
//            }
//        }
        //d.drawHero(p, position, theta, r);
        d.drawHero(p, position, theta, r, currentHealth, maxHealth);
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        //System.out.println(velocity);
        position.add(velocity);
        acceleration.mult(0);
    }

    void shoot() {
        if (projectiles.size() < 3) {
            Environment.addProjectile(position, velocity);
            //Environment.addProjectile(position, velocity, theta);

//            PVector bPos = (PVector) deepClone(position);
//            PVector bVel = (PVector) deepClone(velocity);
//            Float bTheta = (Float) deepClone(theta);
//            projectiles.add(new Projectile(bPos, bVel, bTheta));
        }
    }

    private void applyForce(PVector force) {
        acceleration.add(force);
    }

    void seek(PVector target) {
        PVector desired = PVector.sub(target, position);
        desired.setMag(maxSpeed);
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    PVector giveTarget(PVector mouse) {
        PVector seekThis = new PVector(p.width / 2, p.height / 2);
        double lowestDistance = Settings.maxDistance;
        if(Settings.chaseMouse == true) {
            seekThis = mouse;
        } else {
            if (Environment.minions.isEmpty()) {
                //return seekThis;
                System.out.println("No minions");
            }

            if (Settings.chasePoints) {
                seekThis = followLane();

//                for (PVector p : wayPoints) {
//                    float d = position.dist(p);
//                    if (d < lowestDistance) {
//                        lowestDistance = d;
//                        seekThis = p;
//
//                    }
//                }
            }
            else {
                for (int i = 0; i < Environment.minions.size(); i++) {
                    float d = position.dist(Environment.minions.get(i).position);
                    if (d < lowestDistance) {
                        lowestDistance = d;
                        seekThis = Environment.minions.get(i).position;
                        System.out.println(i);
                    }
                }

            }

        }
        return seekThis;
    }

    private PVector followLane() {
        if (PVector.dist(position, wayPoints.get(waypointIndex)) < 5f) {
            if (waypointIndex < wayPoints.size() - 1) {
                waypointIndex++;
            }

        }
        return wayPoints.get(waypointIndex);


    }

    PVector getPosition() {
        return position;
    }

}

