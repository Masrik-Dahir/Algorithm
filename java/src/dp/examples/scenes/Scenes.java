package dp.examples.scenes;


import java.io.*;
import java.util.*;

public class Scenes {

  static Long[][] dp;
  static int N, W, H;
  static long MOD = 1_000_000_007;

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    String[] ln = br.readLine().split(" ");
    N = Integer.parseInt(ln[0]);
    W = Integer.parseInt(ln[1]);
    H = Integer.parseInt(ln[2]);

    solution1();
  }


  static void solution1() {


    int ribbonSquares = Math.min(W * H, N);
    int plains = (ribbonSquares / W) + 1;

    dp = new Long[W + 1][N + 1];
    long ans = ((f(1, N) - plains) + MOD) % MOD;
    System.out.println(ans);
  }

  static long f(int w, int ribbon) {

    if (ribbon < 0) {
      return 0;
    }


    if (w > W) {
      return 1;
    }
    if (dp[w][ribbon] != null) {
      return dp[w][ribbon];
    }
    long scenes = 0L;

    for (int len = 0; len <= H; len++) {
      scenes = (scenes + f(w + 1, ribbon - len));
    }



    return dp[w][ribbon] = scenes % MOD;
  }
}
