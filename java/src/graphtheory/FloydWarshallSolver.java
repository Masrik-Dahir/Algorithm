
package graphtheory;




import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

import java.util.ArrayList;
import java.util.List;

public class FloydWarshallSolver {

  private int n;
  private boolean solved;
  private double[][] dp;
  private Integer[][] next;

  private static final int REACHES_NEGATIVE_CYCLE = -1;


  public FloydWarshallSolver(double[][] matrix) {
    n = matrix.length;
    dp = new double[n][n];
    next = new Integer[n][n];


    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] != POSITIVE_INFINITY) next[i][j] = j;
        dp[i][j] = matrix[i][j];
      }
    }
  }


  public double[][] getApspMatrix() {
    solve();
    return dp;
  }


  public void solve() {
    if (solved) return;


    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (dp[i][k] + dp[k][j] < dp[i][j]) {
            dp[i][j] = dp[i][k] + dp[k][j];
            next[i][j] = next[i][k];
          }
        }
      }
    }



    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dp[i][k] != POSITIVE_INFINITY && dp[k][j] != POSITIVE_INFINITY && dp[k][k] < 0) {
            dp[i][j] = NEGATIVE_INFINITY;
            next[i][j] = REACHES_NEGATIVE_CYCLE;
          }

    solved = true;
  }


  public List<Integer> reconstructShortestPath(int start, int end) {
    solve();
    List<Integer> path = new ArrayList<>();
    if (dp[start][end] == POSITIVE_INFINITY) return path;
    int at = start;
    for (; at != end; at = next[at][end]) {

      if (at == REACHES_NEGATIVE_CYCLE) return null;
      path.add(at);
    }

    if (next[at][end] == REACHES_NEGATIVE_CYCLE) return null;
    path.add(end);
    return path;
  }





  public static double[][] createGraph(int n) {
    double[][] matrix = new double[n][n];
    for (int i = 0; i < n; i++) {
      java.util.Arrays.fill(matrix[i], POSITIVE_INFINITY);
      matrix[i][i] = 0;
    }
    return matrix;
  }

  public static void main(String[] args) {

    int n = 7;
    double[][] m = createGraph(n);


    m[0][1] = 2;
    m[0][2] = 5;
    m[0][6] = 10;
    m[1][2] = 2;
    m[1][4] = 11;
    m[2][6] = 2;
    m[6][5] = 11;
    m[4][5] = 1;
    m[5][4] = -2;

    FloydWarshallSolver solver = new FloydWarshallSolver(m);
    double[][] dist = solver.getApspMatrix();

    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        System.out.printf("This shortest path from node %d to node %d is %.3f\n", i, j, dist[i][j]);















    System.out.println();


    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        List<Integer> path = solver.reconstructShortestPath(i, j);
        String str;
        if (path == null) {
          str = "HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)";
        } else if (path.size() == 0) {
          str = String.format("DOES NOT EXIST (node %d doesn't reach node %d)", i, j);
        } else {
          str =
              String.join(
                  " -> ",
                  path.stream()
                      .map(Object::toString)
                      .collect(java.util.stream.Collectors.toList()));
          str = "is: [" + str + "]";
        }

        System.out.printf("The shortest path from node %d to node %d %s\n", i, j, str);
      }
    }


















  }
}
