import processing.core.PApplet;
import processing.core.PVector;

class Projectile {
    private Display d = new Display();
    private PVector target;
    PVector position;
    private PVector velocity;
    private float theta;
    private int currentHealth = 200;
    private int maxHealth = 200;
    private int damage = 10;

    Projectile(PVector position, PVector velocity, float theta) {
        this.position = position;
        this.velocity = velocity;
        this.theta = theta;
    }

//    Projectile(PVector pos, PVector target) {
//        this.position = pos;
//        this.velocity = target;
//    }

    Projectile(PVector pos, PVector target, int damage) {
        this.position = pos;
        this.velocity = target;
        this.damage = damage;
    }



    void tick(PApplet p) {
        velocity.setMag(5);
        position.add(velocity);
        //d.drawProjectile(p, position, theta, currentHealth, maxHealth);
        //d.drawProjectile(p, position);
        currentHealth--;
    }

    void drawProjectile(PApplet p) {d.drawProjectile(p, position);}

    boolean projectileAlive() {
        if (currentHealth > 0) {
            return true;
        } else {
            return false;
        }
    }

    int getDamage() {
        return damage;
    }

}
