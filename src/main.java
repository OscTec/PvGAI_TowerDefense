import processing.core.PApplet;

public class main extends PApplet {
    private final Environment e = new Environment();
    private Display d = new Display();
    private boolean gameStarted = false;
    private boolean gamePaused = false;

    public static void main(String[] args) {
        PApplet.main("main");
    }

    public void settings() {
        size(Settings.width, Settings.height);
    }

    public void setup() {
        frameRate(60);
        e.setup(this);
    }

    public void draw() {
        if(Stats.getPlayerWon() || Stats.getAiWon()) {
            gameOver();
        } else {
            starting();
        }
    }

    private void gameOver() {
        if(Stats.getPlayerWon()) {
            background(0);
            d.drawPlayerWin(this);
            if (key == ' ') {
                reset();
            }
            if (Settings.aiVai) {
                System.out.println("Left AI Won!");
                reset();
            }
        } else {
            background(0);
            d.drawAIWin(this);
            if (key == ' ') {
                reset();
            }
            if (Settings.aiVai) {
                System.out.println("Right AI Won!");
                reset();
            }
        }
    }

    private void starting() {
        //background(0);
        //checkPause();
        if(gameStarted && !gamePaused) {
            e.tick();
        } else {
            d.drawMainMenu(this);
            if (key == ' ') {
                gameStarted = true;
                background(0);
            }
            if (key == 'a' || key == 'A') {
                Settings.aiVai = true;
                gameStarted = true;
                background(0);
            }
        }
    }



    private void reset() {
        Stats.setPlayerWon(false);
        Stats.setAiWon(false);
        gameStarted = false;
        e.reset();
        Stats.reset();
        setup();
    }

    /*
    Current bugs
    best minion nt allocated correctly - FIXED
    stats going over limits
    stat number not whole - FIXED
    not all minions get put in next gen - FIXED
     */



}


