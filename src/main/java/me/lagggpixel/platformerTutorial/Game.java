package me.lagggpixel.platformerTutorial;

import me.lagggpixel.platformerTutorial.entities.Player;
import me.lagggpixel.platformerTutorial.levels.LevelManager;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;

public class Game {

    protected final GameWindow gameWindow;
    protected final GamePanel gamePanel;
    protected Thread gameThread;

    private Player player;
    private LevelManager levelManager;

    public Game() {
        initClasses();

        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(200, 200, (int) (64 * GameConstants.scale), (int) (40 * GameConstants.scale));
        player.loadLvlData(levelManager.getCurrentLvlData().getLvlData());
    }

    private void startGameLoop() {
        gameThread = new Thread(new GameThread(this));
        gameThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        levelManager.render(g);
        player.render(g);
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
