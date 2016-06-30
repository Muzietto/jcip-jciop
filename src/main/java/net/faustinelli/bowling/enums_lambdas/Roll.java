package net.faustinelli.funkyJavaGym.bowling.enums_lambdas;

import java.util.function.Consumer;

public class Roll {

    private final Frame frame;
    private final int score;
    private final Number rollNumber;

    public Roll(int score, Number rollNumber, Frame frame) {
        this.score = score;
        this.frame = frame;
        this.rollNumber = rollNumber;
    }

    public int getScore() {
        return score;
    }

    public void updateOtherScores() {
        rollNumber.updateOtherScores.accept(this);
    }

    public void updateGameState() {
        rollNumber.updateGameState.accept(this);
    }

    public void updateLastGameState() {
        rollNumber.updateLastGameState.accept(this);
    }

    @Override
    public String toString() {
        return "" + rollNumber + ": " + score;
    }

    public enum Number {

        FIRST(roll -> {
            if (roll.frame.previousFrameWasStrike()) {
                roll.frame.incrementPreviousFrameScore(roll);
                if (roll.frame.previousFrame.previousFrameWasStrike()) {
                    roll.frame.previousFrame.incrementPreviousFrameScore(roll);
                }
            }

            if (roll.frame.previousFrameWasSpare()) {
                roll.frame.incrementPreviousFrameScore(roll);
            }
        }, roll -> {
            if (roll.score == 10) {
                roll.frame.isStrike = true;
                roll.frame.game.init();
            }
        }, roll -> {
            if (roll.score == 10) {
                roll.frame.isStrike = true;
            }
        }),
        SECOND(roll -> {
            if (roll.frame.previousFrameWasStrike()) {
                roll.frame.incrementPreviousFrameScore(roll);
            }
        }, roll -> {
            roll.frame.game.init();
        }, roll -> {
            if (!roll.frame.isStrike && roll.frame.getScore() < 10) {
                roll.frame.gameOver = true;
            }
        }),
        THIRD(roll -> {
        }, roll -> {
            roll.frame.game.init();
        }, roll -> {
            roll.frame.gameOver = true;
        });

        private final Consumer<Roll> updateOtherScores;
        private final Consumer<Roll> updateGameState;
        private final Consumer<Roll> updateLastGameState;

        Number(Consumer<Roll> updateOtherScores, Consumer<Roll> updateGameState, Consumer<Roll> updateLastGameState) {
            this.updateOtherScores = updateOtherScores;
            this.updateGameState = updateGameState;
            this.updateLastGameState = updateLastGameState;
        }
    }
}
