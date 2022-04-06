package sk.stuba.fei.uim.oop.Menu;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

import sk.stuba.fei.uim.oop.Board.Board;

public class Menu extends JPanel {
    private Board board;
    private JSlider boardSizeSlider;
    private ChangeListener changeListener;
    private JLabel boardSizeLabel;
    private JLabel nextPlayerLabel;
    private JButton restart;
    private ActionListener actionListener;

    public Menu(Board board, ChangeListener changeListener, ActionListener actionListener) {
        this.board = board;
        this.changeListener = changeListener;
        this.boardSizeSlider = new JSlider(Board.BOARD_SIZE_MIN, Board.BOARD_SIZE_MAX, this.board.getSize());
        this.boardSizeSlider.addChangeListener(this.changeListener);
        this.boardSizeLabel = new JLabel();
        this.nextPlayerLabel = new JLabel();
        this.restart = new JButton("Restart");
        this.actionListener = actionListener;
        this.createMenu();
    }

    private void createMenu() {
        this.setLayout(new GridLayout(2, 2));

        this.createBoardSizeLabel();
        this.createNextPlayerLabel();
        this.createBoardSizeSlider();
        this.createRestartButton();

        this.add(this.getBoardSizeLabel());
        this.add(this.getNextPlayerLabel());
        this.add(this.getBoardSizeSlider());
        this.add(this.getRestartButton());
    }

    private void createRestartButton() {
        this.restart.addActionListener(this.actionListener);
        this.restart.setFocusable(false);
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

    public void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("Board size: " + this.board.getSize() + "x" + this.board.getSize());
    }

    private void createNextPlayerLabel() {
        this.nextPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        this.nextPlayerLabel.setText("Next move: Player" );
    }

    private void updateNextPlayerLabel(int nextPlayer) {
        this.nextPlayerLabel.setText("Next move: " + (nextPlayer == 0 ? "Player" : "Computer"));
    }
    
    public JButton getRestartButton() { return this.restart; }

    public JSlider getBoardSizeSlider() { return this.boardSizeSlider; }

    public JLabel getNextPlayerLabel() { return this.nextPlayerLabel; }

    public JLabel getBoardSizeLabel() { return this.boardSizeLabel; }
}
