package main.java.net.faustinelli.hangman;

import java.util.stream.Collectors;

public class HangmanGame {

  private String wordToGuess = "";
  private int maxAttempts = 0;
  private int attemptsSoFar = 0;
  private String alloweds = "abcdefghijklmnopqrstuvwxyz";
  private int wrongGuessesSoFar = 0;

  public HangmanGame(String wordToGuess, int maxAttempts) {
    this.wordToGuess = wordToGuess;
    this.maxAttempts = maxAttempts;
  }

  public boolean isRunning() {
    return attemptsSoFar < maxAttempts;
  }

  public Pair<Integer, String> guessp(String guess) {
    if (guess.length() > 1)
      return new Pair<Integer, String>(-1, "TOO MANY CHARS");
    if (!alloweds.contains(guess))
      return new Pair<Integer, String>(-1, "FORBIDDEN CHAR");

    int rightGuesses = wordToGuess.chars()
        .mapToObj(c -> String.valueOf((char) c))
        .filter(ch -> ch.equals(guess))
        .collect(Collectors.toList())
        .size();

    if (rightGuesses == 0)
      wrongGuessesSoFar++;
    return new Pair<Integer, String>(rightGuesses, wrongGuessesSoFar + " WRONG GUESSES");

  }

  public int guess(String guess) {
    if (guess.length() > 1)
      return -1;
    if (!alloweds.contains(guess))
      return -1;

    int rightGuesses = wordToGuess.chars()
        .mapToObj(c -> String.valueOf((char) c))
        .filter(ch -> ch.equals(guess))
        .collect(Collectors.toList())
        .size();

    return rightGuesses;
  }

}
