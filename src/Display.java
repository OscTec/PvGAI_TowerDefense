import processing.core.PApplet;

public class Display {
    Environment e;

    void draw(PApplet p, Environment e) {
        this.e = e;
        drawHero(p);
        drawProjectile(p);
    }

    void drawHero(PApplet p) {
        for (Hero i : e.heroes) {
            p.fill(127);
            p.stroke(0);
            p.strokeWeight(1);
            p.pushMatrix();
            p.translate(i.position.x, i.position.y);
            p.rotate(i.theta);
            p.beginShape();
            p.vertex(0, -i.r*2);
            p.vertex(-i.r, i.r*2);
            p.vertex(i.r, i.r*2);
            p.endShape(p.CLOSE);
            p.popMatrix();
        }
    }

    void drawProjectile(PApplet p) {
        for (Projectile i : e.projectiles) {
            p.pushMatrix();
            p.rectMode(p.RADIUS);
            p.translate(i.position.x, i.position.y);
            p.rotate(i.theta);
            p.rect(0, 0, 10, 10);
            p.popMatrix();
        }
    }
}
