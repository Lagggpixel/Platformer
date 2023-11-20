package me.lagggpixel.platformerTutorial;

import me.lagggpixel.platformerTutorial.entities.Player;
import me.lagggpixel.platformerTutorial.inputs.KeyboardInputs;
import me.lagggpixel.platformerTutorial.inputs.MouseInputs;
import me.lagggpixel.platformerTutorial.utils.constants.GameConstants;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;
    MouseInputs mouseInputs;

    public GamePanel(Game game) {
        this.game = game;
        mouseInputs = new MouseInputs(this);

        setPreferredSize(new Dimension(GameConstants.width, GameConstants.height));

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return game.getPlayer();
    }
}
    