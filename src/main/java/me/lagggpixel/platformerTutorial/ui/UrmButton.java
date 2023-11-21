package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.UIConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UrmButton extends PauseButton {

    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;

        loadImages();
    }

    private void loadImages() {
        BufferedImage tempImage = LoadSave.getSpritesAtlas(LoadSave.URM_BUTTONS);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = tempImage.getSubimage
                    (i * UIConstants.URMButtons.URM_SIZE_DEFAULT, rowIndex * UIConstants.URMButtons.URM_SIZE_DEFAULT,
                            UIConstants.URMButtons.URM_SIZE_DEFAULT, UIConstants.URMButtons.URM_SIZE_DEFAULT);
        }

    }

    public void update() {
        if (mouseOver) {
            index = 1;
        } else if (mousePressed) {
            index = 2;
        } else {
            index = 0;
        }
    }

    public void render(Graphics g) {
        g.drawImage(images[index], x, y, UIConstants.URMButtons.URM_SIZE, UIConstants.URMButtons.URM_SIZE, null);
    }

    public void resetBool() {
        mouseOver = false;
        mousePressed = false;
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
}
