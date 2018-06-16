import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

class Environment {
    private PApplet p;
    private PVector mouse;
    private ArrayList<Hero> heroes = new ArrayList<>();

    void setup(PApplet p) {
        this.p = p;
        buildHeroes();
    }

    void tick() {
        mouse();
        for (Hero i : heroes) {
            i.seek(mouse);
            i.tick(p);
        }
    }

    private void buildHeroes() {
        for (int x = 0; x < 10; ++x) {
            heroes.add(new Hero(p));
        }
    }

    private void mouse() {
        mouse = new PVector(p.mouseX, p.mouseY);
        p.fill(200);
        p.stroke(0);
        p.strokeWeight(2);
        p.ellipse(mouse.x, mouse.y, 24, 24);
    }

}
