package sk.stuba.fei.uim.oop.Entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Controller.MoveLogic;

public class Enemy extends Entity {

    public Enemy() {
        super.name = "Computer";
        super.color = Color.BLACK;
    }

    @Override
    public void move(Board board, MoveLogic moveLogic) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell bestMove;
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getBoardArray()[i][j];
                if(moveLogic.isValidMove(this, cell)) {
                    possibleMoves.add(cell);
                }
            }
        }
        bestMove = possibleMoves.get(0);
        for(Cell cell : possibleMoves) {
            if(moveLogic.getCellsToFlip(this, cell).size() > moveLogic.getCellsToFlip(this, bestMove).size()) {
                bestMove = cell;
            }
        }
        List<Cell> cellsToFlip = moveLogic.getCellsToFlip(this, bestMove);
        moveLogic.flipCells(cellsToFlip);
        bestMove.setOwner(this);
    }
}