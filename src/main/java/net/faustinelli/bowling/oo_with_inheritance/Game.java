package net.faustinelli.bowling.oo_with_inheritance;

public class Game {

    private int numberOfFramesInGame = 10;
    private Frame currentFrame;

    public Game() {
        init();
    }

    public void init() {
        if (numberOfFrames() < this.numberOfFramesInGame - 1) {
            currentFrame = new Frame(currentFrame, this);
        } else {
            currentFrame = new LastFrame(currentFrame, this);
        }
    }

    // how many pins did you bump?
    public void roll(int pins) {
        if (!currentFrame.gameIsStillGoingOn()) {
            throw new RuntimeException("bowling game over!");
        }

        currentFrame.roll(pins);
    }

    public int score() {
        return currentFrame.computeScore();
    }

    public int numberOfFrames() {
        return (currentFrame == null) ? 0 : currentFrame.numberOfFrames();
    }
}
