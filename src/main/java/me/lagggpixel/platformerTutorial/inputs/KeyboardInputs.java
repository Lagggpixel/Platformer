package me.lagggpixel.platformerTutorial.inputs;


import me.lagggpixel.platformerTutorial.GamePanel;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private final GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePane) {
        this.gamePanel = gamePane;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            }
            default -> {
                break;
            }
        }
    }
}
