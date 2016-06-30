package net.faustinelli.funkyJavaGym.bowling.enums_lambdas;

public class LastFrame extends Frame {

    public LastFrame(Frame previousFrame, Game game) {
        super(previousFrame, game);
    }

    public void roll(int pins) {
        Roll newRoll = newRoll(pins);
        updateOtherScores(newRoll);
        updateGameState(newRoll);
    }

    public boolean gameIsStillGoingOn() {
        return !gameOver;
    }

    private void updateGameState(Roll roll) {
        roll.updateLastGameState();
    }
}
