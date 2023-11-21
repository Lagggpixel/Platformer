package me.lagggpixel.platformerTutorial.gameStates.impl;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.entities.Player;
import me.lagggpixel.platformerTutorial.gameStates.State;
import me.lagggpixel.platformerTutorial.gameStates.interfaces.StateMethods;
import me.lagggpixel.platformerTutorial.levels.LevelManager;
import me.lagggpixel.platformerTutorial.ui.PauseOverlay;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {

    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    private int xLevelOffset;
    private final int leftBorder = (int) (0.2 * GameConstants.WIDTH);
    private final int rightBorder= (int) (0.8 * GameConstants.WIDTH);
    private final int levelTilesWide = LoadSave.getLevelData()[0].length;
    private final int maxTilesOffset = levelTilesWide - GameConstants.TILES_IN_WIDTH;
    private final int maxLevelOffsetX = maxTilesOffset *  GameConstants.TILE_SIZE;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * GameConstants.SCALE), (int) (40 * GameConstants.SCALE));
        player.loadLvlData(levelManager.getCurrentLvlData().getLvlData());
        pauseOverlay = new PauseOverlay(game);
    }

    public void windowFocusGained() {
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
            return;
        }

        levelManager.update();
        player.update();
        checkCloseToBorder();
    }

    /**
     * Checks if the player is close to the border and adjusts the level offset accordingly.
     */
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLevelOffset;
        if (diff > rightBorder) {
            xLevelOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffset += diff - leftBorder;
        }

        if (xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        levelManager.render(g, xLevelOffset);
        player.render(g, xLevelOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
            pauseOverlay.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseClicked(e);
            return;
        }

        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
            return;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
            return;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (paused) {
            pauseOverlay.keyPressed(e);
            return;
        }

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
            case KeyEvent.VK_ESCAPE -> {
                paused = !paused;
            }
            default -> {
                return;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (paused) {
            pauseOverlay.keyReleased(e);
            return;
        }

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

    public void unPauseGame() {
        paused = false;
    }
}
