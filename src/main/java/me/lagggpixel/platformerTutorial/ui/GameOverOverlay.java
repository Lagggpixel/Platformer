package me.lagggpixel.platformerTutorial.ui;

import me.lagggpixel.platformerTutorial.Game;
import me.lagggpixel.platformerTutorial.gameStates.enums.GameState;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {

    private final Game game;

    public GameOverOverlay(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 0,0, 200));
        g.fillRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("Game Over", GameConstants.WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu!", GameConstants.WIDTH / 2, 300);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.getPlaying().resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
