import processing.core.PApplet;
import processing.core.PVector;

public class Tower {
    private Display d = new Display();
    private PApplet p;
    private PVector pos;
    private int health;
    private int damage;
    private int range;
    private float fireRate;
    private Stopwatch sw = new Stopwatch();

    Tower(PApplet p, PVector pos, int health, int damage, int range, float fireRate) {
        this.p = p;
        this.pos = pos;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
    }

    void tick() {
        //System.out.println(sw.elapsedTime());
        d.drawTower(p, pos);
        PVector target = setTarget();
        float distance = PVector.dist(target, pos);
        if(distance <= range && sw.elapsedTime() >= 1/fireRate) {
                Environment.addProjectile(pos, seek(target));
                sw.reset();
            //Projectile(pos, seek(target));
        }

    }

    PVector setTarget() {
        PVector seekThis = new PVector(p.width / 2, p.height / 2);
        double lowestDistance = Settings.maxDistance;
        for (Hero h : Environment.heroes) {
            float d = pos.dist(h.position);
            if (d < lowestDistance) {
                lowestDistance = d;
                seekThis = h.position;

            }
        }
        return seekThis;
    }

    PVector seek(PVector target) {
        PVector desired = PVector.sub(target, pos);
        //desired.setMag(maxSpeed);
        //PVector steer = PVector.sub(desired, velocity);
        //steer.limit(maxForce);
        //applyForce(steer);
        return desired;
    }

}


