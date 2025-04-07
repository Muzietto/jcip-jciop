package main.java.net.faustinelli.bowling2025;

import java.util.Arrays;

public class Game {
  private int score = 0;
  private int lastRollPins = 0;
  private String lastTurnOutcome = "";
  private String errorReason = "";

  public int score() {
    return this.score;
  }

  public String errorReason() {
    return this.errorReason;
  }

  public Game roll(String rollsString) {

    int[] _rolls = Arrays.stream(rollsString.split("."))
        .mapToInt(Integer::parseInt)
        .toArray();

    // int[] _rollsXXX = Arrays.stream(rollsString.split(""))
    //     .map(char -> Integer.parseInt(char))
    //     .toArray();

    for (int pins : _rolls) {
      this.roll(pins);
    }

    return this;
  }

  public Game roll(int pins) {

    if (this.score < 0) // errored game
      return this;

    if (pins > 10) {
      score = -1;
      errorReason = "ERROR: TOO MANY PINS IN ONE ROLL";
      return this;
    }

    if (lastRollPins + pins > 10) {
      score = -1;
      errorReason = "ERROR: TOO MANY PINS IN ONE TURN";
      return this;
    }

    if (lastRollPins > 0 && lastRollPins + pins < 10) { // second roll - end turn

      if (lastTurnOutcome == "STRIKE") {
        lastTurnOutcome = "";
        score = score + lastRollPins + pins;
      }
  
      lastTurnOutcome = "";
      lastRollPins = 0;
      score = score + pins;
      return this;
    }

    if (lastRollPins > 0 && lastRollPins + pins == 10) { // spare

      if (lastTurnOutcome == "STRIKE") {
        lastTurnOutcome = "";
        score = score + lastRollPins;
      }

      lastTurnOutcome = "SPARE";
      lastRollPins = 0;
      score = score + pins;
      return this;
    }

    if (lastRollPins == 0 && pins == 10) { // strike

      if (lastTurnOutcome == "SPARE") {
        lastTurnOutcome = "";
        score = score + 10;
      }
  
      if (lastTurnOutcome == "STRIKE") {
        lastTurnOutcome = "";
        score = score + 10;
      }

      lastTurnOutcome = "STRIKE";
      score = score + 10;
      return this;
    }

    // handling first roll
    if (lastTurnOutcome == "SPARE") {
      lastTurnOutcome = "";
      score = score + pins;
    }

    lastRollPins = pins;
    score = score + pins;
    return this;
  }
}
