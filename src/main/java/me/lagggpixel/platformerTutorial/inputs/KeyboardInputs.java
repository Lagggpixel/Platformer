package me.lagggpixel.platformerTutorial.inputs;


import me.lagggpixel.platformerTutorial.GamePanel;

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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                gamePanel.getPlayer().setUp(true);
                break;
            }
            case KeyEvent.VK_A -> {
                gamePanel.getPlayer().setLeft(true);
                break;
            }
            case KeyEvent.VK_S -> {
                gamePanel.getPlayer().setDown(true);
                break;
            }
            case KeyEvent.VK_D -> {
                gamePanel.getPlayer().setRight(true);
                break;
            }
            default -> {
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                gamePanel.getPlayer().setUp(false);
                break;
            }
            case KeyEvent.VK_A -> {
                gamePanel.getPlayer().setLeft(false);
                break;
            }
            case KeyEvent.VK_S -> {
                gamePanel.getPlayer().setDown(false);
                break;
            }
            case KeyEvent.VK_D -> {
                gamePanel.getPlayer().setRight(false);
                break;
            }
            default -> {
                return;
            }
        }
    }
}
