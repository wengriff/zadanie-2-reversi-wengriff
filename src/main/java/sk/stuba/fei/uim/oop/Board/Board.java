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
    private Cell[][] boardArray;
    
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
        this.boardArray = new Cell[this.getSize()][this.getSize()];
        this.gameArea.setBackground(new Color(255, 204, 204));
        this.gameArea.setLayout(new GridLayout(this.getSize(), this.getSize(), 3, 3));
        for(int i = 0; i < this.getSize(); i++) {
            for(int j = 0; j < this.getSize(); j++) {
                Cell cell = new Cell(i,j);
                cell.setDefaultCellBackground(cell);
                cell.addMouseListener(this.mouseListener);
                this.gameArea.add(cell);
                this.boardArray[i][j] = cell;
            }
        }
    }

    public void setStartingPosition(Player player, Enemy enemy) {
        Cell startingCellOnePlayer = ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) - (this.getSize() / 2) - 1));
        Cell startingCellTwoPlayer = ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) + this.getSize() - (this.getSize() / 2)));
        Cell startingCellOneEnemy = ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) - (this.getSize() / 2)));
        Cell startingCellTwoEnemy = ((Cell) this.getGameArea().getComponent(((this.getSize() * this.getSize()) / 2) + this.getSize() - (this.getSize() / 2) - 1));
        
        startingCellOnePlayer.setOwner(player);
        startingCellTwoPlayer.setOwner(player);
        startingCellOneEnemy.setOwner(enemy);
        startingCellTwoEnemy.setOwner(enemy);
    }

    public int getSize() { return this.size; }

    public void setSize(int value) { this.size = value; }

    public Cell[][] getBoardArray() { return this.boardArray; }

    public JPanel getGameArea() { return this.gameArea; }
}