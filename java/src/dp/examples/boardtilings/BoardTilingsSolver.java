package dp.examples.boardtilings;

import java.util.*;

public class BoardTilingsSolver {

  private int[] tiles;

  private Map<Integer, Integer> tileFrequency = new HashMap<>();

  public BoardTilingsSolver(int[] tiles) {
    this.tiles = tiles;
    init();
  }

  private void init() {

    for (int tile : tiles) {
      tileFrequency.put(tile, tileFrequency.getOrDefault(tile, 0) + 1);
    }
  }

  public long iterativeSolution(int n) {
    long[] dp = new long[n + 1];
    dp[0] = 1;
    for (int i = 1; i <= n; i++) {
      for (int tile : tileFrequency.keySet()) {
        if (i - tile < 0) continue;
        dp[i] += dp[i - tile] * tileFrequency.get(tile);
      }
    }
    return dp[n];
  }



  public long recursiveSolution(int n) {


    Map<Integer, Long> dp = new HashMap<>();
    return f(n, dp);
  }

  private long f(int n, Map<Integer, Long> dp) {

    if (n == 0) {
      return 1;
    }

    Long count = dp.get(n);
    if (count != null) {
      return count;
    }
    count = 0L;
    for (int tile : tileFrequency.keySet()) {
      if (n - tile < 0) continue;
      count += f(n - tile, dp) * tileFrequency.get(tile);
    }

    dp.put(n, count);
    return count;
  }

  public static void main(String[] args) {
    int n = 25;
    int[] tiles = {1, 1, 2, 4};
    BoardTilingsSolver solver = new BoardTilingsSolver(tiles);
    System.out.printf("f(%d) = %d\n", n, solver.iterativeSolution(n));
    System.out.printf("f(%d) = %d\n", n, solver.iterativeSolution(n));
    System.out.println();
    for (int i = 0; i < 10; i++) {
      System.out.printf("f(%d) = %d (iterative soln)\n", i, solver.iterativeSolution(i));
      System.out.printf("f(%d) = %d (recursive soln)\n", i, solver.recursiveSolution(i));
    }












  }
}
