package sk.stuba.fei.uim.oop.Entity;

import java.awt.Color;
import java.util.List;

import sk.stuba.fei.uim.oop.Board.Board;
import sk.stuba.fei.uim.oop.Board.Cell;
import sk.stuba.fei.uim.oop.Controller.MoveLogic;

public class Player extends Entity {

    public Player() {
        super.name = "Player";
        super.color = Color.WHITE;
    }

    @Override
    public void move(Board board, MoveLogic moveLogic) {
        List<Cell> cellsToFlip = moveLogic.getCellsToFlip(this, moveLogic.getSelectedCell());
        moveLogic.flipCells(this,cellsToFlip);
        moveLogic.getSelectedCell().setOwner(this);
    }
}