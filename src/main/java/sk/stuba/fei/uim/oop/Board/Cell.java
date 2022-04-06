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
    private int i,j;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void setOwner(Entity owner) { 
        this.owner = owner; 
        this.assignCellToOwner(this, this.owner);
        this.setBackground(this.owner.getColor());
    }

    private void assignCellToOwner(Cell cell, Entity owner) {
        owner.getCells().add(cell);
    }

    public Entity getOwner() { return this.owner; }

    public void removeOwner() { this.owner = null; }

    public boolean hasOwner() { return this.owner != null; }

    public int getJ() { return this.j; }

    public int getI() { return this.i; }
}
