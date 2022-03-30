package sk.stuba.fei.uim.oop;

import java.awt.*;
import javax.swing.*;

public class Paint extends JPanel {
    private Board board;
    private Player player;

    public Paint(Board board, Player player) {
        this.board = board;
        this.player = player;
        this.setBackground(new Color(76, 153,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.board.display(g);
        this.player.display(g);
    }
}
