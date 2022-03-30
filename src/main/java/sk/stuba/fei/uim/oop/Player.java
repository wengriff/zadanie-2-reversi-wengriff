package sk.stuba.fei.uim.oop;

import java.awt.Color;
import java.awt.Graphics;

import lombok.Getter;

public class Player {
    
    private Controller controller; 

    public Player(Controller controller) {
        this.controller = controller;
    }

    public void display(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(50, 50, 50, 50);
        g.setColor(Color.BLACK);
    }
}
