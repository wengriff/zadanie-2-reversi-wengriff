package sk.stuba.fei.uim.oop.Board;

import javax.swing.JPanel;

import sk.stuba.fei.uim.oop.Entity.Entity;


public class Cell extends JPanel {
    private Entity owner;

    public void setOwner(Entity entity) { 
        this.owner = entity; 
        this.setBackground(entity.getColor());
    }

    public Entity getOwner() { return this.owner; }

    public void removeOwner() { this.owner = null; }

    public boolean hasOwner() { return this.owner != null; }
}
