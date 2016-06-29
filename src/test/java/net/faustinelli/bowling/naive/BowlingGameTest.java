package net.faustinelli.funkyJavaGym.net.faustinelli.bowling.naive;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameTest {

    private Game g;

    @Before
    public void setUp() {
        this.g = new Game();
    }

    @Test
    public void testAllInGutter() {
        rollMany(20, 0);
        assertEquals(0, g.score());
    }

    @Test
    public void testAllOnes() {
        rollMany(20, 1);
        assertEquals(20, g.score());
    }

    @Test
    public void testOneSpare() {
        rollSpare(4);
        g.roll(3);
        g.roll(3);
        rollMany(16, 0);
        assertEquals(19, g.score());
    }

    @Test
    public void testJustTenFrames() {
        try {
            rollMany(21, 0);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testTwoSpares() {
        rollFrame(1, 4); // 5
        rollFrame(4, 5); // 9
        rollSpare(6);    // 10+5
        rollSpare(5);    // 10+0
        rollMany(12, 0);
        assertEquals(29+10, g.score());
    }

    @Test
    public void testOneStrike() {
        rollStrike();
        rollFrame(3, 4);
        rollMany(16, 0);
        assertEquals(24, g.score());
    }

    // FAILS - strike scores aren't updated recursively
    @Test
    public void testTwoStrikes() {
        rollStrike();
        rollStrike();
        rollFrame(3, 4);
        rollMany(14, 0);
        assertEquals(47, game.score());
    }

    @Test
    public void testTwoSparesAndOneStrike() {
        rollFrame(1, 4); // 5
        rollFrame(4, 5); // 9
        rollSpare(6);    // 10+5
        rollSpare(5);    // 10+0
        rollStrike();
        rollFrame(1, 0);
        rollMany(8, 0);
        assertEquals(61, g.score());
    }

    @Test
    public void testCompleteGame() {
        rollFrame(1, 4); // 5
        rollFrame(4, 5); // 9
        rollSpare(6);    // 10+5
        rollSpare(5);    // 10+0
        rollStrike();
        rollFrame(1, 0);
        rollSpare(7);
        rollSpare(6);
        rollStrike();

        rollSpare(2);
        g.roll(6);
        assertEquals(133, g.score());
    }

    // FAILS - strike scores aren't updated recursively
    @Test
    public void testPerfectGame() {
        rollMany(12, 10);
        assertEquals(300, game.score());
    }

    private void rollFrame(int firstRoll, int secondRoll) {
        g.roll(firstRoll);
        g.roll(secondRoll);
    }

    private void rollStrike() {
        g.roll(10);
    }

    private void rollSpare(int firstRoll) {
        rollFrame(firstRoll, 10 - firstRoll);
    }

    private void rollMany(int rounds, int pins) {
        for (int i = 0; i < rounds; i++) {
            g.roll(pins);
        }
    }
}
