
package dp;

public class LongestCommonSubstring {



  public static String lcs(char[] A, char[] B) {

    if (A == null || B == null) return null;

    final int n = A.length;
    final int m = B.length;

    if (n == 0 || m == 0) return null;

    int[][] dp = new int[n + 1][m + 1];


    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {


        if (A[i - 1] == B[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;



        else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
      }
    }

    int lcsLen = dp[n][m];
    char[] lcs = new char[lcsLen];
    int index = 0;




    int i = n, j = m;
    while (i >= 1 && j >= 1) {

      int v = dp[i][j];


      while (i > 1 && dp[i - 1][j] == v) i--;
      while (j > 1 && dp[i][j - 1] == v) j--;


      if (v > 0) lcs[lcsLen - index++ - 1] = A[i - 1];

      i--;
      j--;
    }

    return new String(lcs, 0, lcsLen);
  }

  public static void main(String[] args) {

    char[] A = {'A', 'X', 'B', 'C', 'Y'};
    char[] B = {'Z', 'A', 'Y', 'W', 'B', 'C'};
    System.out.println(lcs(A, B));

    A = new char[] {'3', '9', '8', '3', '9', '7', '9', '7', '0'};
    B = new char[] {'3', '3', '9', '9', '9', '1', '7', '2', '0', '6'};
    System.out.println(lcs(A, B));
  }
}
