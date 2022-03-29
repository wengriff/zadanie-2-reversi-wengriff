package sk.stuba.fei.uim.oop;

import lombok.Getter;
import lombok.Setter;

public class Controller extends Listeners {
    @Getter
    private Board board;

    @Getter
    private Player player;

    public Controller() {
        this.board = new Board();
        this.player = new Player();
    }
}
