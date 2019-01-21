import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Minion {
    private Display d = new Display();
    int currentHealth = 100;
    int maxHealth = 100;
    int speed = 1;
    private ArrayList<PVector> points;
    private int waypointIndex = 0;
    PVector position;
    private PVector velocity;
    private PVector acceleration;
    private float r;
    private PApplet p;
    private float maxForce;
    private float maxSpeed;
    private float theta;

    Minion(PApplet p) {
        this.p = p;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, -2);
        position = new PVector(p.random(p.width), p.random(p.height));
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    Minion(PApplet p, PVector pos) {
        this.p = p;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, -2);
        position = pos;
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    Minion(PApplet p, PVector pos, ArrayList lanePoints) {
        this.p = p;
        position = pos;
        points = lanePoints;

    }

    void tick(PApplet p) {
        //movement();
        d.drawEnemy(p, position, theta, r, currentHealth, maxHealth);
        followLane();
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        position.add(velocity);
        acceleration.mult(0);
    }

    private void followLane() {
        if (PVector.dist(position, points.get(waypointIndex)) < 0.4f) {
            waypointIndex++;
        }

    }

    void seek(PVector target) {
        PVector desired = PVector.sub(target, position);
        desired.setMag(maxSpeed);
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    boolean collisionCheck() {
        boolean hit = false;
        for (Projectile i : Environment.getProjectiles()) {
            //System.out.println(this.position);
            if ((i.position.x <= this.position.x + 10 && i.position.x >= this.position.x - 10) && (i.position.y <= this.position.y + 10 && i.position.y >= this.position.y - 10)) {
                hit = true;
            } else {
                hit = false;
            }
        }
        return hit;
    }

    private void applyForce(PVector force) {
        acceleration.add(force);
    }


}
