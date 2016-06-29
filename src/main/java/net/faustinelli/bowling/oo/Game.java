package net.faustinelli.funkyJavaGym.net.faustinelli.bowling.oo;

public class Game {

    private Frame currentFrame;

    public Game() {
        init();
    }

    public void init() {
        currentFrame = new Frame(currentFrame, this);
    }

    // how many pins did you bump?
    public void roll(int pins) {
        if (currentFrame.gameIsOver()) {
            throw new RuntimeException("bowling game over!");
        }

        currentFrame.roll(pins);
    }

    public int score() {
        return currentFrame.computeScore();
    }

    public int numberOfFrames() {
        return currentFrame.numberOfFrames();
    }
}
