package test.java.net.faustinelli.greed;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import org.junit.Before;
import static org.junit.Assert.assertTrue;

import main.java.net.faustinelli.greed.*;

public class GreedTests {
private GreedSolver solver;

  @Before
  public void setUp() {
    solver = new GreedSolver();
  }

  @Test
  public void test_setUp() {
    String toString = solver.toString();
    assertEquals("main.java.net.faustinelli.greed.GreedSolver", toString.split("@")[0]);
  }

  @Test
  public void test_XXXXX() {
    int result = solver.solve("1211");
    assertEquals(1000, result);
  }

}