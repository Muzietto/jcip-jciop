package net.faustinelli.funkyJavaGym.net.faustinelli.bowling;

public class Game {

    private Frame currentFrame;
    private Frame previousFrame;

    public Game() {
        init();
    }

    public void init() {
        previousFrame = currentFrame;
        currentFrame = new Frame(previousFrame, this);
    }

    // how many pins did you bump?
    public void roll(int pins) {
        if (gameIsOver()) {
            throw new RuntimeException("bowling game over!");
        }

        currentFrame.roll(pins);

    }

    private boolean gameIsOver() {
        return numberOfFrames() > 10
               && !(previousFrameWasSpare() || previousFrameWasStrike());
    }

    private boolean previousFrameWasStrike() {
        return previousFrame != null && previousFrame.isStrike;
    }

    private boolean previousFrameWasSpare() {
        return previousFrame != null && previousFrame.getScore() == 10 && !previousFrame.isStrike;
    }

    public int score() {
        return currentFrame.computeScore();
    }

    public int numberOfFrames() {
        return currentFrame.numberOfFrames();
    }
}
