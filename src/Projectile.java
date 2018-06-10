import processing.core.PApplet;
import processing.core.PVector;

public class Projectile {
    Environment e;
    PVector position;
    private PVector velocity;
    float theta;
    private int health = 100;

//    Projectile(PVector pos, float direction, PVector v){
//        this.position = pos;
//        this.theta = direction;
//        this.velocity = PVector.random2D();
//    }

    Projectile(Environment e){
        this.e = e;
        this.position = e.heroes.get(0).getPosition();
        this.velocity = e.heroes.get(0).getVelocity();
        this.theta = e.heroes.get(0).getTheta();
    }

    public Projectile clone() {
        //return new Projectile(position, theta, velocity);
        return new Projectile(e);
    }

    void tick() {

    }

    boolean lifeCheck() {
        if (health <= 0) {
            return false;
        } else {
            return true;
        }
    }

    void setPosition(PVector pos) {
        this.position = pos;
    }

    void setVelocity(PVector v) {
        this.velocity = v;
    }

    void setTheta(float t) {
        this.theta = t;
    }

}
