import processing.core.PApplet;
import processing.core.PVector;

import java.io.Serializable;

public class Display implements Serializable {

    void drawHero(PApplet p, PVector position, float theta, float r) {
        p.fill(127);
        p.stroke(0);
        p.strokeWeight(1);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(theta);
        p.beginShape();
        p.vertex(0, -r * 2);
        p.vertex(-r, r * 2);
        p.vertex(r, r * 2);
        p.endShape(p.CLOSE);
        p.popMatrix();
    }

    void drawProjectile(PApplet p, PVector position, float theta) {
        p.fill(127);
        p.pushMatrix();
        p.rectMode(p.RADIUS);
        p.translate(position.x, position.y);
        p.rotate(theta);
        p.rect(0, 0, 10, 10);
        p.popMatrix();
    }
}
