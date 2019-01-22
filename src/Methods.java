import processing.core.PVector;

import java.util.ArrayList;

public class Methods {

    protected static PVector findTarget(PVector pos, ArrayList<Tower> targets) {
        PVector seekThis = new PVector();
        double lowestDistance = Settings.maxDistance;
        for (Tower t : targets) {
            float d = pos.dist(t.getPos());
            if (d < lowestDistance) {
                lowestDistance = d;
                seekThis = t.getPos();

            }
        }
        return seekThis;
    }

    protected static PVector seek(PVector pos, PVector target) {
        PVector desired = PVector.sub(target, pos);
        return desired;
    }

    protected static Projectile collisionCheck(PVector pos, ArrayList<Projectile> pro) {
        //boolean hit = false;
        for (Projectile i : pro) {
            //System.out.println(this.position);
            if ((i.position.x <= pos.x + 10 && i.position.x >= pos.x - 10) && (i.position.y <= pos.y + 10 && i.position.y >= pos.y - 10)) {
                //hit = true;

                //currentHealth = currentHealth - i.getDamage();
                //System.out.println("Health: " + currentHealth);
                //Environment.getAiProjectiles().remove(i);
                return i;
            }
        }
        return null;

    }
}
