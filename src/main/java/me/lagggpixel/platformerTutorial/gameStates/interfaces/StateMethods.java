package me.lagggpixel.platformerTutorial.gameStates.interfaces;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {

    // Tick update
    void update();
    // Render
    void render(Graphics g);
    // Mouse inputs
    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void mouseDragged(MouseEvent e);
    // Keyboard inputs
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);

}
