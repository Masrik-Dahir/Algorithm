
package dp;

public class LongestIncreasingSubsequence {

  public static void main(String[] args) {

    System.out.println(lis(new int[] {1, 3, 2, 4, 3}));
    System.out.println(lis(new int[] {2, 7, 4, 3, 8}));
    System.out.println(lis(new int[] {5, 4, 3, 2, 1}));
    System.out.println(lis(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}));
  }


  public static int lis(int[] ar) {

    if (ar == null || ar.length == 0) return 0;
    int n = ar.length, len = 0;



    int[] dp = new int[n];
    java.util.Arrays.fill(dp, 1);




    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (ar[i] < ar[j] && dp[j] < dp[i] + 1) {
          dp[j] = dp[i] + 1;
        }
      }

      if (dp[i] > len) len = dp[i];
    }

    return len;
  }
}
