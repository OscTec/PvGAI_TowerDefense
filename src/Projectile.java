import processing.core.PApplet;
import processing.core.PVector;
import java.io.Serializable;

class Projectile implements Serializable {
    private Display d = new Display();
    private PVector position;
    private PVector velocity;
    private float theta;
    private int currentHealth = 200;
    private int maxHealth = 200;

    Projectile(PVector position, PVector velocity, float theta) {
        this.position = position;
        this.velocity = velocity;
        this.theta = theta;

    }

    void tick(PApplet p) {
        position.add(velocity);
        d.drawProjectile(p, position, theta, currentHealth, maxHealth);
        System.out.println(position);
        currentHealth--;
    }

    boolean projectileAlive() {
        if (currentHealth > 0) {
            return true;
        } else {
            return false;
        }
    }

    PVector getPosition() {
        return position;
    }

    float getTheta() {
        return theta;
    }
}
