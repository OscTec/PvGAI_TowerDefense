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
    private int currentHealth;
    private int maxHealth = 100;
    private float fireRate = 2;
    private int range = 50;
    private Stopwatch sw = new Stopwatch();

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


    void tick(PApplet p, PVector target) {
        checkDamage();
        movement();
        seek(giveTarget(target));
        shoot();
        d.drawHero(p, position, theta, r, currentHealth, maxHealth);
    }

    boolean checkDead() {
        if (currentHealth <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void checkDamage() {
        Projectile hit = Methods.collisionCheck(position, Environment.getAiProjectiles());
        if (hit != null) {
            currentHealth = currentHealth - hit.getDamage();
            Environment.getAiProjectiles().remove(hit);
        }
    }





    void shoot() {
        PVector target = Methods.findTarget(position, Environment.getAiTowers());
        float distance = PVector.dist(target, position);
        if (distance <= range && sw.elapsedTime() >= 1 / fireRate) {
            Environment.addPlayerProjectile(position, Methods.seek(position, target));
            sw.reset();
        }
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        //System.out.println(velocity);
        position.add(velocity);
        acceleration.mult(0);
    }

    private void seek(PVector target) {
        PVector desired = PVector.sub(target, position);
        desired.setMag(maxSpeed);
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    private void applyForce(PVector force) {
        acceleration.add(force);
    }



    private PVector giveTarget(PVector mouse) {
        PVector seekThis = new PVector(p.width / 2, p.height / 2);
        double lowestDistance = Settings.maxDistance;
//        if (Settings.chaseMouse == true) {
//            seekThis = mouse;
//        }

        PVector closestTowerPos = Methods.findTarget(position, Environment.getAiTowers());
        float distance = PVector.dist(position, closestTowerPos);
        if (distance <= range) {
            //Environment.addPlayerProjectile(position, Methods.seek(position, closestTowerPos));
            //System.out.println("Tower in range");
            seekThis = position;
            //sw.reset();
            //Projectile(pos, seek(target));
        } else {
//            if (Environment.minions.isEmpty()) {
//                //return seekThis;
//                System.out.println("No minions");
//            }

            if (Settings.chasePoints) {
                seekThis = followLane();
            }
//            else {
//                for (int i = 0; i < Environment.minions.size(); i++) {
//                    float d = position.dist(Environment.minions.get(i).position);
//                    if (d < lowestDistance) {
//                        lowestDistance = d;
//                        seekThis = Environment.minions.get(i).position;
//                        System.out.println(i);
//                    }
//                }
//
//            }

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


//    void collisionCheck() {
//        //boolean hit = false;
//        for (Projectile i : Environment.getAiProjectiles()) {
//            //System.out.println(this.position);
//            if ((i.position.x <= this.position.x + 10 && i.position.x >= this.position.x - 10) && (i.position.y <= this.position.y + 10 && i.position.y >= this.position.y - 10)) {
//                //hit = true;
//
//                currentHealth = currentHealth - i.getDamage();
//                System.out.println("Health: " + currentHealth);
//                Environment.getAiProjectiles().remove(i);
//                return;
//            }
//        }
//
//    }

    //    void shoot() {
//        if (projectiles.size() < 3) {
//            Environment.addProjectile(position, velocity);
//            //Environment.addProjectile(position, velocity, theta);
//
//            PVector bPos = (PVector) deepClone(position);
//            PVector bVel = (PVector) deepClone(velocity);
//            Float bTheta = (Float) deepClone(theta);
//            projectiles.add(new Projectile(bPos, bVel, bTheta));
//        }
//    }

}

