package sk.stuba.fei.uim.oop.Controller;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.*;
import javax.swing.text.html.parser.Entity;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Player;
import java.util.concurrent.TimeUnit;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller extends Listeners {
    private Board board;
    private Player player;
    private Enemy enemy;
    private JLabel boardSizeLabel;
    private JLabel nextPlayerLabel;
    private JSlider boardSizeSlider;
    private Frame frame;
    private int nextPlayer;
    private boolean gameOver;    

    public Controller(Frame frame) {
        this.frame = frame;
        this.board = new Board(this);
        this.player = new Player();
        this.enemy = new Enemy();
        this.boardSizeLabel = new JLabel();
        this.nextPlayerLabel = new JLabel();
        this.boardSizeSlider = new JSlider(Board.BOARD_SIZE_MIN, Board.BOARD_SIZE_MAX, board.getSize());
        this.gameOver = false;
        this.nextPlayer = 0;
        this.createMenu();
        this.boardSizeSlider.addChangeListener(this);
        this.board.setStartingPosition(this.player, this.enemy);
        this.showPossibleMoves();
    }

    private void showPossibleMoves() {
        for(int i = 0; i < this.board.getSize(); i++) {
            for(int j = 0; j < this.board.getSize(); j++) {
                Cell cell = this.board.getBoard()[i][j];
                if(!cell.hasOwner()) {
                    if(cell.getBackground() == Color.CYAN) {
                        cell.setBackground(Cell.DEFAULT_CELL_COLOR);
                    }
                    if(this.isValidMove(this.nextPlayer, cell)) {
                        cell.setBackground(Color.CYAN);
                    }
                }
            }
        }
    }

    private void createMenu() {
        this.createBoardSizeLabel();
        this.createNextPlayerLabel();
        this.createBoardSizeSlider();
    }

    public void restartGame() {
        this.frame.remove(this.board.getGameArea());
        this.removeEntityCells();
        this.board.createBoard();
        this.frame.add(this.board.getGameArea());
        this.board.setStartingPosition(this.player, this.enemy);
        this.getBoardSizeLabel();
        this.nextPlayer = 0;
        this.showPossibleMoves();
        this.frame.revalidate();
    }

    private void createBoardSizeSlider() {
        this.boardSizeSlider.setMajorTickSpacing(2);
        this.boardSizeSlider.setPaintLabels(true);
        this.boardSizeSlider.setPaintTicks(true);
        this.boardSizeSlider.setSnapToTicks(true);
    }

    private void createBoardSizeLabel() {
        this.boardSizeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.boardSizeLabel.setText("Board size: " + this.board.getSize() + "x" + this.board.getSize());
    }

    private void createNextPlayerLabel() {
        this.nextPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        this.nextPlayerLabel.setText("Next move: Player" );
    }

    private void updateNextPlayerLabel(int nextPlayer) {
        this.nextPlayerLabel.setText("Next move: " + (nextPlayer == 0 ? "Player" : "Computer"));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R:
                System.out.println("restart");
                this.restartGame();
                break;
            case KeyEvent.VK_ESCAPE:
                System.out.println("end");
                frame.dispose();
                System.exit(0);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.restartGame();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.board.setSize(this.boardSizeSlider.getValue());
        this.createBoardSizeLabel();
        this.restartGame();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(cell.hasOwner()) {
            return;
        }
        if(!this.hasValidMove(0)) {
            this.nextPlayer = 1;
        }
        if(this.isValidMove(this.nextPlayer , cell) && this.nextPlayer == 0) {
            List<Cell> cellsToFlip = this.getCellsToFlip(this.nextPlayer, cell);
            this.flipCells(cellsToFlip);
            if(this.nextPlayer == 0) {
                cell.setOwner(this.player);
                this.nextPlayer = 1;
            }
        }
        if(!this.hasValidMove(1)) {
            this.nextPlayer = 0;
            return;
        }
        if(this.nextPlayer == 1) {
            this.enemyTurn();
        }
        if(!this.hasValidMove(0) && !this.hasValidMove(1)) {
            String winner = this.enemy.getCells().size() < this.player.getCells().size() ? "Player" : "Computer";
            this.nextPlayerLabel.setText(winner + " Won!");
        }
        this.showPossibleMoves();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(!cell.hasOwner()) {
            if(cell.getBackground() != Color.CYAN) {
                cell.setBackground(new Color(7, 150, 30));
            } else if (cell.getBackground() == Color.CYAN) {
                cell.setBackground(Color.MAGENTA);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(!cell.hasOwner()) {
            if(cell.getBackground() == Color.MAGENTA ) {
                cell.setBackground(Color.CYAN);
            } else if (cell.getBackground() != Color.CYAN) {
                cell.setBackground(Cell.DEFAULT_CELL_COLOR);
            }
        }
    }


    // @Override
    // public void mousePressed(MouseEvent e) {
    //     Cell cell = (Cell)e.getSource();
    //     if(cell.hasOwner()) {
    //         return;
    //     }
    //     if(this.isValidMove(cell)) {
    //         List<Cell> cellsToFlip = this.getCellsToFlip(cell);
    //         this.flipCells(cellsToFlip);
    //         if(this.nextPlayer == 0) {
    //             cell.setOwner(this.player);
    //             this.nextPlayer = 1;
    //         } else {
    //             cell.setOwner(this.enemy);
    //             this.nextPlayer = 0;
    //         }
    //     }
    // }

    private boolean hasValidMove(int nextPlayer) {
        for(int i = 0; i < this.board.getSize(); i ++) {
            for(int j = 0; j < this.board.getSize(); j++) {
                if(isValidMove(nextPlayer,this.board.getBoard()[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void flipCells(List<Cell> cellsToFlip) {
        for(Cell cell : cellsToFlip) {
            if(this.board.getBoard()[cell.getI()][cell.getJ()].getOwner() == this.enemy) {
                this.board.getBoard()[cell.getI()][cell.getJ()].setOwner(this.player);
            } else {
                this.board.getBoard()[cell.getI()][cell.getJ()].setOwner(this.enemy);
            }
        }
    }

    private boolean isValidMove(int nextPlayer, Cell cell) {
        return !(this.getCellsToFlip(nextPlayer, cell).isEmpty());
    }

    private List<Cell> getCellsToFlip(int nextPlayer, Cell cell) {
        List<Cell> cellsToFlip = new ArrayList<>();

        // 1
        List<Cell> possibleCellsToFlip = new ArrayList<>();
        int column = cell.getJ();
        while(column < this.board.getSize() - 1) {
            column++;
            Cell currentCell = this.board.getBoard()[cell.getI()][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 2
        possibleCellsToFlip.clear();
        column = cell.getJ();
        while(column > 0) {
            column--;
            Cell currentCell = this.board.getBoard()[cell.getI()][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 3
        possibleCellsToFlip.clear();
        int row = cell.getI();
        while(row < this.board.getSize() - 1) {
            row++;
            Cell currentCell = this.board.getBoard()[row][cell.getJ()];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 4
        possibleCellsToFlip.clear();
        row = cell.getI();
        while(row > 0) {
            row--;
            Cell currentCell = this.board.getBoard()[row][cell.getJ()];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 5
        possibleCellsToFlip.clear();
        row = cell.getI();
        column = cell.getJ();
        while(row > 0 && column > 0) {
            row--;
            column--;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }
        
        // 6
        possibleCellsToFlip.clear();
        row = cell.getI();
        column = cell.getJ();
        while(row < this.board.getSize() - 1 && column < this.board.getSize() - 1) {
            row++;
            column++;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 7
        possibleCellsToFlip.clear();
        row = cell.getI();
        column = cell.getJ();
        while(row < this.board.getSize() - 1 && column > 0) {
            row++;
            column--;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 8
        possibleCellsToFlip.clear();
        row = cell.getI();
        column = cell.getJ();
        while(row > 0  && column < this.board.getSize() - 1) {
            row--;
            column++;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == nextPlayer) {
                if(owner != null && owner.getNum() == nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }
        
        return cellsToFlip;
    }

    private boolean hasValidMove(Entity entity, Cell cell) {
        return true;
    }

    private void flipPlayer() {
        if(this.nextPlayer == 0) {
            this.nextPlayer = 1;
        } else {
            this.nextPlayer = 0;
        }
    }

    private void enemyTurn() {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell bestMove;
        for(int i = 0; i < this.board.getSize(); i++) {
            for(int j = 0; j < this.board.getSize(); j++) {
                Cell cell = this.board.getBoard()[i][j];
                if(this.isValidMove(1, cell)) {
                    possibleMoves.add(cell);
                }
            }
        }
        bestMove = possibleMoves.get(0);
        bestMove.getSize();
        for(Cell cell : possibleMoves) {
            if(this.getCellsToFlip(1, cell).size() > this.getCellsToFlip(1, bestMove).size()) {
                bestMove = cell;
            }
        }
        List<Cell> cellsToFlip = this.getCellsToFlip(nextPlayer, bestMove);
        this.flipCells(cellsToFlip);
        bestMove.setOwner(this.enemy);
        this.nextPlayer = 0;
    }

    private void removeEntityCells() {
        this.player.getCells().clear();
        this.enemy.getCells().clear();
    }

    public JSlider getBoardSizeSlider() { return this.boardSizeSlider; }

    public JLabel getNextPlayerLabel() { return this.nextPlayerLabel; }

    public JLabel getBoardSizeLabel() { return this.boardSizeLabel; }

    public int getSize() { return this.board.getSize(); }

    public JPanel getGameArea() { return this.board.getGameArea(); }
}
