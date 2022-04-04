package sk.stuba.fei.uim.oop.Controller;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.*;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Player;

import java.awt.event.*;
import java.awt.event.ActionEvent;

public class Controller extends Listeners {
    private Board board;
    private Player player;
    private Enemy enemy;
    private JLabel boardSizeLabel;
    private JLabel nextPlayerLabel;
    private JSlider boardSizeSlider;
    private Frame frame;

    public Controller(Frame frame) {
        this.frame = frame;
        this.board = new Board(this);
        this.player = new Player();
        this.enemy = new Enemy();
        this.boardSizeLabel = new JLabel();
        this.nextPlayerLabel = new JLabel();
        this.boardSizeSlider = new JSlider(Board.BOARD_SIZE_MIN, Board.BOARD_SIZE_MAX, board.getSize());
        this.boardSizeSlider.addChangeListener(this);
        this.createBoardSizeLabel();
        this.createNextPlayerLabel();
        this.createBoardSizeSlider();
        this.board.gameStartingPosition(this.player, this.enemy);

    }

    public void restartGame() {
        this.frame.remove(this.board.getGameArea());
        this.board.createBoard();
        this.frame.add(this.board.getGameArea());
        this.board.gameStartingPosition(this.player, this.enemy);
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
        this.boardSizeLabel.setText("Board size: " + this.board.getSize());
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
        if(!cell.hasOwner()) {
            cell.setOwner(player);
        }
    }

    private void isValid(Cell cell) {
        if(!cell.hasOwner()) {
            cell.setOwner(this.player);
            // this.player.addCell(cell);
            // this.enemy.removeCell(cell);
            this.board.getGameArea().remove(cell);
            this.board.getGameArea().add(cell);
            this.board.getGameArea().revalidate();
            this.board.getGameArea().repaint();
        }
    }

    public JSlider getBoardSizeSlider() { return this.boardSizeSlider; }

    public JLabel getNextPlayerLabel() { return this.nextPlayerLabel; }

    public JLabel getBoardSizeLabel() { return this.boardSizeLabel; }

    public int getSize() { return this.board.getSize(); }

    public Board getBoard() { return this.board.getBoard(); }

    public JPanel getGameArea() { return this.board.getGameArea(); }
}
