package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.UIConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {

    private int x, y;
    private int rowIndex, index;
    private GameState state;
    private BufferedImage[] images;

    private boolean mouseOver;
    private boolean mousePressed;

    private Rectangle bounds;

    private final int xOffsetCentre = UIConstants.Buttons.B_WIDTH /2;
    public MenuButton(int x, int y, int rowIndex, GameState state) {
        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.state = state;

        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(x-xOffsetCentre, y, UIConstants.Buttons.B_WIDTH, UIConstants.Buttons.B_HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage tempImage = LoadSave.getSpritesAtlas(LoadSave.MENU_BUTTONS);

        for (int i = 0; i < images.length; i++) {
            images[i] = tempImage.getSubimage(i* UIConstants.Buttons.B_WIDTH_DEFAULT, rowIndex * UIConstants.Buttons.B_HEIGHT_DEFAULT, UIConstants.Buttons.B_WIDTH_DEFAULT, UIConstants.Buttons.B_HEIGHT_DEFAULT);
        }
    }

    public void render(Graphics g) {
        g.drawImage(images[index], x-xOffsetCentre, y, UIConstants.Buttons.B_WIDTH, UIConstants.Buttons.B_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }
}
