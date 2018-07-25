import processing.core.PApplet;
import processing.core.PVector;

class Display {

    void drawHero(PApplet p, PVector position, float theta, float r) {
        p.pushMatrix();
        p.fill(127);
        p.stroke(0);
        p.strokeWeight(1);
        p.translate(position.x, position.y);
        p.rotate(theta);
        p.beginShape();
        p.vertex(0, -r * 2);
        p.vertex(-r, r * 2);
        p.vertex(r, r * 2);
        p.endShape(p.CLOSE);
        p.popMatrix();
    }

    void drawEnemy(PApplet p, PVector position, float theta, float r) {
        p.pushMatrix();
        p.fill(127);
        p.stroke(0);
        p.strokeWeight(1);
        p.translate(position.x, position.y);
        p.rotate(theta);
        p.beginShape();
        p.vertex(0, -r * 2);
        p.vertex(-r, r * 2);
        p.vertex(r, r * 2);
        p.endShape(p.CLOSE);
        p.popMatrix();
    }

    void drawProjectile(PApplet p, PVector position, float theta, int currentHealth, int maxHealth) {
        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        p.arc(position.x, position.y, 30, 30, 0, m * (p.PI), p.PIE);
        p.pushMatrix();
        p.fill(220, 20, 60);
        p.ellipse(position.x, position.y, 15, 15);
        p.popMatrix();
    }

    void drawFrameRate(PApplet p) {
        p.pushMatrix();
        p.fill(255, 255, 0);
        p.textSize(20);
        p.text((int)p.frameRate, 10, 30);
        p.popMatrix();
    }

    void drawMouse(PApplet p, PVector mouse) {
        p.pushMatrix();
        p.fill(200);
        p.stroke(0);
        p.strokeWeight(2);
        p.ellipse(mouse.x, mouse.y, 24, 24);
        p.popMatrix();
    }
}
