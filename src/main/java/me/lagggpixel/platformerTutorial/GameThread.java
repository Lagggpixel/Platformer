package me.lagggpixel.platformerTutorial;

import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

public class GameThread implements Runnable{
    private final Game game;
    public static int fps = 0;
    public static int tps = 0;

    public GameThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / GameConstants.FPS;
        double timePerUpdate = 1000000000.0 / GameConstants.TPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        //noinspection InfiniteLoopStatement
        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                game.update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                game.gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                fps = frames;
                tps = updates;
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }

    public static int getFps() {
        return fps;
    }

    public static int getTps() {
        return tps;
    }
}
