package dp.examples.narrowartgallery;


import java.util.Scanner;

public class NarrowArtGalleryRecursive {


  static final int INF = 1000000;

  static int[][] gallery;
  static Integer[][][] dp;

  static final int LEFT = 0;
  static final int RIGHT = 1;

  static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values) if (v < m) m = v;
    return m;
  }

  public static int f(int k, int r) {


    return min(f(k, r, LEFT), f(k, r, RIGHT));
  }







  public static int f(int k, int r, int c) {

    if (k == 0) {
      return 0;
    }
    if (r < 0) {
      return INF;
    }

    if (dp[k][r][c] != null) {
      return dp[k][r][c];
    }

    int roomValue = gallery[r][c];
    return dp[k][r][c] =
        min(


            f(k - 1, r - 1, c) + roomValue,


            f(k, r - 1));
  }

  static void mainProgram() {
    Scanner sc = new Scanner(System.in);
    while (true) {
      int N = sc.nextInt();
      int K = sc.nextInt();

      if (N == 0 && K == 0) break;

      gallery = new int[N][2];
      dp = new Integer[K + 1][N][2];

      int sum = 0;
      for (int i = 0; i < N; i++) {



        int index = N - i - 1;
        gallery[index][LEFT] = sc.nextInt();
        gallery[index][RIGHT] = sc.nextInt();
        sum += gallery[index][LEFT] + gallery[index][RIGHT];
      }

      System.out.printf("%d\n", sum - f(K, N - 1));
    }
  }

  public static void main(String[] Fiset) {
    mainProgram();

  }

  static void test2() {
    int N = 5;
    int K = 4;
    dp = new Integer[K + 1][N][2];








    gallery =
        new int[][] {
          {8, 10},
          {4, 3},
          {0, 1},
          {5, 9},
          {3, 2},
        };
    System.out.println(f(4, 3, LEFT));
    System.out.println(f(4, 3, RIGHT));
    System.out.println(f(3, 3, LEFT));
    System.out.println(f(3, 3, RIGHT));
  }

  static void test1() {
    int N = 6;
    int K = 4;
    dp = new Integer[K + 1][N][2];









    gallery =
        new int[][] {
          {1, 0},
          {3, 3},
          {1, 3},
          {1, 2},
          {2, 1},
          {3, 1},
        };
    f(N - 1, K);

    ok(f(0, 4, LEFT), INF);
    ok(f(0, 4, RIGHT), INF);
    ok(f(1, 4, LEFT), INF);
    ok(f(1, 4, RIGHT), INF);
    ok(f(2, 4, LEFT), INF);
    ok(f(2, 4, RIGHT), INF);
    ok(f(3, 4, LEFT), 6);
    ok(f(3, 4, RIGHT), 8);
    ok(f(4, 4, LEFT), 4);
    ok(f(4, 4, RIGHT), 6);
    ok(f(5, 4, LEFT), 4);
    ok(f(5, 4, RIGHT), 3);

    ok(f(0, 3, LEFT), INF);
    ok(f(0, 3, RIGHT), INF);
    ok(f(1, 3, LEFT), INF);
    ok(f(1, 3, RIGHT), INF);
    ok(f(2, 3, LEFT), 5);
    ok(f(2, 3, RIGHT), 6);
    ok(f(3, 3, LEFT), 2);
    ok(f(3, 3, RIGHT), 5);
    ok(f(4, 3, LEFT), 2);
    ok(f(4, 3, RIGHT), 2);
    ok(f(5, 3, LEFT), 2);
    ok(f(5, 3, RIGHT), 2);

    ok(f(0, 2, LEFT), INF);
    ok(f(0, 2, RIGHT), INF);
    ok(f(1, 2, LEFT), 4);
    ok(f(1, 2, RIGHT), 3);
    ok(f(2, 2, LEFT), 1);
    ok(f(2, 2, RIGHT), 3);
    ok(f(3, 2, LEFT), 1);
    ok(f(3, 2, RIGHT), 1);
    ok(f(4, 2, LEFT), 1);
    ok(f(4, 2, RIGHT), 1);
    ok(f(5, 2, LEFT), 1);
    ok(f(5, 2, RIGHT), 1);

    ok(f(0, 1, LEFT), 1);
    ok(f(0, 1, RIGHT), 0);
    ok(f(1, 1, LEFT), 0);
    ok(f(1, 1, RIGHT), 0);
    ok(f(2, 1, LEFT), 0);
    ok(f(2, 1, RIGHT), 0);
    ok(f(3, 1, LEFT), 0);
    ok(f(3, 1, RIGHT), 0);
    ok(f(4, 1, LEFT), 0);
    ok(f(4, 1, RIGHT), 0);
    ok(f(5, 1, LEFT), 0);
    ok(f(5, 1, RIGHT), 0);
  }

  static void ok(int a, int b) {
    if (a != b) {
      System.out.println("Error: " + a + " != " + b);
    }
  }
}
