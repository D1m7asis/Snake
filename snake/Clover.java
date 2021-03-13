package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Clover extends GameObject {

    private static final String CLOVER = "\uD83D\uDCA9";

    public boolean isAlive = true;

    public Clover(int x, int y) {
        super(x, y);
    }

    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.NONE, CLOVER, Color.CHOCOLATE, 75);
    }
}
