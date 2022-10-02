
package dp;

import java.util.ArrayList;
import java.util.List;

public class Knapsack_01 {

  
  public static int knapsack(int capacity, int[] W, int[] V) {

    if (W == null || V == null || W.length != V.length || capacity < 0)
      throw new IllegalArgumentException("Invalid input");

    final int N = W.length;



    int[][] DP = new int[N + 1][capacity + 1];

    for (int i = 1; i <= N; i++) {


      int w = W[i - 1], v = V[i - 1];

      for (int sz = 1; sz <= capacity; sz++) {


        DP[i][sz] = DP[i - 1][sz];



        if (sz >= w && DP[i - 1][sz - w] + v > DP[i][sz]) DP[i][sz] = DP[i - 1][sz - w] + v;
      }
    }

    int sz = capacity;
    List<Integer> itemsSelected = new ArrayList<>();




    for (int i = N; i > 0; i--) {
      if (DP[i][sz] != DP[i - 1][sz]) {
        int itemIndex = i - 1;
        itemsSelected.add(itemIndex);
        sz -= W[itemIndex];
      }
    }






    return DP[N][capacity];
  }

  public static void main(String[] args) {

    int capacity = 10;
    int[] V = {1, 4, 8, 5};
    int[] W = {3, 3, 5, 6};
    System.out.println(knapsack(capacity, W, V));

    capacity = 7;
    V = new int[] {2, 2, 4, 5, 3};
    W = new int[] {3, 1, 3, 4, 2};
    System.out.println(knapsack(capacity, W, V));
  }
}
