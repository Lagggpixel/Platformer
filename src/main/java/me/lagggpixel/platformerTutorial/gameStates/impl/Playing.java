package me.lagggpixel.platformerTutorial.gameStates.impl;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.entities.EnemyManager;
import me.lagggpixel.platformerTutorial.entities.Player;
import me.lagggpixel.platformerTutorial.gameStates.State;
import me.lagggpixel.platformerTutorial.gameStates.interfaces.StateMethods;
import me.lagggpixel.platformerTutorial.levels.LevelManager;
import me.lagggpixel.platformerTutorial.ui.GameOverOverlay;
import me.lagggpixel.platformerTutorial.ui.PauseOverlay;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.Environment;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Playing extends State implements StateMethods {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private boolean paused = false;

    private int xLevelOffset;
    private final int leftBorder = (int) (0.2 * GameConstants.WIDTH);
    private final int rightBorder= (int) (0.8 * GameConstants.WIDTH);
    private final int levelTilesWide = LoadSave.getLevelData()[0].length;
    private final int maxTilesOffset = levelTilesWide - GameConstants.TILES_IN_WIDTH;
    private final int maxLevelOffsetX = maxTilesOffset *  GameConstants.TILE_SIZE;

    private BufferedImage backgroundImage, bigClouds, smallClouds;
    private int[] smallCloudsPositions;
    private Random random = new Random();

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImage = LoadSave.getSpritesAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
        bigClouds = LoadSave.getSpritesAtlas(LoadSave.BIG_CLOUDS);
        smallClouds = LoadSave.getSpritesAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPositions = new int[8];
        for (int i = 0; i < smallCloudsPositions.length; i++) {
            smallCloudsPositions[i] = (int) (90 * GameConstants.SCALE + random.nextInt((int) (100 * GameConstants.SCALE)));
        }
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(game);
        player = new Player(game, 200, 200, (int) (64 * GameConstants.SCALE), (int) (40 * GameConstants.SCALE));
        player.loadLvlData(levelManager.getCurrentLvlData().getLvlData());
        pauseOverlay = new PauseOverlay(game);
        gameOverOverlay = new GameOverOverlay(game);
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

        if (gameOver) {
            return;
        }

        if (paused) {
            pauseOverlay.update();
            return;
        }

        levelManager.update();
        enemyManager.update(levelManager.getCurrentLvlData().getLvlData(), player);
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
        g.drawImage(backgroundImage, 0, 0, GameConstants.WIDTH, GameConstants.HEIGHT, null);

        renderClouds(g, xLevelOffset);

        levelManager.render(g, xLevelOffset);
        enemyManager.render(g, xLevelOffset);
        player.render(g, xLevelOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
            pauseOverlay.render(g);
        } else if (gameOver) {
            gameOverOverlay.render(g);
        }
    }

    private void renderClouds(Graphics g, int xLevelOffset) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigClouds, i * Environment.BIG_CLOUD_WIDTH - ((int) (0.3 * xLevelOffset)), (int) (204 * GameConstants.SCALE), Environment.BIG_CLOUD_WIDTH, Environment.BIG_CLOUD_HEIGHT, null);
        }
        for (int i = 0; i < smallCloudsPositions.length; i++) {
            g.drawImage(smallClouds, Environment.SMALL_CLOUD_WIDTH * 4 * i - ((int) (0.7 * xLevelOffset)), smallCloudsPositions[i], Environment.SMALL_CLOUD_WIDTH, Environment.SMALL_CLOUD_HEIGHT, null);
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void resetAll() {
        gameOver = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (gameOver) {
            return;
        }

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
        if (gameOver) {
            return;
        }

        if (paused) {
            pauseOverlay.mousePressed(e);
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver) {
            return;
        }

        if (paused) {
            pauseOverlay.mouseReleased(e);
            return;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver) {
            return;
        }

        if (paused) {
            pauseOverlay.mouseMoved(e);
            return;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (gameOver) {
            return;
        }

        if (paused) {
            pauseOverlay.mouseDragged(e);
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
            return;
        }

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
        if (gameOver) {
            return;
        }

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
