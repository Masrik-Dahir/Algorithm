
package dp;

public class KnapsackUnbounded {

  
  public static int unboundedKnapsack(int maxWeight, int[] W, int[] V) {

    if (W == null || V == null || W.length != V.length || maxWeight < 0)
      throw new IllegalArgumentException("Invalid input");

    final int N = W.length;



    int[][] DP = new int[N + 1][maxWeight + 1];


    for (int i = 1; i <= N; i++) {


      int w = W[i - 1], v = V[i - 1];


      for (int sz = 1; sz <= maxWeight; sz++) {


        if (sz >= w) DP[i][sz] = DP[i][sz - w] + v;


        if (DP[i - 1][sz] > DP[i][sz]) DP[i][sz] = DP[i - 1][sz];
      }
    }


    return DP[N][maxWeight];
  }

  public static int unboundedKnapsackSpaceEfficient(int maxWeight, int[] W, int[] V) {

    if (W == null || V == null || W.length != V.length)
      throw new IllegalArgumentException("Invalid input");

    final int N = W.length;



    int[] DP = new int[maxWeight + 1];


    for (int sz = 1; sz <= maxWeight; sz++) {


      for (int i = 0; i < N; i++) {




        if (sz - W[i] >= 0 && DP[sz - W[i]] + V[i] > DP[sz]) DP[sz] = DP[sz - W[i]] + V[i];
      }
    }


    return DP[maxWeight];
  }

  public static void main(String[] args) {

    int[] W = {3, 6, 2};
    int[] V = {5, 20, 3};
    int knapsackValue = unboundedKnapsackSpaceEfficient(10, W, V);
    System.out.println("Maximum knapsack value: " + knapsackValue);
  }
}
