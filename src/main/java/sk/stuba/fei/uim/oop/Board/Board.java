package sk.stuba.fei.uim.oop.Board;
import java.awt.*;
import javax.swing.*;

import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Player;
import java.awt.event.*;




public class Board {
    public static final int BOARD_SIZE_MIN = 6;
    public static final int BOARD_SIZE_MAX = 12;
    private int size;
    private JPanel gameArea;
    private MouseListener mouseListener;
    
    public Board(MouseListener mouseListener) {
        this.size = Board.BOARD_SIZE_MIN;
        this.mouseListener = mouseListener;
        this.createBoard();
    }

    public void createBoard() {
        this.initializeBoard();
    }

    private void initializeBoard() {
        this.gameArea = new JPanel();
        this.gameArea.setBackground(Color.BLACK);
        this.gameArea.setLayout(new GridLayout(this.getSize(), this.getSize(), 1, 1));
        for(int i = 0; i < this.getSize(); i++) {
            for(int j = 0; j < this.getSize(); j++) {
                Cell cell = new Cell();
                cell.setBackground(new Color(7, 176, 30));
                cell.setOpaque(true);
                cell.addMouseListener(this.mouseListener);
                this.gameArea.add(cell);
            }
        }
    }

    public void gameStartingPosition(Player player, Enemy enemy) {
        ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) - (this.getSize() / 2) - 1)).setOwner(player);
        ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) + this.getSize() - (this.getSize() / 2))).setOwner(player);
        ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) - (this.getSize() / 2))).setOwner(enemy);
        ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) + this.getSize() - (this.getSize() / 2) - 1)).setOwner(enemy);
    }

    public int getSize() { return this.size; }

    public void setSize(int value) { this.size = value; }

    public Board getBoard() { return this; }

    public JPanel getGameArea() { return this.gameArea; }
}
