package sk.stuba.fei.uim.oop.Controller;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Entity;
import sk.stuba.fei.uim.oop.Entity.Player;

import java.awt.*;

public class MoveLogic {
    private Board board;
    private Enemy enemy;
    private Player player;

    public MoveLogic(Board board, Player player, Enemy enemy) {
        this.board = board;
        this.player = player;
        this.enemy = enemy;
    } 

    public boolean isValidMove(Entity nextPlayer, Cell cell) {
        return !(this.getCellsToFlip(nextPlayer, cell).isEmpty());
    }
    
    public boolean hasValidMove(Entity nextPlayer) {
        for(int i = 0; i < this.board.getSize(); i ++) {
            for(int j = 0; j < this.board.getSize(); j++) {
                if(isValidMove(nextPlayer,this.board.getBoardArray()[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void flipCells(List<Cell> cellsToFlip) {
        for(Cell cell : cellsToFlip) {
            if(this.board.getBoardArray()[cell.getI()][cell.getJ()].getOwner() == this.enemy) {
                this.board.getBoardArray()[cell.getI()][cell.getJ()].setOwner(this.player);
            } else {
                this.board.getBoardArray()[cell.getI()][cell.getJ()].setOwner(this.enemy);
            }
        }
    }

    public void showPossibleMoves() {
        for(int i = 0; i < this.board.getSize(); i++) {
            for(int j = 0; j < this.board.getSize(); j++) {
                Cell cell = this.board.getBoardArray()[i][j];
                if(!cell.hasOwner()) {
                    if(cell.getBackground() == Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                        cell.setBackground(Cell.DEFAULT_CELL_COLOR);
                    }
                    if(this.isValidMove(this.player, cell)) {
                        cell.setBackground(Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT);
                    }
                }
            }
        }
    }

    public List<Cell> getCellsToFlip(Entity nextPlayer, Cell cell) {
        List<Cell> cellsToFlip = new ArrayList<>();

        // 1
        List<Cell> possibleCellsToFlip = new ArrayList<>();
        int column = cell.getJ();
        while(column < this.board.getSize() - 1) {
            column++;
            Cell currentCell = this.board.getBoardArray()[cell.getI()][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[cell.getI()][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[row][cell.getJ()];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[row][cell.getJ()];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
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
            Cell currentCell = this.board.getBoardArray()[row][column];
            var owner = currentCell.getOwner();
            if(owner == null || owner.equals(nextPlayer)) {
                if(owner != null && owner.equals(nextPlayer)) {
                    cellsToFlip.addAll(possibleCellsToFlip);
                }
                break;
            } else {
                possibleCellsToFlip.add(currentCell);
            }
        }
        return cellsToFlip;
    }
}
