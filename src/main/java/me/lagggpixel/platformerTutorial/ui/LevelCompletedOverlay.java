package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;
import me.lagggpixel.platformerTutorial.utils.constants.UIConstants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LevelCompletedOverlay {

    private final Game game;

    private BufferedImage image;
    private UrmButton menu, next;

    private int backgroundX, backgroundY, backgroundW, backgroundH;

    public LevelCompletedOverlay(Game game) {
        this.game = game;

        initImage();
        initButtons();
    }

    private void initImage() {
        image = LoadSave.getSpritesAtlas(LoadSave.LEVEL_COMPLETED);
        backgroundW = (int) (image.getWidth() * GameConstants.SCALE);
        backgroundH = (int) (image.getHeight() * GameConstants.SCALE);
        backgroundX = GameConstants.WIDTH / 2 - backgroundW / 2;
        backgroundY = GameConstants.HEIGHT / 2 - backgroundH / 2;

    }

    private void initButtons() {
        int menuX = (int) (330 * GameConstants.SCALE);
        int nextX = (int) (445 * GameConstants.SCALE);
        int y = (int) (195 * GameConstants.SCALE);
        next = new UrmButton(nextX, y, UIConstants.URMButtons.URM_SIZE, UIConstants.URMButtons.URM_SIZE, 0);
        menu = new UrmButton(menuX, y, UIConstants.URMButtons.URM_SIZE, UIConstants.URMButtons.URM_SIZE, 2);
    }

    public void update() {

    }

    public void render(Graphics g) {
        g.drawImage(image, backgroundX, backgroundY, backgroundW, backgroundH, null);
        next.render(g);
        menu.render(g);
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(next, e)) {
            next.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e) && menu.isMousePressed()) {
            game.getPlaying().resetAll();
            GameState.state = GameState.MENU;
        } else if (isIn(next, e) && next.isMousePressed()) {
            game.getPlaying().loadNextLevel();
        }

        menu.resetBool();
        next.resetBool();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(next, e)) {
            next.setMousePressed(true);
        }
    }

    private boolean isIn(UrmButton button, MouseEvent e) {
        return button.getBounds().contains(e.getPoint());
    }
}
