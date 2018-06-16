import processing.core.PApplet;

public class main extends PApplet {
    private final Environment e = new Environment();

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
        e.tick();
    }

}


