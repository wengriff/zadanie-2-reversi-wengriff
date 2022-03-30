package sk.stuba.fei.uim.oop;

import java.awt.Graphics;

public class Board {
    public static final int BOARD_SIZE_MIN = 6;
    public static final int BOARD_SIZE_MAX = 12;
    private Cell[][] board;
    
    public Board() {
        this.createBoard();
    }

    public void createBoard() {
    }

    public void display(Graphics g) {

    }

    public int getBoardSize() {
        return BOARD_SIZE_MIN;
    }
}
