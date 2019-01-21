import com.sun.jdi.ArrayReference;
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
    protected static ArrayList<Hero> heroes = new ArrayList<>();
    static ArrayList<Minion> minions = new ArrayList<>();
    private ArrayList<Tower> playerTowers = new ArrayList<>();
    //static ArrayList<Point> topLanePoints = new ArrayList<>();
    private static ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<PVector> topLanePoints= new ArrayList<>();
    private ArrayList<PVector> midLanePoints= new ArrayList<>();
    private ArrayList<PVector> btmLanePoints= new ArrayList<>();


    void setup(PApplet p) {
        this.p = p;
        buildTopLane();
        buildMidLane();
        buildBtmLane();
        buildHeroes();
        buildMinions();
        buildTowers();

    }

    void tick() {
        mouse();
        for (Hero h : heroes) {
            if (h.checkDead()) {
                heroes.remove(h);
                return;
            }
            h.tick(p, mouse);
        }

        for (Tower t : playerTowers) {
            t.tick();
        }
        for (PVector pos: topLanePoints) {
            d.drawWaypoint(p, pos);
        }
        for (PVector pos: midLanePoints) {
            d.drawWaypoint(p, pos);
        }
        for (PVector pos: btmLanePoints) {
            d.drawWaypoint(p, pos);
        }

//        for (int i = 0; i < minions.size(); i++) {
//            //System.out.println("minion: " + i);
//            minions.get(i).seek(heroes.get(0).getPosition());
//            minions.get(i).tick(p);
//            //minions.get(i).collisionCheck();
//            if(minions.get(i).collisionCheck()) {
//                minions.remove(i);
//            }
//        }
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
        heroes.add(new Hero(p, new PVector(100, p.height/2), topLanePoints));
        heroes.add(new Hero(p, new PVector(100, p.height/2), midLanePoints));
        heroes.add(new Hero(p, new PVector(100, p.height/2), btmLanePoints));
    }

    private void buildMinions() {
        minions.add(new Minion(p, new PVector(100, 50)));
        minions.add(new Minion(p, new PVector(200, 50)));
        minions.add(new Minion(p, new PVector(300, 50)));
        minions.add(new Minion(p, new PVector(100, 100)));
        minions.add(new Minion(p, new PVector(100, 200)));
    }

    private void buildTowers() {
        playerTowers.add(new Tower(p, new PVector(p.width*0.5f, p.height*0.1f), 10,10,10,1f));
    }

    private void buildTopLane() {
        topLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f - p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.1f, p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.25f, p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.5f, p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.75f, p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.9f, p.height*0.1f));
        topLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f - p.height*0.1f));
    }

    private void buildMidLane() {
        midLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.25f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.5f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.75f, p.height*0.5f));
        midLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f));
    }

    private void buildBtmLane() {
        btmLanePoints.add(new PVector(p.width*0.1f, p.height*0.5f + p.height*0.1f));
        btmLanePoints.add(new PVector(p.width*0.1f, p.height*0.9f));
        btmLanePoints.add(new PVector(p.width*0.25f, p.height*0.9f));
        btmLanePoints.add(new PVector(p.width*0.5f, p.height*0.9f));
        btmLanePoints.add(new PVector(p.width*0.75f, p.height*0.9f));
        btmLanePoints.add(new PVector(p.width*0.9f, p.height*0.9f));
        btmLanePoints.add(new PVector(p.width*0.9f, p.height*0.5f + p.height*0.1f));
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

    static void addProjectile(PVector position, PVector velocity) {
        PVector bPos = (PVector) deepClone(position);
        PVector bVel = (PVector) deepClone(velocity);
        projectiles.add(new Projectile(bPos, bVel));
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
