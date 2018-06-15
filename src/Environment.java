import processing.core.PApplet;
import processing.core.PVector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Environment {
    private PApplet p;
    private Display d = new Display();
    ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    void setup(PApplet p) {
        this.p = p;
        buildHeroes();
    }

    void tick(PVector mouseLoc) {
        for (Hero i : heroes) {
            i.seek(mouseLoc);
            i.tick(p);
        }
//        for (Projectile i : projectiles) {
//
//        }
//        for (int i = 0; i < projectiles.size(); i++) {
//            projectiles.get(i).tick(p);
//            if (!projectiles.get(i).projectileAlive()) {
//                projectiles.remove(i);
//            } else {
//                this.drawProjectile(p, projectiles.get(i).getPosition(), projectiles.get(i).getTheta());
//            }
//
//        }
    }

    void shoot() {
        Hero hCopy = (Hero) deepClone(heroes.get(0));
        PVector bPos = hCopy.getPosition();
        PVector bVel = hCopy.getVelocity();
        float bTheta = hCopy.getTheta();
        projectiles.add(new Projectile(bPos, bVel, bTheta));
    }

    private void buildHeroes() {
        //for(int x = 0; x <= 1; ++x) {
        heroes.add(new Hero());
        //}
    }

    void drawProjectile(PApplet p, PVector position, float theta) {
        p.fill(127);
        p.rectMode(p.RADIUS);
        p.translate(position.x, position.y);
        p.rotate(theta);
        p.rect(0, 0, 10, 10);
    }

    static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
