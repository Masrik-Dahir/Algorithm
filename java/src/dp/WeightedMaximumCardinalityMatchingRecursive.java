
package dp;

import java.awt.geom.*;
import java.util.*;

public class WeightedMaximumCardinalityMatchingRecursive implements MwpmInterface {






  private static class MatchingCost {
    double cost = 0;
    int impossibleEdgeMatches = 0;

    public MatchingCost() {}

    public MatchingCost(double cost, int iem) {
      this.cost = cost;
      this.impossibleEdgeMatches = iem;
    }

    public MatchingCost(MatchingCost mc) {
      this.cost = mc.cost;
      this.impossibleEdgeMatches = mc.impossibleEdgeMatches;
    }

    public static MatchingCost createInfiniteValueMatchingCost() {
      return new MatchingCost(Double.MAX_VALUE, Integer.MAX_VALUE / 2);
    }



    public void updateMatchingCost(Double edgeCost) {
      if (edgeCost == null) {
        impossibleEdgeMatches++;
      } else {
        cost += edgeCost;
      }
    }


    public boolean isBetterMatchingCost(MatchingCost mc) {
      if (impossibleEdgeMatches < mc.impossibleEdgeMatches) {
        return true;
      }
      if (impossibleEdgeMatches == mc.impossibleEdgeMatches) {
        return cost < mc.cost;
      }
      return false;
    }

    @Override
    public String toString() {
      return cost + " " + impossibleEdgeMatches;
    }
  }


  private int n;
  private Double[][] cost;


  private final int FULL_STATE;
  private int artificialNodeId = -1;
  private boolean isOdd;
  private boolean solved;


  private double minWeightCost;
  private int[] matching;



  public WeightedMaximumCardinalityMatchingRecursive(Double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n <= 1) throw new IllegalArgumentException("Invalid matrix size: " + n);
    setCostMatrix(cost);
    FULL_STATE = (1 << n) - 1;
  }




  private void setCostMatrix(Double[][] inputMatrix) {
    isOdd = (n % 2 == 0) ? false : true;
    Double[][] newCostMatrix = null;

    if (isOdd) {
      newCostMatrix = new Double[n + 1][n + 1];
    } else {
      newCostMatrix = new Double[n][n];
    }

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        newCostMatrix[i][j] = inputMatrix[i][j];
      }
    }

    if (isOdd) {
      for (int i = 0; i < n; i++) {
        newCostMatrix[n][i] = null;
        newCostMatrix[i][n] = null;
      }
      newCostMatrix[n][n] = 0.0;
      artificialNodeId = n;
      n++;
    }

    this.cost = newCostMatrix;
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
    MatchingCost[] dp = new MatchingCost[1 << n];
    int[] history = new int[1 << n];

    MatchingCost matchingCost = f(FULL_STATE, dp, history);
    minWeightCost = matchingCost.cost;

    reconstructMatching(history);
    solved = true;
  }

  private MatchingCost f(int state, MatchingCost[] dp, int[] history) {
    if (dp[state] != null) {
      return dp[state];
    }
    if (state == 0) {
      return new MatchingCost();
    }
    int p1, p2;

    for (p1 = 0; p1 < n; p1++) {
      if ((state & (1 << p1)) > 0) {
        break;
      }
    }

    int bestState = -1;
    MatchingCost bestMatchingCost = MatchingCost.createInfiniteValueMatchingCost();

    for (p2 = p1 + 1; p2 < n; p2++) {

      if ((state & (1 << p2)) > 0) {
        int reducedState = state ^ (1 << p1) ^ (1 << p2);

        MatchingCost matchCost = new MatchingCost(f(reducedState, dp, history));
        matchCost.updateMatchingCost(cost[p1][p2]);

        if (matchCost.isBetterMatchingCost(bestMatchingCost)) {
          bestMatchingCost = matchCost;
          bestState = reducedState;
        }
      }
    }

    history[state] = bestState;
    return dp[state] = bestMatchingCost;
  }





  private void reconstructMatching(int[] history) {

    int[] map = new int[n];
    int[] leftNodes = new int[n / 2];

    int matchingSize = 0;


    for (int i = 0, state = FULL_STATE; state != 0; state = history[state]) {

      int pairUsed = state ^ history[state];

      int leftNode = getBitPosition(Integer.lowestOneBit(pairUsed));
      int rightNode = getBitPosition(Integer.highestOneBit(pairUsed));

      leftNodes[i++] = leftNode;
      map[leftNode] = rightNode;

      if (cost[leftNode][rightNode] != null) {
        matchingSize++;
      }
    }


    Arrays.sort(leftNodes);

    matchingSize = matchingSize * 2;
    matching = new int[matchingSize];

    for (int i = 0, j = 0; i < n / 2; i++) {
      int leftNode = leftNodes[i];
      int rightNode = map[leftNodes[i]];

      if (isOdd && (leftNode == artificialNodeId || rightNode == artificialNodeId)) {
        continue;
      }

      if (cost[leftNode][rightNode] != null) {
        matching[2 * j] = leftNode;
        matching[2 * j + 1] = rightNode;
        j++;
      }
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

    Double[][] costMatrix = {
      {0.0, 9.0, 9.0, 9.0, 9.0, 1.0},
      {9.0, 0.0, 1.0, 9.0, 9.0, 9.0},
      {9.0, 1.0, 0.0, 9.0, 9.0, 9.0},
      {9.0, 9.0, 9.0, 0.0, 1.0, 9.0},
      {9.0, 9.0, 9.0, 1.0, 0.0, 9.0},
      {1.0, 9.0, 9.0, 9.0, 9.0, 0.0},
    };

    WeightedMaximumCardinalityMatchingRecursive mwpm =
        new WeightedMaximumCardinalityMatchingRecursive(costMatrix);


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
