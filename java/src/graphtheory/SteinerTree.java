
package graphtheory;

import java.util.*;

public class SteinerTree {


  public static double minLengthSteinerTree(double[][] distances, int[] subsetToConnect) {

    int v = distances.length;
    int t = subsetToConnect.length;


    if (t <= 1) {
      return 0;
    }


    floydWarshall(distances);



    double[][] dp = new double[1 << t][v];
    for (int i = 0; i < dp.length; i++) {
      Arrays.fill(dp[i], Double.POSITIVE_INFINITY);
    }



    for (int mask = 0; mask < t; mask++) {
      for (int j = 0; j < v; j++) {
        dp[1 << mask][j] = distances[subsetToConnect[mask]][j];
      }
    }


    for (int mask = 1; mask < 1 << t; mask++) {


      for (int j = 0; j < v; j++) {


        for (int subMask = (mask - 1) & mask; subMask > 0; subMask = (subMask - 1) & mask) {



          dp[mask][j] = Math.min(dp[mask][j], dp[subMask][j] + dp[mask ^ subMask][j]);
        }
      }


      for (int j = 0; j < v; j++) {
        for (int k = 0; k < v; k++) {
          dp[mask][j] = Math.min(dp[mask][j], dp[mask][k] + distances[k][j]);
        }
      }
    }



    return dp[(1 << t) - 1][subsetToConnect[0]];
  }


  public static void floydWarshall(double[][] distance) {

    int n = distance.length;


    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (distance[i][k] + distance[k][j] < distance[i][j])
            distance[i][j] = distance[i][k] + distance[k][j];



    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (distance[i][k] + distance[k][j] < distance[i][j])
            distance[i][j] = Double.NEGATIVE_INFINITY;
  }


  public static void main(String[] args) {

    final double INF = Double.POSITIVE_INFINITY;


    double[][] matrix1 =
        new double[][] {
          {0, 3, 4, INF, INF},
          {3, 0, INF, 5, INF},
          {4, INF, 0, INF, 6},
          {INF, 5, INF, 0, INF},
          {INF, INF, 6, INF, 0}
        };

    System.out.println(minLengthSteinerTree(matrix1, new int[] {}));
    System.out.println(minLengthSteinerTree(matrix1, new int[] {2}));
    System.out.println(minLengthSteinerTree(matrix1, new int[] {2, 4}));
    System.out.println(minLengthSteinerTree(matrix1, new int[] {0, 3}));
    System.out.println(minLengthSteinerTree(matrix1, new int[] {1, 2, 4}));
    System.out.println(minLengthSteinerTree(matrix1, new int[] {4, 1, 2}));
    System.out.println(minLengthSteinerTree(matrix1, new int[] {3, 0, 4}));
    System.out.println(
        minLengthSteinerTree(matrix1, new int[] {0, 1, 2, 3, 4}));


    double[][] matrix2 =
        new double[][] {
          {0, 3, 5, INF, INF, INF},
          {3, 0, INF, 4, INF, INF},
          {5, INF, 0, INF, 7, 8},
          {INF, 4, INF, 0, INF, 1},
          {INF, INF, 7, INF, 0, 2},
          {INF, INF, 8, 1, 2, 0}
        };

    System.out.println(minLengthSteinerTree(matrix2, new int[] {}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {4}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {0, 5}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {5, 0}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {4, 0}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {2, 4, 5}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {3, 1, 0}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {3, 0}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {3, 0, 5}));
    System.out.println(minLengthSteinerTree(matrix2, new int[] {0, 4, 5}));
  }
}
