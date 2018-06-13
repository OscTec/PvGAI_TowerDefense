import processing.core.PApplet;
import processing.core.PVector;

public class main extends PApplet {
    final Environment e = new Environment();
    Display d;
    PVector mouse;
    Hero wizard;
    Boolean fired = false;
    Projectile bullet;

    public static void main(String[] args) {
        PApplet.main("main");
    }

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        d = new Display();
        e.setup(this);

        //wizard = new Hero();
    }

    public void draw() {
        background(0);
        mouse();
        e.tick(mouse);
//        wizard.seek(mouse);
//        wizard.tick(this);
//        if (fired) {
//            bullet.tick(this);
//        }
    }

    void mouse() {
        mouse = new PVector(mouseX, mouseY);
        fill(200);
        stroke(0);
        strokeWeight(2);
        ellipse(mouse.x, mouse.y, 24, 24);
    }

    public void mouseClicked() {
        e.heroes.get(0).shoot();
        //final PVector bVel2 = wizard.getVelocity();


    }


}


