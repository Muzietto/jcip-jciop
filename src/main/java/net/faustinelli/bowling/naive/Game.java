package net.faustinelli.funkyJavaGym.net.faustinelli.bowling;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Frame> frames = new ArrayList<>();
    private Frame currentFrame;
    private Frame lastFrame;

    public Game() {
        init();
    }

    private void init() {
        lastFrame = currentFrame;
        currentFrame = new Frame();
        frames.add(currentFrame);
    }

    // how many pins did you bump?
    public void roll(int pins) {
        if (gameIsOver()) {
            throw new RuntimeException("bowling game over!");
        }

        if (this.frames.size() < 11) {
            currentFrame.score += pins;
        }

        if (lastFrameWasStrike()) {
            lastFrame.score += pins;
        }

        if (currentFrame.isFirstRoll) {

            if (lastFrameWasSpare()) {
                lastFrame.score += pins;
            }

            currentFrame.isFirstRoll = false;

            if (pins == 10) {
                currentFrame.isStrike = true;
                init();
            }
        } else {
            init();
        }
    }

    private boolean gameIsOver() {
        return this.frames.size() > 10
               && !(lastFrameWasSpare() || lastFrameWasStrike());
    }

    private boolean lastFrameWasStrike() {
        return lastFrame != null && lastFrame.isStrike;
    }

    private boolean lastFrameWasSpare() {
        return lastFrame != null && lastFrame.score == 10 && !lastFrame.isStrike;
    }

    public int score() {
        return frames.stream().reduce(0, (total, turn) -> total + turn.score, (a, b) -> a + b);
    }

    private class Frame {

        private boolean isFirstRoll = true;
        private boolean isStrike = false;
        private int score = 0;

    }
}
