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
    private Frame frame;
    private Board board;
    private Player player;
    private Enemy enemy;
    private MoveLogic moveLogic;
    private JSlider boardSizeSlider;
    private JLabel boardSizeLabel;
    private JLabel nextPlayerLabel;
    private int nextPlayer;

    public Controller(Frame frame) {
        this.frame = frame;
        this.board = new Board(this);
        this.player = new Player();
        this.enemy = new Enemy();
        this.moveLogic = new MoveLogic(this.board, this.player, this.enemy);
        this.boardSizeSlider = new JSlider(Board.BOARD_SIZE_MIN, Board.BOARD_SIZE_MAX, board.getSize());
        this.boardSizeLabel = new JLabel();
        this.nextPlayerLabel = new JLabel();
        this.nextPlayer = 0;
        this.createMenu();
        this.boardSizeSlider.addChangeListener(this);
        this.board.setStartingPosition(this.player, this.enemy);
        this.moveLogic.showPossibleMoves(this.nextPlayer);
    }

    public void restartGame() {
        this.frame.remove(this.board.getGameArea());
        this.clearEntityCells();
        this.board.createBoard();
        this.frame.add(this.board.getGameArea());
        this.board.setStartingPosition(this.player, this.enemy);
        this.getBoardSizeLabel();
        this.nextPlayer = 0;
        this.moveLogic.showPossibleMoves(this.nextPlayer);
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
        if(!this.moveLogic.hasValidMove(0)) {
            this.flipPlayer();
        }
        if(this.moveLogic.isValidMove(this.nextPlayer , cell) && this.nextPlayer == 0) {
            List<Cell> cellsToFlip = this.moveLogic.getCellsToFlip(nextPlayer, cell);
            this.moveLogic.flipCells(cellsToFlip);
            if(this.nextPlayer == 0) {
                cell.setOwner(this.player);
                this.flipPlayer();
            }
        }
        if(!this.moveLogic.hasValidMove(1)) {
            this.flipPlayer();
            return;
        }
        if(this.nextPlayer == 1) {
            this.enemyTurn();
        }
        if(!this.moveLogic.hasValidMove(0) && !this.moveLogic.hasValidMove(1)) {
            String winner = this.enemy.getCells().size() < this.player.getCells().size() ? "Player" : "Computer";
            this.nextPlayerLabel.setText(winner + " Won!");
        }
        this.moveLogic.showPossibleMoves(this.nextPlayer);
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

    private void enemyTurn() {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell bestMove;
        for(int i = 0; i < this.board.getSize(); i++) {
            for(int j = 0; j < this.board.getSize(); j++) {
                Cell cell = this.board.getBoardArray()[i][j];
                if(this.moveLogic.isValidMove(1, cell)) {
                    possibleMoves.add(cell);
                }
            }
        }
        bestMove = possibleMoves.get(0);
        bestMove.getSize();
        for(Cell cell : possibleMoves) {
            if(this.moveLogic.getCellsToFlip(1, cell).size() > this.moveLogic.getCellsToFlip(1, bestMove).size()) {
                bestMove = cell;
            }
        }
        List<Cell> cellsToFlip = this.moveLogic.getCellsToFlip(nextPlayer, bestMove);
        this.moveLogic.flipCells(cellsToFlip);
        bestMove.setOwner(this.enemy);
        this.nextPlayer = 0;
    }

    private void flipPlayer() {
        if(this.nextPlayer == 0) {
            this.nextPlayer = 1;
        } else {
            this.nextPlayer = 0;
        }
    }

    private void clearEntityCells() {
        this.player.getCells().clear();
        this.enemy.getCells().clear();
    }

    private void createMenu() {
        this.createBoardSizeLabel();
        this.createNextPlayerLabel();
        this.createBoardSizeSlider();
    }

    public JSlider getBoardSizeSlider() { return this.boardSizeSlider; }

    public JLabel getNextPlayerLabel() { return this.nextPlayerLabel; }

    public JLabel getBoardSizeLabel() { return this.boardSizeLabel; }

    public JPanel getGameArea() { return this.board.getGameArea(); }
}
