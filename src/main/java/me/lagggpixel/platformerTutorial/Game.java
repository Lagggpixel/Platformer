package me.lagggpixel.platformerTutorial;

import me.lagggpixel.platformerTutorial.entities.Player;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;

public class Game implements Runnable{

    // Instance
    private static Game INSTANCE;

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameThread;

    private Player player;

    public Game() {
        INSTANCE = this;

        initClasses();

        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void initClasses() {
        player = new Player(200, 200);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }

    public static Game getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / GameConstants.fps;
        double timePerUpdate = 1000000000.0 / GameConstants.tps;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusGained() {
        player.resetDirBooleans();
    }

    public void windowFocusLost() {
    }
}
