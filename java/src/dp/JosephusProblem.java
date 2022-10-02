
package dp;

public class JosephusProblem {






  public static int josephus(int n, int k) {
    int[] dp = new int[n];
    for (int i = 1; i < n; i++) dp[i] = (dp[i - 1] + k) % (i + 1);
    return dp[n - 1];
  }

  public static void main(String[] args) {

    int n = 41, k = 2;
    System.out.println(josephus(n, k));

    n = 25;
    k = 18;
    System.out.println(josephus(n, k));

    n = 5;
    k = 2;
    System.out.println(josephus(n, k));
  }
}
