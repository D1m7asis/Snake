package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;

    private Snake snake;
    private Apple apple;
    private Clover clover;

    private int turnDelay;
    private int score;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            createNewClover();

            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }

          snake.move(clover);
        if (!clover.isAlive) {
            createNewClover();
            score -= 5;
            setScore(score);
            turnDelay += 10;
            setTurnTimer(turnDelay);
        }

        if (!snake.isAlive) {
            gameOver();
        }

        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }




    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }

        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        }
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        createNewClover();
        isGameStopped = false;

        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i, j, Color.DARKGREEN, "\uD83C\uDF42", Color.GREEN, 60); //100 - 3D Эффект
            }
        }
        snake.draw(this);
        apple.draw(this);
        clover.draw(this);
    }

    private void createNewApple() {
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }

    private void createNewClover() {
        Clover newClover;
            // do {
                int x = snake.getTailX(); //getRandomNumber(WIDTH); // FIXME: 13.03.2021 Нужно создавать какашку после съедания яблока, за хвостом
                int y = snake.getTailY(); //getRandomNumber(HEIGHT);
                newClover = new Clover(x, y);
                // } while (snake.checkCollision(newClover));
            clover = newClover;
    }


    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "\uD835\uDD4E\uD835\uDD38\uD835\uDD4A\uD835\uDD4B\uD835\uDD3C\uD835\uDD3B", Color.RED, 50);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "\uD835\uDD44\uD835\uDD40\uD835\uDD4A\uD835\uDD4A\uD835\uDD40\uD835\uDD46ℕ " +
                                             "ℙ\uD835\uDD38\uD835\uDD4A\uD835\uDD4A\uD835\uDD3C\uD835\uDD3B", Color.LIMEGREEN, 35);
    }
}
