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

    void drawHero(PApplet p, PVector pos, float theta, float r, int currentHealth, int maxHealth) {
        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        p.arc(pos.x, pos.y, 30, 30, 0, m * (p.PI), p.PIE);
        p.pushMatrix();
        p.fill(220, 20, 60);
        p.ellipse(pos.x, pos.y, 15, 15);
        p.popMatrix();
    }

    void drawMinion(PApplet p, PVector pos, int currentHealth, int maxHealth, int range) {

        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        //p.ellipseMode(p.CENTER);
        p.arc(pos.x, pos.y, 30, 30, 0, m * (p.PI), p.PIE);
        p.pushMatrix();
        p.fill(220, 20, 60);
        p.ellipse(pos.x, pos.y, 15, 15);
        p.popMatrix();
        if(Settings.rangeLines) {
            p.pushMatrix();
            p.noFill();
            //p.ellipseMode(p.CENTER);
            p.stroke(204, 102, 0);
            p.ellipse(pos.x, pos.y, range*2, range*2);
            p.popMatrix();
        }
    }

    void drawEnemy(PApplet p, PVector position, float theta, float r, int currentHealth, int maxHealth) {
        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        p.arc(position.x, position.y, 30, 30, 0, m * (p.PI), p.PIE);
        p.pushMatrix();
        p.fill(100, 0, 0);
        p.stroke(0);
        p.strokeWeight(1);
        p.translate(position.x, position.y);
        p.rotate(theta);
        p.ellipse(0, 0, 20, 20);
//        p.beginShape();
//        p.vertex(0, -r * 2);
//        p.vertex(-r, r * 2);
//        p.vertex(r, r * 2);
//        p.endShape(p.CLOSE);
        p.popMatrix();
    }

    void drawWaypoint(PApplet p, PVector pos) {
        p.ellipse(pos.x,pos.y,5,5);

    }

    void drawTower(PApplet p, PVector pos) {
        p.rect(pos.x, pos.y, 10, 10);
    }

    void drawTower(PApplet p, PVector pos, int currentHealth, int maxHealth) {
        p.rectMode(p.RADIUS);
        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        p.rect(pos.x, pos.y + 20, 10 * m, 2, 7);
        p.pushMatrix();
        p.rectMode(p.CENTER);
        p.fill(220, 20, 60);
        p.rect(pos.x, pos.y, 25, 25);
        p.popMatrix();
    }

    void drawTower(PApplet p, PVector pos, int currentHealth, int maxHealth, int range) {
        p.rectMode(p.RADIUS);
        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        p.rect(pos.x, pos.y + 20, 10 * m, 2, 7);
        p.pushMatrix();
        p.rectMode(p.CENTER);
        p.fill(220, 20, 60);
        p.rect(pos.x, pos.y, 25, 25);
        p.popMatrix();
        if(Settings.rangeLines) {
            p.pushMatrix();
            p.noFill();
            //p.ellipseMode(p.CENTER);
            p.stroke(204, 102, 0);
            p.ellipse(pos.x, pos.y, range*2, range*2);
            p.popMatrix();
        }
    }

    void drawLanes(PApplet p) {
        p.pushMatrix();
        p.rectMode(p.CENTER);
        p.rect(p.width*0.1f, p.height*0.5f, p.width*0.05f, p.height*0.8f);
        p.rect(p.width*0.9f, p.height*0.5f, p.width*0.05f, p.height*0.8f);
        p.rect(p.width*0.5f, p.height*0.1f, p.width*0.85f, p.width*0.05f);
        p.rect(p.width*0.5f, p.height*0.5f, p.width*0.85f, p.width*0.05f);
        p.rect(p.width*0.5f, p.height*0.9f, p.width*0.85f, p.width*0.05f);
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

    void drawProjectile(PApplet p, PVector pos){
        p.ellipse(pos.x, pos.y, 15, 15);
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
