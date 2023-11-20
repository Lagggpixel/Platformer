package me.lagggpixel.platformerTutorial;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {

    private final JFrame jFrame;

    public GameWindow(GamePanel gamePanel) {
        this.jFrame = new JFrame();
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.add(gamePanel);
        this.jFrame.pack();
        this.jFrame.setResizable(false);

        this.jFrame.setLocationRelativeTo(null);

        this.jFrame.setVisible(true);

        this.jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusGained();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }

}
