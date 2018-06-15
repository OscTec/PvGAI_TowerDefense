import processing.core.PApplet;
import processing.core.PVector;

public class main extends PApplet {
    private final Environment e = new Environment();
    private PVector mouse;

    public static void main(String[] args) {
        PApplet.main("main");
    }

    public void settings() {
        size(1000, 600);
    }

    public void setup() {
        e.setup(this);
    }

    public void draw() {
        background(0);
        mouse();
        e.tick(mouse);
    }

    private void mouse() {
        mouse = new PVector(mouseX, mouseY);
        fill(200);
        stroke(0);
        strokeWeight(2);
        ellipse(mouse.x, mouse.y, 24, 24);
    }

    public void mouseClicked() {
        e.heroes.get(0).shoot();
        //e.shoot();
    }


}


