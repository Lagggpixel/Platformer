package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
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
    private UrmButton menuButton, replayButton, unpauseButton;
    private VolumeButton volumeButton;

    public PauseOverlay(Game game) {
        this.game = game;

        loadBackground();
        loadSoundButtons();
        loadUrmButtons();
        loadVolumeButtons();
    }

    private void loadVolumeButtons() {
        int vX = (int) (309 * GameConstants.scale);
        int vY = (int) (278 * GameConstants.scale);
        volumeButton = new VolumeButton(vX, vY, UIConstants.VolumeButtons.SLIDER_WIDTH, UIConstants.VolumeButtons.VOLUME_HEIGHT);
    }

    private void loadUrmButtons() {
        int menuX = (int) (330 * GameConstants.scale);
        int replayX = (int) (397 * GameConstants.scale);
        int unpauseX = (int) (462 * GameConstants.scale);
        int buttonY = (int) (325 * GameConstants.scale);

        menuButton = new UrmButton(menuX, buttonY, UIConstants.URMButtons.URM_SIZE, UIConstants.URMButtons.URM_SIZE, 2);
        replayButton = new UrmButton(replayX, buttonY, UIConstants.URMButtons.URM_SIZE, UIConstants.URMButtons.URM_SIZE, 1);
        unpauseButton = new UrmButton(unpauseX, buttonY, UIConstants.URMButtons.URM_SIZE, UIConstants.URMButtons.URM_SIZE, 0);
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

        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        volumeButton.update();
    }

    public void render(Graphics g) {
        g.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, null);

        musicButton.render(g);
        sfxButton.render(g);

        menuButton.render(g);
        replayButton.render(g);
        unpauseButton.render(g);

        volumeButton.render(g);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else if (isIn(e, replayButton)) {
            replayButton.setMousePressed(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
        } else  if(isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton) && musicButton.isMousePressed()) {
            musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton) && sfxButton.isMousePressed()) {
            sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isIn(e, menuButton) && menuButton.isMousePressed()) {
            GameState.state = GameState.MENU;
            game.getPlaying().unPauseGame();
        } else if (isIn(e, replayButton) && replayButton.isMousePressed()) {
            System.out.println("Replay level.");
        } else if (isIn(e, unpauseButton) && unpauseButton.isMousePressed()) {
            game.getPlaying().unPauseGame();
        } else if(isIn(e, volumeButton) && volumeButton.isMousePressed()) {
            volumeButton.setMousePressed(false);
        }
        musicButton.resetBool();
        sfxButton.resetBool();
        menuButton.resetBool();
        replayButton.resetBool();
        unpauseButton.resetBool();
        volumeButton.resetBool();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else if (isIn(e, replayButton)) {
            replayButton.setMouseOver(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMouseOver(true);
        } else  if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
    }

    public boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }


}
