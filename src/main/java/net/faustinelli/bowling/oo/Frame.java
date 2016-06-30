package net.faustinelli.bowling.oo;

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

    private void incrementScore(int pins) {
        this.score += pins;
    }

    public int computeScore() {
        return this.score + ((previousFrame != null) ? previousFrame.computeScore() : 0);
    }

    public int numberOfFrames() {
        return 1 + ((previousFrame != null) ? previousFrame.numberOfFrames() : 0);
    }

    public boolean gameIsOver() {
        return numberOfFrames() > 10
               && !(previousFrameWasSpare() || previousFrameWasStrike());
    }

    public void roll(int pins) {
        if (game.numberOfFrames() < 11) {
            incrementScore(pins);
        }

        if (isFirstRoll) {
            if (previousFrameWasStrike()) {
                incrementPreviousFrameScore(pins);
                if (previousFrame.previousFrameWasStrike() && game.numberOfFrames() < 12) {
                    previousFrame.incrementPreviousFrameScore(pins);
                }
            }

            if (previousFrameWasSpare()) {
                incrementPreviousFrameScore(pins);
            }
        } else {
            if (previousFrameWasStrike()) {
                incrementPreviousFrameScore(pins);
            }
        }

        updateGameState(pins);
    }

    private void incrementPreviousFrameScore(int pins) {
        if (previousFrame != null) {
            previousFrame.incrementScore(pins);
        }
    }

    private void updateGameState(int pins) {
        if (isFirstRoll) {
            isFirstRoll = false;
            if (pins == 10) {
                isStrike = true;
                game.init();
            }
        } else {
            game.init();
        }
    }

    public boolean previousFrameWasSpare() {
        return previousFrame != null && previousFrame.getScore() == 10 && !previousFrame.isStrike;
    }

    public boolean previousFrameWasStrike() {
        return previousFrame != null && previousFrame.isStrike;
    }
}
