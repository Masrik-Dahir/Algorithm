
package dp;

import java.awt.geom.*;
import java.util.*;

public class MinimumWeightPerfectMatching {


  private final int n;
  private double[][] cost;


  private final int END_STATE;
  private boolean solved;


  private double minWeightCost;
  private int[] matching;


  public MinimumWeightPerfectMatching(double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n == 0) throw new IllegalArgumentException("Matrix size is zero");
    if (n % 2 != 0)
      throw new IllegalArgumentException("Matrix has an odd size, no perfect matching exists.");
    if (n > 32)
      throw new IllegalArgumentException(
          "Matrix too large! A matrix that size for the MWPM problem with a time complexity of"
              + "O(n^2*2^n) requires way too much computation and memory for a modern home computer.");
    END_STATE = (1 << n) - 1;
    this.cost = cost;
  }

  public double getMinWeightCost() {
    solveRecursive();
    return minWeightCost;
  }


  public int[] getMinWeightCostMatching() {
    solveRecursive();
    return matching;
  }



  public void solveRecursive() {
    if (solved) return;
    Double[] dp = new Double[1 << n];
    int[] history = new int[1 << n];
    minWeightCost = f(END_STATE, dp, history);
    reconstructMatching(history);
    solved = true;
  }

  private double f(int state, Double[] dp, int[] history) {
    if (dp[state] != null) {
      return dp[state];
    }
    if (state == 0) {
      return 0;
    }
    int p1, p2;

    for (p1 = 0; p1 < n; p1++) {
      if ((state & (1 << p1)) > 0) {
        break;
      }
    }
    int bestState = -1;
    double minimum = Double.MAX_VALUE;

    for (p2 = p1 + 1; p2 < n; p2++) {

      if ((state & (1 << p2)) > 0) {
        int reducedState = state ^ (1 << p1) ^ (1 << p2);
        double matchCost = f(reducedState, dp, history) + cost[p1][p2];
        if (matchCost < minimum) {
          minimum = matchCost;
          bestState = reducedState;
        }
      }
    }
    history[state] = bestState;
    return dp[state] = minimum;
  }

  public void solve() {
    if (solved) return;









    Double[] dp = new Double[1 << n];



    int[] history = new int[1 << n];




    final int numPairs = (n * (n - 1)) / 2;
    int[] pairStates = new int[numPairs];
    double[] pairCost = new double[numPairs];

    int k = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int state = (1 << i) | (1 << j);
        dp[state] = cost[i][j];
        pairStates[k] = state;
        pairCost[k] = cost[i][j];
        k++;
      }
    }

    for (int state = 0b11; state < (1 << n); state++) {


      if (dp[state] == null) {
        continue;
      }
      for (int i = 0; i < numPairs; i++) {
        int pair = pairStates[i];

        if ((state & pair) != 0) continue;

        int newState = state | pair;
        double newCost = dp[state] + pairCost[i];
        if (dp[newState] == null || newCost < dp[newState]) {
          dp[newState] = newCost;



          history[newState] = state;
        }
      }
    }

    reconstructMatching(history);

    minWeightCost = dp[END_STATE];
    solved = true;
  }





  private void reconstructMatching(int[] history) {

    int[] map = new int[n];
    int[] leftNodes = new int[n / 2];


    for (int i = 0, state = END_STATE; state != 0; state = history[state]) {

      int pairUsed = state ^ history[state];

      int leftNode = getBitPosition(Integer.lowestOneBit(pairUsed));
      int rightNode = getBitPosition(Integer.highestOneBit(pairUsed));

      leftNodes[i++] = leftNode;
      map[leftNode] = rightNode;
    }


    java.util.Arrays.sort(leftNodes);

    matching = new int[n];
    for (int i = 0; i < n / 2; i++) {
      matching[2 * i] = leftNodes[i];
      int rightNode = map[leftNodes[i]];
      matching[2 * i + 1] = rightNode;
    }
  }



  private int getBitPosition(int k) {
    int count = -1;
    while (k > 0) {
      count++;
      k >>= 1;
    }
    return count;
  }



  public static void main(String[] args) {




  }

  private static boolean include(int i) {
    boolean toInclude = Integer.bitCount(i) >= 2 && Integer.bitCount(i) % 2 == 0;
    return toInclude;
  }

  private static void test1() {

    int n = 6;
    List<Point2D> pts = new ArrayList<>();


    for (int i = 0; i < n / 2; i++) {
      pts.add(new Point2D.Double(2 * i, 0));
      pts.add(new Point2D.Double(2 * i, 1));
    }
    Collections.shuffle(pts);

    double[][] cost = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        cost[i][j] = pts.get(i).distance(pts.get(j));
      }
    }

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    double minCost = mwpm.getMinWeightCost();
    if (minCost != n / 2) {
      System.out.printf("MWPM cost is wrong! Got: %.5f But wanted: %d\n", minCost, n / 2);
    } else {
      System.out.printf("MWPM is: %.5f\n", minCost);
    }

    int[] matching = mwpm.getMinWeightCostMatching();
    for (int i = 0; i < matching.length / 2; i++) {
      int ii = matching[2 * i];
      int jj = matching[2 * i + 1];
      System.out.printf(
          "(%d, %d) <-> (%d, %d)\n",
          (int) pts.get(ii).getX(),
          (int) pts.get(ii).getY(),
          (int) pts.get(jj).getX(),
          (int) pts.get(jj).getY());
    }
  }

  private static void test2() {
    double[][] costMatrix = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    if (cost != 2.0) {
      System.out.println("error cost not 2");
    }
    System.out.println(cost);


  }
}
