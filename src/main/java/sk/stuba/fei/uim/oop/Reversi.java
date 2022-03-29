package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Reversi {
    public Reversi() {
        JFrame frame = new JFrame("Reversi Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(false);
        
        frame.setLayout(new BorderLayout());
        Controller controller = new Controller();
        frame.addMouseListener(controller);

        JPanel menu = new JPanel();
        menu.setBackground(Color.GRAY);
        JButton restart = new JButton("Restart");

        menu.setLayout(new GridLayout(2, 2));

        frame.setVisible(true);
    }
}
