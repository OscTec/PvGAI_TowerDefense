import processing.core.PApplet;
import processing.core.PVector;

import java.beans.PropertyVetoException;

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
        p.pushMatrix();
        float healthColour = p.map(currentHealth, 0, maxHealth, 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, maxHealth, 0, 2);
        p.arc(pos.x, pos.y, 30, 30, 0, m * (p.PI), p.PIE);
        //p.pushMatrix();
        p.fill(220, 20, 60);
        p.ellipse(pos.x, pos.y, 15, 15);
        p.popMatrix();
    }

    void drawMinion(PApplet p, PVector pos, int currentHealth, int maxHealth, int range) {
        p.pushMatrix();

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
        p.popMatrix();
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
        p.pushMatrix();
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
        p.popMatrix();
    }

    void drawLanes(PApplet p) {
        p.pushMatrix();
        p.fill(255,255,255);
        p.rectMode(p.CENTER);
        p.rect(p.width*0.1f, p.height*0.5f, p.width*0.05f, p.height*0.78f);
        p.rect(p.width*0.9f, p.height*0.5f, p.width*0.05f, p.height*0.78f);
        p.rect(p.width*0.5f, p.height*0.15f, p.width*0.85f, p.width*0.05f);
        p.rect(p.width*0.5f, p.height*0.5f, p.width*0.85f, p.width*0.05f);
        p.rect(p.width*0.5f, p.height*0.85f, p.width*0.85f, p.width*0.05f);
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

    void drawHQ(PApplet p, PVector pos, int currentHealth) {
        p.pushMatrix();
        p.rectMode(p.RADIUS);
        p.fill(125, 98, 0);
        p.rect(pos.x, pos.y, 30, 30);
        float healthColour = p.map(currentHealth, 0, Stats.getHqHealth(), 0, 1);
        p.fill(p.lerpColor(p.color(255, 0, 0), p.color(0, 255, 0), healthColour));
        float m = p.map(currentHealth, 0, Stats.getHqHealth(), 0, 2);
        p.rect(pos.x, pos.y + 20, 10 * m, 2, 7);
        p.popMatrix();
    }

    void drawStats(PApplet p) {
        p.pushMatrix();
        p.fill(0, 102, 153);
        p.textSize(32);
        p.text("HP: " + Stats.getPlayerMinionHealth(), p.width*0.01f, 30);
        p.text("DMG: " + Stats.getPlayerMinionDamage(), p.width*0.2f, 30);
        p.text("SPD: " + Stats.getPlayerMinionSpeed(), p.width*0.4f, 30);
        p.text("Range: " + Stats.getPlayerMinionRange(), p.width*0.6f, 30);
        p.text("Atk Rate: " + Stats.getPlayerMinionAtkSpeed(), p.width*0.8f, 30);

        p.fill(255, 10, 15);
        p.text("HP: " + Stats.getAiMinionHealth(), p.width*0.01f, p.height - 10);
        p.text("DMG: " + Stats.getAiMinionDamage(), p.width*0.2f, p.height - 10);
        p.text("SPD: " + Stats.getAiMinionSpeed(), p.width*0.4f, p.height - 10);
        p.text("Range: " + Stats.getAiMinionRange(), p.width*0.6f, p.height - 10);
        p.text("Atk Rate: " + Stats.getAiMinionAtkSpeed(), p.width*0.8f, p.height - 10);
        p.popMatrix();
    }

    void drawMainMenu(PApplet p) {
        p.pushMatrix();
        //p.textAlign(p.CENTER, p.CENTER);
        p.fill(0, 102, 153);
        p.textSize(48);
        p.text("PvAI Battle Arena", p.width*0.35f, p.height*0.5f - 50);
        p.text("Press SPACEBAR to start", p.width*0.3f, p.height*0.5f);
        p.popMatrix();
    }

    void drawPlayerWin(PApplet p) {
        p.pushMatrix();
        p.fill(0, 102, 153);
        p.textSize(48);
        p.text("Player Wins!", p.width*0.4f, p.height*0.5f);
        p.text("Press Space bar to reset", p.width*0.3f, p.height*0.5f + 30);
        p.popMatrix();
    }

    void drawAIWin(PApplet p) {
        p.pushMatrix();
        p.fill(0, 102, 153);
        p.textSize(48);
        p.text("AI Wins!", p.width*0.4f, p.height*0.5f);
        p.text("Press Space bar to reset", p.width*0.3f, p.height*0.5f + 30);
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
