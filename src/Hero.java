import processing.core.PApplet;
import processing.core.PVector;
import java.io.*;
import java.util.ArrayList;

class Hero implements Serializable {
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private PVector position;
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



    Hero() {
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, -2);
        position = new PVector(100, 100);
        r = 6;
        maxSpeed = 4;
        maxForce = 0.1f;
    }

    Hero(PApplet p) {
        this.p = p;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, -2);
        position = new PVector(p.random(p.width), p.random(p.height));
        r = 6;
        maxSpeed = 4;
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

    void tick(PApplet p, PVector target) {
        movement();
        seek(giveTarget(target));

        if (currentCoolDown < 0) {
            shoot();
            currentCoolDown = maxCoolDown;
        } else {
            currentCoolDown--;
        }

//        for (int i = 0; i < projectiles.size(); i++) {
//            if (!projectiles.get(i).projectileAlive()) {
//                projectiles.remove(i);
//            } else {
//                projectiles.get(i).tick(p);
//            }
//        }
        d.drawHero(p, position, theta, r);
    }

    private void movement() {
        theta = velocity.heading() + (float) (3.14 / 2);
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        position.add(velocity);
        acceleration.mult(0);
    }

    void shoot() {
        if (projectiles.size() < 3) {
            Environment.addProjectile(position, velocity, theta);

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
            } else {
                for (int i = 0; i < Environment.minions.size(); i++) {
                    float d = position.dist(Environment.minions.get(i).position);
                    if (d < lowestDistance) {
                        lowestDistance = d;
                        seekThis = Environment.minions.get(i).position;
                        System.out.println(i);
                        //System.out.println(seekThis);
                    }
                }

            }

        }
        //System.out.println(seekThis);
        return seekThis;
    }

//    static Object deepClone(Object object) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(object);
//            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            return ois.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    PVector getPosition() {
        return position;
    }

}

