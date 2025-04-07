package test.java.net.faustinelli.bowling2025;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.java.net.faustinelli.bowling2025.Game;

public class GameTests {

  private Game game;

  @Before
  public void setUp() {

    game = new Game();
  }

  @Test
  public void test_returnThis() {

    Game result = game.roll(5);

    String toString = result.toString();

    assertEquals("main.java.net.faustinelli.bowling2025.Game", toString.split("@")[0]);

  }

  @Test
  public void test_01() {

    int result = game.score();

    assertEquals(0, result);

  }

  @Test
  public void test_startGame() {

    int result = game.roll(5).score();

    assertEquals(5, result);

    int result2 = game.roll(1).score();

    assertEquals(5 + 1, result2);

  }

  @Test
  public void test_secondTurn() {

    int resultFirstTurn = game.roll(5).roll(4).score();

    assertEquals(9, resultFirstTurn);

    int resultNextRoll = game.roll(5).score();

    assertEquals(9 + 5, resultNextRoll);

  }

  @Test
  public void test_errorTooMainPinsInOneRoll() {

    int result = game.roll(11).score();
    assertEquals(-1, result);

    assertEquals("ERROR: TOO MANY PINS IN ONE ROLL", game.errorReason());

    int result2 = game.roll(1).roll(2).score();
    assertEquals(-1, result2);

  }

  @Test
  public void test_errorTooManyPinsInOneTurn() {

    int result = game.roll(5).roll(6).score();
    assertEquals(-1, result);

    assertEquals("ERROR: TOO MANY PINS IN ONE TURN", game.errorReason());

    int result2 = game.roll(1).roll(2).score();
    assertEquals(-1, result2);

  }

  @Test
  public void test_spare() {

    int resultFirstTurn = game.roll(5).roll(5).score();
    assertEquals(10, resultFirstTurn);

    int resultNextRoll = game.roll(1).score();
    assertEquals(10 + 1 + 1, resultNextRoll);
  }

  @Test
  public void test_strike() {

    int resultFirstTurn = game.roll(10).score();
    assertEquals(10, resultFirstTurn);

    int resultFirstRoll = game.roll(1).score();
    assertEquals(10 + 1, resultFirstRoll);

    int resultSecondRoll = game.roll(2).score();
    assertEquals(11 + 2 + (1 + 2), resultSecondRoll);

  }

  @Test
  public void test_spareAndThenSpare() {

    int resultFirstSpare = game.roll(5).roll(5).score();
    assertEquals(10, resultFirstSpare);

    int resultNextSpare = game.roll(1).roll(9).score();
    assertEquals(10 + 1 + 10, resultNextSpare);

    int resultFirstRoll = game.roll(3).score();
    assertEquals(21 + 3 + 3, resultFirstRoll);
  }

  @Test
  public void test_spareAndThenStrike() {

    int resultFirstSpare = game.roll(5).roll(5).score();
    assertEquals(10, resultFirstSpare);

    int resultNextStrike = game.roll(10).score();
    assertEquals(10 + 10 + 10, resultNextStrike);

    int resultFirstRoll = game.roll(3).score();
    assertEquals(30 + 3, resultFirstRoll);

    int resultSecondRoll = game.roll(2).score();
    assertEquals(33 + 2 + (3 + 2), resultSecondRoll);
  }

  @Test
  public void test_strikeAndThenStrike() {

    int resultFirstStrike = game.roll(10).score();
    assertEquals(10, resultFirstStrike);

    int resultSecondStrike = game.roll(10).score();
    assertEquals(10 + 10 + 10, resultSecondStrike);

    int resultFirstRoll = game.roll(2).score();
    assertEquals(30 + 2, resultFirstRoll);

    int resultSecondRoll = game.roll(1).score();
    assertEquals(32 + 1 + (2 + 1), resultSecondRoll);

  }

  @Test
  public void test_RollsString() {

    int result = game.roll("5.1").score();

    assertEquals(5 + 1, result);

  }

}