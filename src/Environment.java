import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;


public class Environment implements Cloneable {

    PApplet p;

    public ArrayList<Hero> heroes = new ArrayList<Hero>();
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public void setup(PApplet p) {
        this.p = p;
        buildHeros();
    }


    void buildHeros() {
        for(int x = 0; x <= 2; ++x) {
            heroes.add(new Hero());
        }
    }

    public void tick(PVector mouseLoc) {
        for (Hero i: heroes) {
            i.seek(mouseLoc);
            i.tick(p);
        }
    }

}
