package sk.stuba.fei.uim.oop;

import javax.swing.JLabel;
import javax.swing.JSlider;

import java.awt.event.*;

public class Controller extends Listeners {
    private Board board;
    private Paint paint;
    private Player player;
    private JLabel boardSizeLabel;
    private JLabel nextPlayerLabel;
    private JSlider boardSizeSlider;

    public Controller() {
        this.board = new Board();
        this.player = new Player(this);
        this.paint = new Paint(this.board, this.player);
        this.paint.addMouseListener(this);
        this.paint.addMouseMotionListener(this);
        this.boardSizeLabel = new JLabel();
        this.nextPlayerLabel = new JLabel();
        this.boardSizeSlider = new JSlider(Board.BOARD_SIZE_MIN, Board.BOARD_SIZE_MAX);
        this.createBoardSizeLabel();
        this.createNextPlayerLabel();
        this.createBoardSizeSlider();
    }

    public void restartGame() {
        this.board.createBoard();
        this.getBoardSizeLabel();
    }

    private void createBoardSizeSlider() {
        this.boardSizeSlider.setMajorTickSpacing(2);
        this.boardSizeSlider.setPaintLabels(true);
        this.boardSizeSlider.setPaintTicks(true);
        this.boardSizeSlider.setSnapToTicks(true);
    }

    private void createBoardSizeLabel() {
        this.boardSizeLabel.setText("Board size: " + this.board.getBoardSize());
    }

    private void createNextPlayerLabel() {
        this.nextPlayerLabel.setText("Next player: ");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.restartGame();
                break;
        }
        this.paint.repaint();
    }

    public JSlider getBoardSizeSlider() { return this.boardSizeSlider; }

    public JLabel getNextPlayerLabel() { return this.nextPlayerLabel; }

    public JLabel getBoardSizeLabel() { return this.boardSizeLabel; }

    public Paint getPaint() { return this.paint; }
}
