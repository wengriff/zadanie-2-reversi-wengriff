package sk.stuba.fei.uim.oop.Controller;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.*;
import javax.swing.text.html.parser.Entity;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Player;

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

    public Controller(Frame frame) {
        this.frame = frame;
        this.board = new Board(this);
        this.player = new Player();
        this.enemy = new Enemy();
        this.boardSizeLabel = new JLabel();
        this.nextPlayerLabel = new JLabel();
        this.boardSizeSlider = new JSlider(Board.BOARD_SIZE_MIN, Board.BOARD_SIZE_MAX, board.getSize());
        this.nextPlayer = 0;
        this.createMenu();
        this.boardSizeSlider.addChangeListener(this);
        this.board.setStartingPosition(this.player, this.enemy);

        // this.play();
    }

    private void play() {
        while(!this.gameEnd()) {

        }
    }

    private boolean gameEnd() {
        for(int i = 0; i < this.board.getGameArea().getComponentCount(); i++) {
            Cell cell = (Cell)this.board.getGameArea().getComponent(i);
            if(!cell.hasOwner()) {
                return false;
            }
        }
        return true;
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
        this.nextPlayerLabel.setText("Next move: " + this.player.getName());
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
        if(this.isValidMove(cell) && this.nextPlayer == 0) {
            List<Cell> cellsToFlip = this.getCellsToFlip(cell);
            this.flipCells(cellsToFlip);
            if(this.nextPlayer == 0) {
                cell.setOwner(this.player);
                this.nextPlayer = 1;
            } else {
                cell.setOwner(this.enemy);
                this.nextPlayer = 0;
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

    @Override
    public void mouseEntered(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(!cell.hasOwner()) {
            cell.setBackground(new Color(7, 150, 30));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(!cell.hasOwner()) {
            cell.setBackground(new Color(7, 176, 30));
        }
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

    private boolean isValidMove(Cell cell) {
        return !(this.getCellsToFlip(cell).isEmpty());
    }

    private List<Cell> getCellsToFlip(Cell cell) {
        List<Cell> cellsToFlip = new ArrayList<>();

        // 1
        List<Cell> possibleCellsToFlip = new ArrayList<>();
        int column = cell.getJ();
        while(column < this.board.getSize() - 1) {
            column++;
            Cell currentCell = this.board.getBoard()[cell.getI()][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 2
        possibleCellsToFlip = new ArrayList<>();
        column = cell.getJ();
        while(column > 0) {
            column--;
            Cell currentCell = this.board.getBoard()[cell.getI()][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 3
        possibleCellsToFlip = new ArrayList<>();
        int row = cell.getI();
        while(row < this.board.getSize() - 1) {
            row++;
            Cell currentCell = this.board.getBoard()[row][cell.getJ()];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 4
        possibleCellsToFlip = new ArrayList<>();
        row = cell.getI();
        while(row > 0) {
            row--;
            Cell currentCell = this.board.getBoard()[row][cell.getJ()];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 5
        possibleCellsToFlip = new ArrayList<>();
        row = cell.getI();
        column = cell.getJ();
        while(row > 0 && column > 0) {
            row--;
            column--;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }
        
        // 6
        possibleCellsToFlip = new ArrayList<>();
        row = cell.getI();
        column = cell.getJ();
        while(row < this.board.getSize() - 1 && column < this.board.getSize() - 1) {
            row++;
            column++;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 7
        possibleCellsToFlip = new ArrayList<>();
        row = cell.getI();
        column = cell.getJ();
        while(row < this.board.getSize() - 1 && column > 0) {
            row++;
            column--;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }

        // 8
        possibleCellsToFlip = new ArrayList<>();
        row = cell.getI();
        column = cell.getJ();
        while(row > 0  && column < this.board.getSize() - 1) {
            row--;
            column--;
            Cell currentCell = this.board.getBoard()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.getNum() == this.nextPlayer) {
                if(owner != null && owner.getNum() == this.nextPlayer) {
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
