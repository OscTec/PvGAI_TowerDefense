import processing.core.PApplet;
import processing.core.PVector;

class Hero {
    //private PApplet p;
    protected PVector position;
    private PVector velocity;
    private PVector acceleration;
    //Environment e = new Environment();
    protected float theta;
    protected float r;
    private float maxforce;    // Maximum steering force
    private float maxspeed;    // Maximum speed

    Hero() {
        //this.p = parent;
        this.acceleration = new PVector(0,0);
        this.velocity = new PVector(0,-2);
        this.position = new PVector(100,100);
        this.r = 6;
        this.maxspeed = 4;
        this.maxforce = 0.1f;
    }

    void tick(Environment e) {
        theta = velocity.heading() + (float)(3.14/2);
        this.velocity.add(acceleration);
        this.velocity.limit(maxspeed);
        this.position.add(velocity);
        this.acceleration.mult(0);
    }

    public Hero clone() {
        return new Hero();
    }

    void shoot(Environment e) {
        //e.addProjectile(position, theta, velocity);
        e.addProjectile();
        e.addTest();
    }

    void applyForce(PVector force) {
        //velocity.add(force);
        acceleration.add(force);
    }

    void seek(PVector target) {
        PVector desired = PVector.sub(target,position);
        desired.setMag(maxspeed);
        PVector steer = PVector.sub(desired,velocity);
        steer.limit(maxforce);
        applyForce(steer);
    }

    PVector getPosition() {
        return position;
    }

    PVector getVelocity() {
        return velocity;
    }

    float getTheta() {
        return theta;
    }

}

