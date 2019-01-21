import processing.core.PApplet;
import processing.core.PVector;

public class Point {
    private PApplet p;
    PVector position;


    Point(PApplet p, PVector pos) {
        this.p = p;
        position = pos;
    }
}
