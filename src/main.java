import processing.core.PApplet;
import processing.core.PVector;

public class main extends PApplet {
    final Environment e = new Environment();
    Display d;
    PVector mouse;

    public static void main(String[] args) {
        PApplet.main("main");
    }

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        addHeroes();
        d = new Display();
    }

    public void draw() {
        mouse();
        d.draw(this, e);
        e.tick(mouse);
    }

    void mouse() {
        mouse = new PVector(mouseX, mouseY);
        fill(200);
        stroke(0);
        strokeWeight(2);
        ellipse(mouse.x, mouse.y, 48, 48);
    }

    public void mouseClicked() {
        e.shoot();
    }

    void addHeroes() {
        e.addHero();
    }
}
