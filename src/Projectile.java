import processing.core.PApplet;
import processing.core.PVector;

import java.io.Serializable;

public class Projectile {
    private Display d;
    public PVector position;
    private PVector velocity;
    private float theta;
    private int health = 100;

    Projectile(PVector pos, PVector v, float theta){
        d = new Display();
        this.position = pos;
        this.theta = theta;
        this.velocity = v;
    }

    void tick(PApplet p) {
        this.position.add(velocity);
        d.drawProjectile(p, position);
        System.out.println(velocity);
    }

    boolean lifeCheck() {
        if (health <= 0) {
            return false;
        } else {
            return true;
        }
    }

}
