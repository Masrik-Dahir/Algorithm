
package dp;

import java.awt.geom.*;
import java.util.*;


public class WeightedMaximumCardinalityMatchingIterative implements MwpmInterface {


  private final int n;
  private double[][] cost;


  private final int END_STATE;
  private boolean solved;


  private double minWeightCost;
  private int[] matching;


  public WeightedMaximumCardinalityMatchingIterative(double[][] cost) {
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
    solve();
    return minWeightCost;
  }

  
  public int[] getMatching() {
    solve();
    return matching;
  }

  private void solve() {
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
    test();
  }

  private static void test() {

    double[][] costMatrix = {
      {0, 9, 9, 9, 9, 1},
      {9, 0, 1, 9, 9, 9},
      {9, 1, 0, 9, 9, 9},
      {9, 9, 9, 0, 1, 9},
      {9, 9, 9, 1, 0, 9},
      {1, 9, 9, 9, 9, 0},
    };

    WeightedMaximumCardinalityMatchingIterative mwpm =
        new WeightedMaximumCardinalityMatchingIterative(costMatrix);


    double cost = mwpm.getMinWeightCost();
    if (cost != 3.0) {
      System.out.println("error cost not 3");
    } else {
      System.out.printf("Found MWPM of: %.3f\n", cost);
    }


    int[] matching = mwpm.getMatching();
    for (int i = 0; i < matching.length / 2; i++) {
      int node1 = matching[2 * i];
      int node2 = matching[2 * i + 1];
      System.out.printf("Matched node %d with node %d\n", node1, node2);
    }






  }
}
