package net.faustinelli.bowling.oo_with_inheritance;

public class Frame {

    protected int score = 0;
    protected final Frame previousFrame;
    protected Game game;

    protected boolean isFirstRoll = true;
    protected boolean isSecondRoll = false;
    protected boolean isStrike = false;

    public Frame(Frame previousFrame, Game game) {
        this.previousFrame = previousFrame;
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    private void incrementScore(int pins) {
        this.score += pins;
    }

    public int computeScore() {
        return this.score + ((previousFrame != null) ? previousFrame.computeScore() : 0);
    }

    public int numberOfFrames() {
        return 1 + ((previousFrame != null) ? previousFrame.numberOfFrames() : 0);
    }

    public boolean gameIsStillGoingOn() {
        return true;
    }

    public void roll(int pins) {
        updateScores(pins);
        updateGameState(pins);
    }

    protected void updateScores(int pins) {
        incrementScore(pins);

        if (isFirstRoll) {
            if (previousFrameWasStrike()) {
                incrementPreviousFrameScore(pins);
                if (previousFrame.previousFrameWasStrike()) {
                    previousFrame.incrementPreviousFrameScore(pins);
                }
            }

            if (previousFrameWasSpare()) {
                incrementPreviousFrameScore(pins);
            }
        } else if (isSecondRoll) {
            if (previousFrameWasStrike()) {
                incrementPreviousFrameScore(pins);
            }
        }
    }

    private void incrementPreviousFrameScore(int pins) {
        if (previousFrame != null) {
            previousFrame.incrementScore(pins);
        }
    }

    private void updateGameState(int pins) {
        if (isFirstRoll) {
            isFirstRoll = false;
            isSecondRoll = true;
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
