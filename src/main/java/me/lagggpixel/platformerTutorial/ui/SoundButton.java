package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.UIConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundButton extends PauseButton{

    private BufferedImage[][] soundImages;
    private boolean mouseOver, mousePressed;
    private boolean isMuted;
    private int rowIndex, colorIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImages();
    }

    private void loadSoundImages() {
        BufferedImage tempImage = LoadSave.getSpritesAtlas(LoadSave.SOUND_BUTTONS);
        soundImages = new BufferedImage[2][3];
        for (int j = 0; j < soundImages.length; j++) {
            for (int i = 0; i < soundImages[j].length; i++) {
                soundImages[j][i] = tempImage.getSubimage
                        (i * UIConstants.PauseButtons.SOUND_SIZE_DEFAULT, j * UIConstants.PauseButtons.SOUND_SIZE_DEFAULT,
                        UIConstants.PauseButtons.SOUND_SIZE, UIConstants.PauseButtons.SOUND_SIZE);
            }
        }
    }

    public void update() {
        if (isMuted) {
            rowIndex = 1;
        }
        else {
            rowIndex = 0;
        }

        if (mouseOver) {
            colorIndex = 1;
        } else if (mousePressed) {
            colorIndex = 2;
        }
        else {
            colorIndex = 0;
        }
    }

    public void render(Graphics g) {
        g.drawImage(soundImages[rowIndex][colorIndex], x, y, width, height, null);
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

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }
}
