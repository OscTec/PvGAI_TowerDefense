import processing.core.PApplet;

public class main extends PApplet {
    private final Environment e = new Environment();

    public static void main(String[] args) {
        PApplet.main("main");
    }

    public void settings() {
        size(Settings.width, Settings.height);
    }

    public void setup() {
        e.setup(this);
    }

    public void draw() {
        background(0);
        e.tick();
    }

}


