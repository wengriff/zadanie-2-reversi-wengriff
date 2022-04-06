package sk.stuba.fei.uim.oop.Entity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import sk.stuba.fei.uim.oop.Board.Cell;

public class Entity {
    protected String name;
    protected Color color;
    protected List<Cell> cells;
    protected int num;

    public Entity() {
        this.cells = new ArrayList<>();
    }
    
    public Color getColor() { return this.color; }

    public void setColor(Color color) { this.color = color; }

    public String getName() { return this.name; }

    public List<Cell> getCells() { return this.cells; }

    public int getNum() { return this.num; }
}
