package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.UIConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VolumeButton extends PauseButton {

    private BufferedImage[] images;
    private BufferedImage slider;
    private int index;
    private boolean mouseOver, mousePressed;
    private int buttonX;
    private int minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width/2, y, UIConstants.VolumeButtons.VOLUME_WIDTH, height);
        bounds.x -= UIConstants.VolumeButtons.VOLUME_WIDTH/2;
        buttonX = x+width/2;
        this.x = x;
        this.width = width;

        this.minX = x + UIConstants.VolumeButtons.VOLUME_WIDTH/2;
        this.maxX = x + width - UIConstants.VolumeButtons.VOLUME_WIDTH/2;

        loadImages();
    }

    private void loadImages() {
        BufferedImage tempImage = LoadSave.getSpritesAtlas(LoadSave.VOLUME_BUTTONS);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = tempImage.getSubimage
                    (i * UIConstants.VolumeButtons.VOLUME_WIDTH_DEFAULT, 0,
                            UIConstants.VolumeButtons.VOLUME_WIDTH, UIConstants.VolumeButtons.VOLUME_HEIGHT);
        }
        slider = tempImage.getSubimage(3*UIConstants.VolumeButtons.VOLUME_WIDTH_DEFAULT, 0,
                UIConstants.VolumeButtons.SLIDER_WIDTH_DEFAULT, UIConstants.VolumeButtons.VOLUME_HEIGHT);
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
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(images[index], buttonX - UIConstants.VolumeButtons.VOLUME_WIDTH/2, y,
                UIConstants.VolumeButtons.VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        mousePressed = true;
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        bounds.x = buttonX - UIConstants.VolumeButtons.VOLUME_WIDTH/2;
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
