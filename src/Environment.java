import processing.core.PApplet;
import processing.core.PVector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class Environment {
    private PApplet p;
    private Display d = new Display();
    private PVector mouse;
    private ArrayList<Hero> heroes = new ArrayList<>();
    static ArrayList<Minion> minions = new ArrayList<>();
    private static ArrayList<Projectile> projectiles = new ArrayList<>();

    void setup(PApplet p) {
        this.p = p;
        buildHeroes();
        buildMinions();
    }

    void tick() {
        mouse();
        for (Hero i : heroes) {
            //i.seek(mouse);
            i.tick(p, mouse);
        }
        for (int i = 0; i < minions.size(); i++) {
            //System.out.println("minion: " + i);
            minions.get(i).seek(heroes.get(0).getPosition());
            minions.get(i).tick(p);
            if(minions.get(i).collisionCheck()) {
                minions.remove(i);
            }
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (!projectiles.get(i).projectileAlive()) {
                projectiles.remove(i);
            } else {
                projectiles.get(i).tick(p);
            }
        }
        d.drawFrameRate(p);
    }

    private void buildHeroes() {
        for (int x = 0; x < 1; ++x) {
            heroes.add(new Hero(p));
        }
    }

    private void buildMinions() {
        for (int x = 0; x < 5; ++x) {
            minions.add(new Minion(p));
        }
    }

    private void mouse() {
        mouse = new PVector(p.mouseX, p.mouseY);
        d.drawMouse(p, mouse);
    }

    static void addProjectile(PVector position, PVector velocity, float theta) {
        PVector bPos = (PVector) deepClone(position);
        PVector bVel = (PVector) deepClone(velocity);
        Float bTheta = (Float) deepClone(theta);
        projectiles.add(new Projectile(bPos, bVel, bTheta));
    }

    static ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    static ArrayList<Minion> getMinions() {
        return minions;
    }

    static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
