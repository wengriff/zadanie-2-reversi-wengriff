package sk.stuba.fei.uim.oop.Entity;
import java.awt.Color;
import java.util.List;

import sk.stuba.fei.uim.oop.Board.Cell;

public class Entity {
    protected Color color;
    protected String name;
    protected List<Cell> cells;
    
    public Color getColor() { return this.color; }

    public void setColor(Color color) { this.color = color; }

    public String getName() { return this.name; }

    public List<Cell> getCells() { return this.cells; }
}
