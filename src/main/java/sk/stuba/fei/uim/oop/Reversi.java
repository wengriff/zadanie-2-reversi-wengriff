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

        // JPanel menu = new JPanel();
        // JButton restart = new JButton("Restart");   
        // restart.addActionListener(controller);
        // restart.setFocusable(false);

        // menu.setLayout(new GridLayout(2, 2));
        // menu.add(controller.getBoardSizeLabel());
        // menu.add(controller.getNextPlayerLabel());
        // menu.add(controller.getBoardSizeSlider());
        // menu.add(restart);

        // frame.add(menu, BorderLayout.SOUTH);

        frame.add(controller.getMenu(), BorderLayout.SOUTH);


        frame.setVisible(true);
    }
}
