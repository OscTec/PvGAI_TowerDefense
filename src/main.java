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
            if(Stats.getPlayerWon()) {
                background(0);
                d.drawPlayerWin(this);
            } else {
                d.drawAIWin(this);
            }
        } else {
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


    }



}


