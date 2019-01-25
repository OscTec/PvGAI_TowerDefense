import processing.core.PApplet;

public class main extends PApplet {
    private final Environment e = new Environment();
    private Display d = new Display();
    private boolean gameStarted = false;

    public static void main(String[] args) {
        PApplet.main("main");
    }

    public void settings() {
        size(Settings.width, Settings.height);
    }

    public void setup() {
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
        } else {
            background(0);
            d.drawAIWin(this);
            if (key == ' ') {
                reset();
            }
        }
    }

    private void starting() {
        background(0);
        if(gameStarted) {
            e.tick();
        } else {
            d.drawMainMenu(this);
            if (key == ' ') {
                gameStarted = true;
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



}


