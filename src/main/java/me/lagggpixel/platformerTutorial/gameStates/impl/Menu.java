package me.lagggpixel.platformerTutorial.gameStates.impl;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.gameStates.State;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.gameStates.interfaces.StateMethods;
import me.lagggpixel.platformerTutorial.ui.MenuButton;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImage, menuBackgroundImage;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadBackground();
        loadButtons();
    }

    private void loadBackground() {
        menuBackgroundImage = LoadSave.getSpritesAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
        backgroundImage = LoadSave.getSpritesAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImage.getWidth() * GameConstants.SCALE);
        menuHeight = (int) (backgroundImage.getHeight() * GameConstants.SCALE);
        menuX = GameConstants.WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * GameConstants.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GameConstants.WIDTH / 2, (int) (150 * GameConstants.SCALE), 0, GameState.PLAYING);
        buttons[1] = new MenuButton(GameConstants.WIDTH / 2, (int) (220 * GameConstants.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(GameConstants.WIDTH / 2, (int) (290 * GameConstants.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton menuButton : buttons) {
            menuButton.update();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(menuBackgroundImage, 0, 0, GameConstants.WIDTH, GameConstants.HEIGHT, null);
        g.drawImage(backgroundImage, menuX, menuY, menuWidth, menuHeight, null);
        for (MenuButton menuButton : buttons) {
            menuButton.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            if (isIn(e, menuButton)) {
                menuButton.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            if (isIn(e, menuButton)) {
                if (menuButton.isMousePressed()) {
                    menuButton.applyGameState();
                    menuButton.setMousePressed(false);
                    break;
                }
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton menuButton : buttons) {
            menuButton.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            menuButton.setMouseOver(false);
        }

        for (MenuButton menuButton : buttons) {
            if (isIn(e, menuButton)) {
                menuButton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
