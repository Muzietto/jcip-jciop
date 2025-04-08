package test.java.net.faustinelli.hangman;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import org.junit.Before;
import static org.junit.Assert.assertTrue;

import main.java.net.faustinelli.hangman.*;

public class HangmanTests {

  private HangmanGame game;

  @Before
  public void setUp() {
    game = new HangmanGame("paperino", 5);
  }

  @Test
  public void test_setUp() {
    String toString = game.toString();
    assertEquals("main.java.net.faustinelli.hangman.HangmanGame", toString.split("@")[0]);
    assertTrue(game.isRunning());
  }

  @Test
  public void test_guessMultipleCharacters() {
    Pair<Integer, String> result = game.guessp("asdfg");
    assertEquals(Integer.valueOf(-1), result.first());
    assertEquals("TOO MANY CHARS", result.second());
  }

  @Test
  public void test_guessForbiddenCharacter() {
    Pair<Integer, String> result = game.guessp("A");
    assertEquals(Integer.valueOf(-1), result.first());
    assertEquals("FORBIDDEN CHAR", result.second());
  }

  @Test
  public void test_guessAbsentCharacter() {
    Pair<Integer, String> result = game.guessp("k");
    assertEquals(Integer.valueOf(0), result.first());
    assertEquals("1 WRONG GUESSES", result.second());
  }

  @Test
  public void test_guessPresentCharacter() {
    Pair<Integer, String> result = game.guessp("p");
    assertEquals(Integer.valueOf(2), result.first());
    assertEquals("0 WRONG GUESSES", result.second());
  }

  @Test
  public void test_MultipleWrongGuesses() {
    Pair<Integer, String> result = game.guessp("k");
    assertEquals(Integer.valueOf(0), result.first());
    assertEquals("1 WRONG GUESSES", result.second());

    result = game.guessp("k");
    assertEquals(Integer.valueOf(0), result.first());
    assertEquals("2 WRONG GUESSES", result.second());
  }

}