package dp.examples.editdistance;

import java.io.*;
import java.util.*;

public class EditDistance {

  final char[] a, b;
  final int insertionCost, deletionCost, substitutionCost;

  public EditDistance(
      String a, String b, int insertionCost, int deletionCost, int substitutionCost) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Input string must not be null");
    }
    this.a = a.toCharArray();
    this.b = b.toCharArray();
    this.insertionCost = insertionCost;
    this.deletionCost = deletionCost;
    this.substitutionCost = substitutionCost;
  }

  public static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values) {
      if (v < m) {
        m = v;
      }
    }
    return m;
  }




  public int editDistance() {
    Integer[][] dp = new Integer[a.length + 1][b.length + 1];
    return f(dp, 0, 0);
  }

  private int f(Integer[][] dp, int i, int j) {
    if (i == a.length && j == b.length) {
      return 0;
    }
    if (i == a.length) {
      return (b.length - j) * insertionCost;
    }
    if (j == b.length) {
      return (a.length - i) * deletionCost;
    }
    if (dp[i][j] != null) {
      return dp[i][j];
    }
    int substitute = f(dp, i + 1, j + 1) + (a[i] == b[j] ? 0 : substitutionCost);
    int delete = f(dp, i + 1, j) + deletionCost;
    int insert = f(dp, i, j + 1) + insertionCost;
    return dp[i][j] = min(substitute, delete, insert);
  }



  public static int micahEditDistance(
      String a, String b, int insertionCost, int deletionCost, int substitutionCost) {

    final int AL = a.length(), BL = b.length();
    int[][] arr = new int[AL + 1][BL + 1];

    for (int i = 0; i <= AL; i++) {
      for (int j = (i == 0 ? 1 : 0); j <= BL; j++) {

        int min = Integer.MAX_VALUE;


        if (i > 0 && j > 0)
          min = arr[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : substitutionCost);


        if (i > 0) min = Math.min(min, arr[i - 1][j] + deletionCost);


        if (j > 0) min = Math.min(min, arr[i][j - 1] + insertionCost);

        arr[i][j] = min;
      }
    }

    return arr[AL][BL];
  }

  public static void main(String[] args) {
    String a = "923456789";
    String b = "12345";
    EditDistance solver = new EditDistance(a, b, 100, 4, 2);
    System.out.println(solver.editDistance());
    System.out.println(micahEditDistance(a, b, 100, 4, 2));

    a = "12345";
    b = "923456789";
    solver = new EditDistance(a, b, 100, 4, 2);
    System.out.println(solver.editDistance());
    System.out.println(micahEditDistance(a, b, 100, 4, 2));

    a = "aaa";
    b = "aaabbb";
    solver = new EditDistance(a, b, 10, 2, 3);
    System.out.println(solver.editDistance());
    System.out.println(micahEditDistance(a, b, 10, 2, 3));

    a = "1023";
    b = "10101010";
    solver = new EditDistance(a, b, 5, 7, 2);
    System.out.println(solver.editDistance());
    System.out.println(micahEditDistance(a, b, 5, 7, 2));

    a = "923456789";
    b = "12345";
    EditDistance solver2 = new EditDistance(a, b, 100, 4, 2);
    System.out.println(solver2.editDistance());
    System.out.println(micahEditDistance(a, b, 100, 4, 2));

    a = "aaaaa";
    b = "aabaa";
    solver = new EditDistance(a, b, 2, 3, 10);
    System.out.println(solver.editDistance());
    System.out.println(micahEditDistance(a, b, 2, 3, 10));
  }
}
