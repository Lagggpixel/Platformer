package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.utils.LoadSave;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;
import me.lagggpixel.platformerTutorial.utils.constants.UIConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {

    private final Game game;
    protected BufferedImage backgroundImage;
    protected int bgX, bgY;
    protected int bgWidth, bgHeight;
    private SoundButton musicButton, sfxButton;

    public PauseOverlay(Game game) {
        this.game = game;

        loadBackground();
        loadSoundButtons();
    }

    private void loadSoundButtons() {
        int soundX = (int) (450 * GameConstants.scale);
        int musicY = (int) (140  * GameConstants.scale);
        int sfxY = (int) (186  * GameConstants.scale);
        musicButton = new SoundButton(soundX, musicY,
                UIConstants.PauseButtons.SOUND_SIZE, UIConstants.PauseButtons.SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY,
                UIConstants.PauseButtons.SOUND_SIZE, UIConstants.PauseButtons.SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.getSpritesAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImage.getWidth() * GameConstants.scale);
        bgHeight = (int) (backgroundImage.getHeight() * GameConstants.scale);
        bgX = GameConstants.width / 2 - bgWidth / 2;
        bgY = (int) (25 * GameConstants.scale);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
    }

    public void render(Graphics g) {
        g.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, null);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton) && musicButton.isMousePressed()) {
            musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton) && sfxButton.isMousePressed()) {
            musicButton.setMuted(!musicButton.isMuted());
        }
        musicButton.resetBool();
        sfxButton.resetBool();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
    }

    public boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }


}
