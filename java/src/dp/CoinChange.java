
package dp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoinChange {

  public static class Solution {

    Optional<Integer> minCoins = Optional.empty();


    List<Integer> selectedCoins = new ArrayList<Integer>();
  }



  private static final int INF = Integer.MAX_VALUE / 2;

  public static Solution coinChange(int[] coins, final int n) {
    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (coins.length == 0) throw new IllegalArgumentException("No coin values :/");
    for (int coin : coins) {
      if (coin <= 0) {
        throw new IllegalArgumentException("Coin with value `" + coin + "` is not allowed.");
      }
    }

    final int m = coins.length;

    int[][] dp = new int[m + 1][n + 1];
    java.util.Arrays.fill(dp[0], INF);
    dp[1][0] = 0;


    for (int i = 1; i <= m; i++) {
      int coinValue = coins[i - 1];
      for (int j = 1; j <= n; j++) {


        dp[i][j] = dp[i - 1][j];


        if (j - coinValue >= 0 && dp[i][j - coinValue] + 1 < dp[i][j]) {
          dp[i][j] = dp[i][j - coinValue] + 1;
        }
      }
    }



    Solution solution = new Solution();

    if (dp[m][n] != INF) {
      solution.minCoins = Optional.of(dp[m][n]);
    } else {
      return solution;
    }

    for (int change = n, coinIndex = m; coinIndex > 0; ) {
      int coinValue = coins[coinIndex - 1];
      boolean canSelectCoin = change - coinValue >= 0;
      if (canSelectCoin && dp[coinIndex][change - coinValue] < dp[coinIndex][change]) {
        solution.selectedCoins.add(coinValue);
        change -= coinValue;
      } else {
        coinIndex--;
      }
    }

    return solution;
  }

  public static Solution coinChangeSpaceEfficient(int[] coins, int n) {
    if (coins == null) throw new IllegalArgumentException("Coins array is null");


    int[] dp = new int[n + 1];
    java.util.Arrays.fill(dp, INF);
    dp[0] = 0;

    for (int i = 1; i <= n; i++) {
      for (int coin : coins) {
        if (i - coin < 0) {
          continue;
        }
        if (dp[i - coin] + 1 < dp[i]) {
          dp[i] = dp[i - coin] + 1;
        }
      }
    }

    Solution solution = new Solution();
    if (dp[n] != INF) {
      solution.minCoins = Optional.of(dp[n]);
    } else {
      return solution;
    }

    for (int i = n; i > 0; ) {
      int selectedCoinValue = INF;
      int cellWithFewestCoins = dp[i];
      for (int coin : coins) {
        if (i - coin < 0) {
          continue;
        }
        if (dp[i - coin] < cellWithFewestCoins) {
          cellWithFewestCoins = dp[i - coin];
          selectedCoinValue = coin;
        }
      }
      solution.selectedCoins.add(selectedCoinValue);
      i -= selectedCoinValue;
    }


    return solution;
  }




  public static int coinChangeRecursive(int[] coins, int n) {
    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (n < 0) return -1;

    int[] dp = new int[n + 1];
    return coinChangeRecursive(n, coins, dp);
  }


  private static int coinChangeRecursive(int n, int[] coins, int[] dp) {
    if (n < 0) return -1;
    if (n == 0) return 0;
    if (dp[n] != 0) return dp[n];

    int minCoins = INF;
    for (int coinValue : coins) {
      int value = coinChangeRecursive(n - coinValue, coins, dp);
      if (value != -1 && value < minCoins) minCoins = value + 1;
    }



    return dp[n] = (minCoins == INF) ? -1 : minCoins;
  }


  private static void p(int[][] dp) {
    for (int[] r : dp) {
      for (int v : r) {
        System.out.printf("%4d, ", v == INF ? -1 : v);
      }
      System.out.println();
    }
  }

  private static void p(int[] dp) {
    for (int v : dp) {
      System.out.printf("%4d, ", v == INF ? -1 : v);
    }
    System.out.println();
  }

  public static void main(String[] args) {



    example4();
  }

  private static void example4() {
    int n = 11;
    int[] coins = {2, 4, 1};

    System.out.println(coinChangeSpaceEfficient(coins, n));


  }

  private static void example1() {
    int[] coins = {2, 6, 1};
    System.out.println(coinChange(coins, 17).minCoins);
    System.out.println(coinChange(coins, 17).selectedCoins);
    System.out.println(coinChangeSpaceEfficient(coins, 17));
    System.out.println(coinChangeRecursive(coins, 17));
  }

  private static void example2() {
    int[] coins = {2, 3, 5};
    System.out.println(coinChange(coins, 12).minCoins);
    System.out.println(coinChange(coins, 12).selectedCoins);
    System.out.println(coinChangeSpaceEfficient(coins, 12));
    System.out.println(coinChangeRecursive(coins, 12));
  }

  private static void example3() {
    int[] coins = {3, 4, 7};
    System.out.println(coinChange(coins, 17).minCoins);
    System.out.println(coinChange(coins, 17).selectedCoins);
    System.out.println(coinChangeSpaceEfficient(coins, 17));
    System.out.println(coinChangeRecursive(coins, 17));
  }
}
