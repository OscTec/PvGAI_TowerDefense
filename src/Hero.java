import processing.core.PApplet;
import processing.core.PVector;
import java.io.*;

import java.util.ArrayList;

class Hero implements Cloneable, Serializable {
    ArrayList<Projectile> projectiles = new ArrayList<>();
    private PVector position;
    private PVector velocity;
    private PVector acceleration;
    private Display d = new Display();
    protected float theta;
    protected float r;
    private float maxForce;
    private float maxSpeed;

    Hero() {
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, -2);
        position = new PVector(100, 100);
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    Hero(PVector position, PVector velocity, float theta) {
        acceleration = new PVector(0, 0);
        this.velocity = velocity;
        this.position = position;
        this.theta = theta;
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    void tick(PApplet p) {
        movement();
        d.drawHero(p, position, theta, r);
        for (Projectile bullet : projectiles) {
            bullet.tick(p);
        }
    }

    void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        position.add(velocity);
        acceleration.mult(0);
    }

//    void shoot() {
//        try {
//            Hero hCopy = (Hero) super.clone();
//            //Hero hCopy = (Hero) this.clone();
//            //hCopy.setPosition((Hero)hCopy.getPosition().clone());
//            PVector bPos = hCopy.getPosition();
//            PVector bVel = hCopy.getVelocity();
//            float bTheta = hCopy.getTheta();
//            projectiles.add(new Projectile(bPos, bVel, bTheta));
//            //Projectile bullet = new Projectile(bPos, bVel, bTheta);
//        } catch (CloneNotSupportedException c) {
//            System.out.print("hi");
//        }
//    }

    void shoot() {
        //Hero hCopy = new this.clone();
        Hero hCopy = (Hero)deepClone(this);
        PVector bPos = hCopy.getPosition();
        PVector bVel = hCopy.getVelocity();
        float bTheta = hCopy.getTheta();
        projectiles.add(new Projectile(bPos, bVel, bTheta));
    }





    void applyForce(PVector force) {
        acceleration.add(force);
    }

    void seek(PVector target) {
        PVector desired = PVector.sub(target, position);
        desired.setMag(maxSpeed);
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);
        applyForce(steer);
    }

    public PVector getVelocity() {
        return velocity;
    }

    public PVector getPosition() {
        return position;
    }

    public float getTheta() {
        return theta;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    @Override
    public Object clone() {
        Hero user = null;
        try {
            user = (Hero) super.clone();
        } catch (CloneNotSupportedException e) {
            user = new Hero(
                    this.getPosition(), this.getVelocity(), this.getTheta());
        }
        //user.address = (Address) this.address.clone();
        return user;
    }

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

