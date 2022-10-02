
package dp;

public class LongestPalindromeSubsequence {

  public static void main(String[] args) {
    System.out.println(lps("bbbab"));
    System.out.println(lps("bccd"));
  }


  public static int lps(String s) {
    if (s == null || s.length() == 0) return 0;
    Integer[][] dp = new Integer[s.length()][s.length()];
    return lps(s, dp, 0, s.length() - 1);
  }



  private static int lps(String s, Integer[][] dp, int i, int j) {


    if (j < i) return 0;
    if (i == j) return 1;
    if (dp[i][j] != null) return dp[i][j];

    char c1 = s.charAt(i), c2 = s.charAt(j);


    if (c1 == c2) return dp[i][j] = lps(s, dp, i + 1, j - 1) + 2;


    return dp[i][j] = Math.max(lps(s, dp, i + 1, j), lps(s, dp, i, j - 1));
  }
}
