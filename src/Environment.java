import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

class Environment {
    private PApplet p;
    private Display d = new Display();
    private PVector mouse;
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Minion> minions = new ArrayList<>();

    void setup(PApplet p) {
        this.p = p;
        buildHeroes();
        buildMinions();
    }

    void tick() {
        mouse();
        for (Hero i : heroes) {
            i.seek(mouse);
            i.tick(p);
        }
        for (Minion i : minions) {
            i.seek(heroes.get(0).getPosition());
            i.tick(p);
        }
        d.drawFrameRate(p);
    }

    private void buildHeroes() {
        for (int x = 0; x < 1; ++x) {
            heroes.add(new Hero(p));
        }
    }

    private void buildMinions() {
        for (int x = 0; x < 1; ++x) {
            minions.add(new Minion(p));
        }
    }

    private void mouse() {
        mouse = new PVector(p.mouseX, p.mouseY);
        d.drawMouse(p, mouse);
    }

}
