package net.faustinelli.funkyJavaGym.net.faustinelli.bowling.oo_with_inheritance;

public class LastFrame extends Frame {

    private boolean isThirdRoll = false;
    private boolean gameOver = false;

    public LastFrame(Frame previousFrame, Game game) {
        super(previousFrame, game);
    }

    public void roll(int pins) {
        updateScores(pins);
        updateGameState(pins);
    }

    public boolean gameIsStillGoingOn() {
        return !gameOver;
    }

    private void updateGameState(int pins) {
        if (isFirstRoll) {
            isFirstRoll = false;
            isSecondRoll = true;
            if (pins == 10) {
                isStrike = true;
            }
        } else if (isSecondRoll) {
            isSecondRoll = false;

            if (!isStrike && score < 10) {
                gameOver = true;
            } else {
                isThirdRoll = true;
            }
        } else if (isThirdRoll) {
            isThirdRoll = false;
            gameOver = true;
        }
    }
}
