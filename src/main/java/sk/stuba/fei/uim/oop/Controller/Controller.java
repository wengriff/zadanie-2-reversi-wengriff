package sk.stuba.fei.uim.oop.Controller;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.*;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Player;
import sk.stuba.fei.uim.oop.Menu.Menu;


import java.awt.event.*;
import java.awt.event.ActionEvent;

public class Controller extends Listeners {
    private Frame frame;
    private Board board;
    private Player player;
    private Enemy enemy;
    private MoveLogic moveLogic;
    private Menu menu;

    public Controller(Frame frame) {
        this.frame = frame;
        this.board = new Board(this);
        this.player = new Player();
        this.enemy = new Enemy();
        this.moveLogic = new MoveLogic(this.board, this.player, this.enemy);
        this.menu = new Menu(this.board, this, this);
        this.board.setStartingPosition(this.player, this.enemy);
        this.moveLogic.showPossibleMoves();
    }

    public void restartGame() {
        this.frame.remove(this.board.getGameArea());
        this.clearScore();
        this.board.createBoard();
        this.frame.add(this.board.getGameArea());
        this.board.setStartingPosition(this.player, this.enemy);
        this.menu.resetNextPlayerLabel();
        this.moveLogic.showPossibleMoves();
        this.frame.revalidate();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            // case KeyEvent.VK_W:
            //     this.enemy.move(this.board, this.moveLogic);
            //     this.moveLogic.showPossibleMoves();
            // break;
            case KeyEvent.VK_R:
                this.restartGame();
                break;
            case KeyEvent.VK_ESCAPE:
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
        this.board.setSize(this.menu.getBoardSizeSlider().getValue());
        this.menu.updateBoardSizeLabel();
        this.restartGame();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        this.moveLogic.setSelectedCell(cell);

        if(cell.hasOwner() || !this.moveLogic.isValidMove(this.player , cell)) {
            return;
        }

        this.player.move(this.board, this.moveLogic);

        this.menu.updateNextPlayerLabel(this.enemy);
        Timer timer = new Timer(150, event -> {
            this.menu.updateNextPlayerLabel(this.player);
        });

        timer.setRepeats(false);
        timer.start();

        if(!this.moveLogic.hasValidMove(this.enemy)) {
            if(this.moveLogic.hasValidMove(this.player)) {
                this.player.move(this.board, this.moveLogic);
            }
            else {
                this.displayWinner();
            }
        }

        do {
            if(!this.moveLogic.hasValidMove(this.enemy)) {
                break;
            }
            this.enemy.move(this.board, this.moveLogic);
        } while(!this.moveLogic.hasValidMove(this.player));

        if(this.isGameFinished()) {
            this.displayWinner();
        }
        this.moveLogic.showPossibleMoves();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        cell.setCellHighlight();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        cell.removeCellHighlight();
    }

    private boolean isGameFinished() {
        return (!this.moveLogic.hasValidMove(this.player) && !this.moveLogic.hasValidMove(this.enemy));
    }

    private void clearScore() {
        this.player.getCells().clear();
        this.enemy.getCells().clear();
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private String determineWinner() {
        return this.enemy.getCells().size() < this.player.getCells().size() ? "Player" : "Computer";
    }

    private void displayWinner() {
        String winner = determineWinner();
        this.menu.getNextPlayerLabel().setText(winner + " Won!");
    }

    public JPanel getGameArea() { return this.board.getGameArea(); }

    public JPanel getMenu() { return this.menu; }
}