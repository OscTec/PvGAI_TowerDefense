import processing.core.PApplet;
import processing.core.PVector;

public class Headquarters {

    private PApplet p;
    private PVector pos;
    private boolean player;
    private Display d = new Display();
    private int currentHealth;


    //sim HQ
    Headquarters(PApplet p, PVector pos, boolean player, int currentHealth) {
        this.p = p;
        this.pos = pos;
        this.player = player;
        this.currentHealth = currentHealth;
    }


    //Normal hq
    Headquarters(PApplet p, PVector pos, boolean player) {
        this.p = p;
        this.pos = pos;
        this.player = player;
        this.currentHealth = Stats.getHqHealth();
    }

    void tick(){
        checkDamage();
        //System.out.println(currentHealth);
        //d.drawHQ(p, pos, currentHealth);
    }

    void drawHQ() {d.drawHQ(p, pos, currentHealth);}

    private void checkDamage() {
        if (player) {
            Projectile hit = Methods.collisionCheck(pos, Environment.getAiProjectiles());
            if (hit != null) {
                //System.out.println("Player minion hit");
                currentHealth = currentHealth - hit.getDamage();
                Environment.getAiProjectiles().remove(hit);
            }
        } else {
            Projectile hit = Methods.collisionCheck(pos, Environment.getPlayerProjectiles());
            if (hit != null) {
                //System.out.println("AI minion hit");
                currentHealth = currentHealth - hit.getDamage();
                Environment.getPlayerProjectiles().remove(hit);
            }
        }

    }

    boolean checkDead() {
        if (currentHealth <= 0) {
            return true;
        } else {
            return false;
        }
    }

    PVector getPos() {
        return pos;
    }

    boolean getPlayer() {return player;}

    int getCurrentHealth() {return currentHealth;}
}
