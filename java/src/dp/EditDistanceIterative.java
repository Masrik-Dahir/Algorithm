
package dp;

public class EditDistanceIterative {



  public static int editDistance(
      String a, String b, int insertionCost, int deletionCost, int substitutionCost) {

    final int AL = a.length(), BL = b.length();
    int[][] dp = new int[AL + 1][BL + 1];

    for (int i = 0; i <= AL; i++) {
      for (int j = (i == 0 ? 1 : 0); j <= BL; j++) {

        int min = Integer.MAX_VALUE;


        if (i > 0 && j > 0)
          min = dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : substitutionCost);


        if (i > 0) min = Math.min(min, dp[i - 1][j] + deletionCost);


        if (j > 0) min = Math.min(min, dp[i][j - 1] + insertionCost);

        dp[i][j] = min;
      }
    }

    return dp[AL][BL];
  }

  public static void main(String[] args) {

    String a = "abcdefg";
    String b = "abcdefg";


    System.out.println(EditDistanceIterative.editDistance(a, b, 10, 10, 10));

    a = "aaa";
    b = "aaabbb";


    System.out.println(EditDistanceIterative.editDistance(a, b, 10, 2, 3));

    a = "1023";
    b = "10101010";


    System.out.println(EditDistanceIterative.editDistance(a, b, 5, 7, 2));

    a = "923456789";
    b = "12345";



    System.out.println(EditDistanceIterative.editDistance(a, b, 2, 4, 1));

    a = "aaaaa";
    b = "aabaa";

    System.out.println(EditDistanceIterative.editDistance(a, b, 2, 3, 10));
  }
}
