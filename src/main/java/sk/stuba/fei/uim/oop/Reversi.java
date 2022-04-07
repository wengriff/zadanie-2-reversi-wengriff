package sk.stuba.fei.uim.oop;

import javax.swing.*;

import sk.stuba.fei.uim.oop.Controller.Controller;

import java.awt.*;

public class Reversi {
    public Reversi() {
        JFrame frame = new JFrame("Reversi Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 830);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        
        Controller controller = new Controller(frame);
        frame.addKeyListener(controller);
        frame.setFocusable(true);

        frame.add(controller.getGameArea(), BorderLayout.CENTER);

        frame.add(controller.getMenu(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}