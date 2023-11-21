package me.lagggpixel.platformerTutorial;

import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.gameStates.impl.Menu;
import me.lagggpixel.platformerTutorial.gameStates.impl.Playing;

import java.awt.*;

public class Game {

    protected final GameWindow gameWindow;
    protected final GamePanel gamePanel;

    private Playing playing;
    private Menu menu;

    protected Thread gameThread;

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
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(new GameThread(this));
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU:
                menu.render(g);
                break;
            case PLAYING:
                playing.render(g);
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void windowFocusLost() {
        if (GameState.state == GameState.PLAYING) {
            playing.windowFocusLost();
        }
    }

    public void windowFocusGained() {
        if (GameState.state == GameState.PLAYING) {
            playing.windowFocusGained();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

}
