
package datastructures.sparsetable;

import java.util.function.BinaryOperator;

public class SparseTable {


  private int n;


  private int P;


  private int[] log2;


  private long[][] dp;


  private int[][] it;


  public enum Operation {
    MIN,
    MAX,
    SUM,
    MULT,
    GCD
  };

  private Operation op;


  private BinaryOperator<Long> sumFn = (a, b) -> a + b;
  private BinaryOperator<Long> minFn = (a, b) -> Math.min(a, b);
  private BinaryOperator<Long> maxFn = (a, b) -> Math.max(a, b);
  private BinaryOperator<Long> multFn = (a, b) -> a * b;
  private BinaryOperator<Long> gcdFn =
      (a, b) -> {
        long gcd = a;
        while (b != 0) {
          gcd = b;
          b = a % b;
          a = gcd;
        }
        return Math.abs(gcd);
      };

  public SparseTable(long[] values, Operation op) {

    this.op = op;
    init(values);
  }

  private void init(long[] v) {
    n = v.length;



    P = (int) (Math.log(n) / Math.log(2));
    dp = new long[P + 1][n];
    it = new int[P + 1][n];

    for (int i = 0; i < n; i++) {
      dp[0][i] = v[i];
      it[0][i] = i;
    }

    log2 = new int[n + 1];
    for (int i = 2; i <= n; i++) {
      log2[i] = log2[i / 2] + 1;
    }


    for (int i = 1; i <= P; i++) {
      for (int j = 0; j + (1 << i) <= n; j++) {
        long leftInterval = dp[i - 1][j];
        long rightInterval = dp[i - 1][j + (1 << (i - 1))];
        if (op == Operation.MIN) {
          dp[i][j] = minFn.apply(leftInterval, rightInterval);

          if (leftInterval <= rightInterval) {
            it[i][j] = it[i - 1][j];
          } else {
            it[i][j] = it[i - 1][j + (1 << (i - 1))];
          }
        } else if (op == Operation.MAX) {
          dp[i][j] = maxFn.apply(leftInterval, rightInterval);

          if (leftInterval >= rightInterval) {
            it[i][j] = it[i - 1][j];
          } else {
            it[i][j] = it[i - 1][j + (1 << (i - 1))];
          }
        } else if (op == Operation.SUM) {
          dp[i][j] = sumFn.apply(leftInterval, rightInterval);
        } else if (op == Operation.MULT) {
          dp[i][j] = multFn.apply(leftInterval, rightInterval);
        } else if (op == Operation.GCD) {
          dp[i][j] = gcdFn.apply(leftInterval, rightInterval);
        }
      }
    }


  }


  private void printTable() {
    for (long[] r : dp) {
      for (int i = 0; i < r.length; i++) {
        System.out.printf("%02d, ", r[i]);
      }
      System.out.println();
    }
  }


  public long query(int l, int r) {

    if (op == Operation.MIN) {
      return query(l, r, minFn);
    } else if (op == Operation.MAX) {
      return query(l, r, maxFn);
    } else if (op == Operation.GCD) {
      return query(l, r, gcdFn);
    }


    if (op == Operation.SUM) {
      return sumQuery(l, r);
    } else {
      return multQuery(l, r);
    }
  }

  public int queryIndex(int l, int r) {
    if (op == Operation.MIN) {
      return minQueryIndex(l, r);
    } else if (op == Operation.MAX) {
      return maxQueryIndex(l, r);
    }
    throw new UnsupportedOperationException(
        "Operation type: " + op + " doesn't support index queries :/");
  }

  private int minQueryIndex(int l, int r) {
    int len = r - l + 1;
    int p = log2[len];
    long leftInterval = dp[p][l];
    long rightInterval = dp[p][r - (1 << p) + 1];
    if (leftInterval <= rightInterval) {
      return it[p][l];
    } else {
      return it[p][r - (1 << p) + 1];
    }
  }

  private int maxQueryIndex(int l, int r) {
    int len = r - l + 1;
    int p = log2[len];
    long leftInterval = dp[p][l];
    long rightInterval = dp[p][r - (1 << p) + 1];
    if (leftInterval >= rightInterval) {
      return it[p][l];
    } else {
      return it[p][r - (1 << p) + 1];
    }
  }










  private long sumQuery(int l, int r) {
    long sum = 0;
    for (int p = log2[r - l + 1]; l <= r; p = log2[r - l + 1]) {
      sum += dp[p][l];
      l += (1 << p);
    }
    return sum;
  }

  private long multQuery(int l, int r) {
    long result = 1;
    for (int p = log2[r - l + 1]; l <= r; p = log2[r - l + 1]) {
      result *= dp[p][l];
      l += (1 << p);
    }
    return result;
  }








  private long query(int l, int r, BinaryOperator<Long> fn) {
    int len = r - l + 1;
    int p = log2[len];
    return fn.apply(dp[p][l], dp[p][r - (1 << p) + 1]);
  }

  

  public static void main(String[] args) {


    example3();
  }

  private static void example1() {
    long[] values = {1, 2, -3, 2, 4, -1, 5};


    SparseTable sparseTable = new SparseTable(values, SparseTable.Operation.MULT);

    System.out.println(sparseTable.query(2, 3));
  }

  private static void exampleFromSlides() {
    long[] values = {4, 2, 3, 7, 1, 5, 3, 3, 9, 6, 7, -1, 4};


    SparseTable sparseTable = new SparseTable(values, SparseTable.Operation.MIN);

    System.out.printf("Min value between [2, 7] = %d\n", sparseTable.query(2, 7));
  }

  private static void example3() {
    long[] values = {4, 4, 4, 4, 4, 4};

    SparseTable sparseTable = new SparseTable(values, SparseTable.Operation.SUM);

    System.out.printf("%d\n", sparseTable.query(0, values.length - 1));
  }
}
