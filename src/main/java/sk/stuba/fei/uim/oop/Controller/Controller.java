package sk.stuba.fei.uim.oop.Controller;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.*;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Entity;
import sk.stuba.fei.uim.oop.Entity.Player;
import sk.stuba.fei.uim.oop.Menu.Menu;


import java.awt.event.*;
import java.awt.event.ActionEvent;

public class Controller extends Listeners {
    private Frame frame;
    private Board board;
    private Player player;
    private Enemy enemy;
    private Entity nextPlayer;
    private MoveLogic moveLogic;
    private Menu menu;

    public Controller(Frame frame) {
        this.frame = frame;
        this.board = new Board(this);
        this.player = new Player();
        this.enemy = new Enemy();
        this.nextPlayer = this.player;
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
        this.nextPlayer = this.player;
        this.moveLogic.showPossibleMoves();
        this.frame.revalidate();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            // case KeyEvent.VK_W:
            //     if(this.nextPlayer instanceof Enemy) {
            //         this.enemy.move(this.board, this.moveLogic);
            //         this.flipPlayer();
            //     }
            //     this.moveLogic.showPossibleMoves();
            // break;
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
        this.board.setSize(this.menu.getBoardSizeSlider().getValue());
        this.menu.updateBoardSizeLabel();
        this.restartGame();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        this.moveLogic.setSelectedCell(cell);

        if(cell.hasOwner()) {
            return;
        }

        if(!this.moveLogic.isValidMove(this.player , cell)) {
            return;
        }

        if(this.nextPlayer instanceof Player) {
            this.player.move(this.board, this.moveLogic);
            this.flipPlayer();
        }

        System.out.println(this.moveLogic.hasValidMove(this.enemy) + " first enemy");
        if(!this.moveLogic.hasValidMove(this.enemy) || this.isGameFinished()) {
            if(this.isGameFinished()) {
                System.out.println("eeeee");
                this.displayWinner();
                return;
            }
            this.flipPlayer();
            return;
        }

        // this.delay(2000);
        System.out.println(this.moveLogic.hasValidMove(this.enemy) + " second enemy");
        if(!this.moveLogic.hasValidMove(this.enemy)) {
            if(this.isGameFinished()) {
                System.out.println("ddddddd");
                this.displayWinner();
                return;
            }
            this.flipPlayer();
            return;
        }

        // Works
        // ---------------------------------------------------------------------------
        System.out.println(this.moveLogic.hasValidMove(this.enemy) + " third enemy");
        if(this.moveLogic.hasValidMove(this.enemy) && this.nextPlayer instanceof Enemy) {
            do {
                if(this.isGameFinished()) {
                    System.out.println("kkkkkk");
                    this.displayWinner();
                    return;
                }
                this.enemy.move(this.board, this.moveLogic);
            } while(!this.moveLogic.hasValidMove(this.player));
            this.flipPlayer();
        }
        // ---------------------------------------------------------------------------

        if(this.isGameFinished()) {
            System.out.println("hhhhhhhh");
            this.displayWinner();
            return;
        }
        System.out.println(this.moveLogic.hasValidMove(this.player) + " first player");
        this.moveLogic.showPossibleMoves();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(!cell.hasOwner()) {
            if(cell.getBackground() != Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                cell.setBackground(Cell.DEFAULT_CELL_HIGHLIGHT);
            } else if (cell.getBackground() == Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                cell.setBackground(Cell.POSSIBLE_MOVE_CELL_HOVER_HIGHLIGHT);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Cell cell = (Cell)e.getSource();
        if(!cell.hasOwner()) {
            if(cell.getBackground() == Cell.POSSIBLE_MOVE_CELL_HOVER_HIGHLIGHT) {
                cell.setBackground(Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT);
            } else if (cell.getBackground() != Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                cell.setBackground(Cell.DEFAULT_CELL_COLOR);
            }
        }
    }

    private boolean isGameFinished() {
        this.moveLogic.showPossibleMoves();
        return (!this.moveLogic.hasValidMove(this.player) && !this.moveLogic.hasValidMove(this.enemy));
    }

    private void flipPlayer() {
        if(this.nextPlayer instanceof Player) {
            this.nextPlayer = this.enemy;
        } else {
            this.nextPlayer = this.player;
        }
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