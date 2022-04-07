package sk.stuba.fei.uim.oop.Board;

import javax.swing.JPanel;
import java.awt.*;

import sk.stuba.fei.uim.oop.Entity.Entity;


public class Cell extends JPanel {
    public static final Color DEFAULT_CELL_COLOR = new Color(7, 176, 30);
    public static final Color DEFAULT_CELL_HIGHLIGHT = new Color(7, 150, 30);
    public static final Color POSSIBLE_MOVE_CELL_HIGHLIGHT = new Color(153, 204, 255);
    public static final Color POSSIBLE_MOVE_CELL_HOVER_HIGHLIGHT = new Color(51, 153, 255); 
    private Entity owner;
    private int row,column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setOwner(Entity owner) { 
        this.owner = owner; 
        this.assignCellToOwner(this, this.owner);
        this.setBackground(this.owner.getColor());
    }

    private void assignCellToOwner(Cell cell, Entity owner) {
        owner.getCells().add(cell);
    }

    public void setCellHighlight() {
        if(!this.hasOwner()) {
            if(this.getBackground() != Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                this.setBackground(Cell.DEFAULT_CELL_HIGHLIGHT);
            } else if (this.getBackground() == Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                this.setBackground(Cell.POSSIBLE_MOVE_CELL_HOVER_HIGHLIGHT);
            }
        }
    }

    public void removeCellHighlight() {
        if(!this.hasOwner()) {
            if(this.getBackground() == Cell.POSSIBLE_MOVE_CELL_HOVER_HIGHLIGHT) {
                this.setBackground(Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT);
            } else if (this.getBackground() != Cell.POSSIBLE_MOVE_CELL_HIGHLIGHT) {
                this.setBackground(Cell.DEFAULT_CELL_COLOR);
            }
        }
    }

    public Entity getOwner() { return this.owner; }

    // public void removeOwner() { this.owner = null; }

    public boolean hasOwner() { return this.owner != null; }

    public int getColumn() { return this.column; }

    public int getRow() { return this.row; }
}