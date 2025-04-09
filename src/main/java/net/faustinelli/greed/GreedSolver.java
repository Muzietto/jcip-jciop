package main.java.net.faustinelli.greed;

import java.util.Arrays;
import java.util.Collections;

public class GreedSolver {
  private int maxScoreSoFar = 0;

  public int solve(String diceStr) {
    String remaining = diceStr;
    String[] dice = diceStr.split("");
    System.out.printf("dice %s \n", dice);

    for (int i = 0; i < 10; i++) {
      int score = 0;

      while (remaining.length() > 0) {

      String currentRemaining = remaining;

      Collections.shuffle(Arrays.asList(remaining));
      System.out.println(remaining);
      String diceString = String.join("", dice);

      if (diceString.contains("111")) {
        score += 1000;
        remaining = remaining.replaceFirst("111", "");
      }

      if (score > maxScoreSoFar) {
        maxScoreSoFar = score;
        System.out.printf("maxScoreSoFar %s \n", maxScoreSoFar);
      }

      if (remaining == currentRemaining) {
        break;
      }
    }
  }

    return maxScoreSoFar;
  }

}
