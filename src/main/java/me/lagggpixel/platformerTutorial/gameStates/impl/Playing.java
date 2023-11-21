package me.lagggpixel.platformerTutorial.gameStates.impl;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.entities.Player;
import me.lagggpixel.platformerTutorial.gameStates.State;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.gameStates.interfaces.StateMethods;
import me.lagggpixel.platformerTutorial.levels.LevelManager;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {

    private Player player;
    private LevelManager levelManager;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * GameConstants.scale), (int) (40 * GameConstants.scale));
        player.loadLvlData(levelManager.getCurrentLvlData().getLvlData());
    }

    public void windowFocusGained() {
        player.resetDirBooleans();
    }

    public void windowFocusLost() {
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();
    }

    @Override
    public void render(Graphics g) {
        levelManager.render(g);
        player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                player.setLeft(true);
                break;
            }
            case KeyEvent.VK_D -> {
                player.setRight(true);
                break;
            }
            case KeyEvent.VK_SPACE -> {
                player.setJump(true);
                break;
            }
            case KeyEvent.VK_BACK_SPACE -> {
                GameState.state = GameState.MENU;
            }
            default -> {
                return;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                player.setLeft(false);
                break;
            }
            case KeyEvent.VK_D -> {
                player.setRight(false);
                break;
            }
            case KeyEvent.VK_SPACE -> {
                player.setJump(false);
                break;
            }
            default -> {
                return;
            }
        }
    }
}
