import processing.core.PApplet;
import processing.core.PVector;

class Projectile {
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
        velocity.setMag(5);
        position.add(velocity);
        d.drawProjectile(p, position, theta, currentHealth, maxHealth);
        currentHealth--;
    }

    boolean projectileAlive() {
        if (currentHealth > 0) {
            return true;
        } else {
            return false;
        }
    }

}
