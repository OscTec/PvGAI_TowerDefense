import processing.core.PApplet;
import processing.core.PVector;

public class Minion extends Enemy{
    private Display d = new Display();
    int health = 100;
    int speed = 5;
    private PVector position;
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
        position = new PVector(100, 100);
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    void tick(PApplet p) {
        movement();
        d.drawEnemy(p, position, theta, r);
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        position.add(velocity);
        acceleration.mult(0);
    }

    void seek(PVector target) {
        PVector desired = PVector.sub(target, position);
        desired.setMag(maxSpeed);
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    private void applyForce(PVector force) {
        acceleration.add(force);
    }


}
