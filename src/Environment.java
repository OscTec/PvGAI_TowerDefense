import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;


public class Environment implements Cloneable {

    ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<Hero> heroes = new ArrayList<>();
    ArrayList<Test> tests = new ArrayList<>();

    void addProjectile(PVector position, float theta, PVector velocity) {
        projectiles.add(new Projectile(position, theta, velocity));
    }

    void addProjectile() {
        projectiles.add(new Projectile(this));
    }

    void addHero() {
        heroes.add(new Hero());
    }

    void addTest() {
        tests.add(new Test(5));
    }

    public Environment clone() {
        Environment c = new Environment();
        for (Hero i : heroes) {
            c.heroes.add(i.clone());
        }
        for (Projectile i : projectiles) {
            c.projectiles.add(i.clone());
        }
        return c;
    }

    public void tick(PVector mouse) {
        Environment before = Environment.this.clone();
        for (Hero i : heroes) {
            i.seek(mouse);
            i.tick(before);
        }
        for (Projectile i : projectiles) {
            i.tick(before);
        }
        for (Test i : tests) {
            i.tick(before);
        }
    }

    void shoot() {
        for (Hero i : heroes) {
            i.shoot(this);
        }
    }

}
