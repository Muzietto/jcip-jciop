package net.faustinelli.funkyJavaGym.net.faustinelli.bowling;

public class Frame {

    private int score = 0;
    private final Frame previousFrame;
    private Game game;

    public boolean isFirstRoll = true;
    public boolean isStrike = false;

    public Frame(Frame previousFrame, Game game) {
        this.previousFrame = previousFrame;
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore(int pins) {
        this.score += pins;
    }

    public int computeScore() {
        return this.score + ((previousFrame != null) ? previousFrame.computeScore() : 0);
    }

    public int numberOfFrames() {
        return 1 + ((previousFrame != null) ? previousFrame.numberOfFrames() : 0);
    }

    public void roll(int pins) {
        if (game.numberOfFrames() < 11) {
            incrementScore(pins);
        }

        if (previousFrameWasStrike()) {
            previousFrame.incrementScore(pins);
        }

        if (isFirstRoll) {

            if (previousFrameWasSpare()) {
                previousFrame.incrementScore(pins);
            }

            isFirstRoll = false;

            if (pins == 10) {
                isStrike = true;
                game.init();
            }
        } else {
            game.init();
        }
    }

    private boolean previousFrameWasSpare() {
        return previousFrame != null && previousFrame.getScore() == 10 && !previousFrame.isStrike;
    }

    private boolean previousFrameWasStrike() {
        return previousFrame != null && previousFrame.isStrike;
    }
}
