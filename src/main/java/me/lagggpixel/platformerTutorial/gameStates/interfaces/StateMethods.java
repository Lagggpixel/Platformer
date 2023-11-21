package me.lagggpixel.platformerTutorial.gameStates.interfaces;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {

    // Tick update
    public void update();
    // Render
    public void render(Graphics g);
    // Mouse inputs
    public void mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    // Keyboard inputs
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);

}
