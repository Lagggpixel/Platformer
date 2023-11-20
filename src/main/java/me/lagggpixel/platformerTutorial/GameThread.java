package me.lagggpixel.platformerTutorial;

import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

public class GameThread implements Runnable{
    private final Game game;

    public GameThread(Game game) {
        this.game = game;
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
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }
}
