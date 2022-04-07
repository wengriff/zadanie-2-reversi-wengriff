package sk.stuba.fei.uim.oop.Controller;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Entity.Enemy;
import sk.stuba.fei.uim.oop.Entity.Entity;
import sk.stuba.fei.uim.oop.Entity.Player;

public class MoveLogic {
    private Board board;
    private Enemy enemy;
    private Player player;
    private Cell selectedCell;

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
                if(isValidMove(nextPlayer, this.board.getBoardArray()[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void flipCells(List<Cell> cellsToFlip) {
        for(Cell cell : cellsToFlip) {
            if(cell.getOwner() == this.enemy) {
                cell.setOwner(this.player);
            } else {
                cell.setOwner(this.enemy);
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
        int column = cell.getColumn();
        while(column < this.board.getSize() - 1) {
            column++;
            Cell currentCell = this.board.getBoardArray()[cell.getRow()][column];
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
        column = cell.getColumn();
        while(column > 0) {
            column--;
            Cell currentCell = this.board.getBoardArray()[cell.getRow()][column];
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
        int row = cell.getRow();
        while(row < this.board.getSize() - 1) {
            row++;
            Cell currentCell = this.board.getBoardArray()[row][cell.getColumn()];
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
        row = cell.getRow();
        while(row > 0) {
            row--;
            Cell currentCell = this.board.getBoardArray()[row][cell.getColumn()];
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
        row = cell.getRow();
        column = cell.getColumn();
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
        row = cell.getRow();
        column = cell.getColumn();
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
        row = cell.getRow();
        column = cell.getColumn();
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
        row = cell.getRow();
        column = cell.getColumn();
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

    public Cell getSelectedCell() { return this.selectedCell; }

    public void setSelectedCell(Cell selectedCell) { this.selectedCell = selectedCell; }
}