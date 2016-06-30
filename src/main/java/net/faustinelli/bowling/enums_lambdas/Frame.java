package net.faustinelli.funkyJavaGym.bowling.enums_lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Frame {

    protected final Frame previousFrame;
    protected Game game;
    protected List<Roll> rolls;

    protected boolean isStrike = false;
    protected boolean gameOver = false;

    public Frame(Frame previousFrame, Game game) {
        this.previousFrame = previousFrame;
        this.game = game;
        this.rolls = new ArrayList<Roll>();
    }

    public int getScore() {
        Optional<Integer> optional = rolls.stream().map(Roll::getScore).reduce(Integer::sum);
        return optional.orElse(0);
    }

    private void incrementScore(Roll extraRoll) {
        rolls.add(extraRoll);
    }

    public int computeScore() {
        return getScore() + ((previousFrame != null) ? previousFrame.computeScore() : 0);
    }

    public int numberOfFrames() {
        return 1 + ((previousFrame != null) ? previousFrame.numberOfFrames() : 0);
    }

    public boolean gameIsStillGoingOn() {
        return true;
    }

    public void roll(int pins) {
        Roll newRoll = newRoll(pins);
        updateOtherScores(newRoll);
        updateGameState(newRoll);
    }

    protected void updateOtherScores(Roll roll) {
        roll.updateOtherScores();
    }

    protected Roll newRoll(int pins) {
        int rollsSize = this.rolls.size();
        switch (rollsSize) {
            case 0: {
                return buildAndStoreNewRoll(pins, Roll.Number.FIRST);
            }
            case 1: {
                return buildAndStoreNewRoll(pins, Roll.Number.SECOND);
            }
            case 2: {
                return buildAndStoreNewRoll(pins, Roll.Number.THIRD);
            }
            default: {
                throw new RuntimeException("can't have more than three rolls");
            }
        }
    }

    private Roll buildAndStoreNewRoll(int pins, Roll.Number rollNumber) {
        Roll newRoll = new Roll(pins, rollNumber, this);
        this.rolls.add(newRoll);
        return newRoll;
    }

    public void incrementPreviousFrameScore(Roll extraRoll) {
        if (previousFrame != null) {
            previousFrame.incrementScore(extraRoll);
        }
    }

    private void updateGameState(Roll roll) {
        roll.updateGameState();
    }

    public boolean previousFrameWasSpare() {
        return previousFrame != null && previousFrame.getScore() == 10 && !previousFrame.isStrike;
    }

    public boolean previousFrameWasStrike() {
        return previousFrame != null && previousFrame.isStrike;
    }
}
