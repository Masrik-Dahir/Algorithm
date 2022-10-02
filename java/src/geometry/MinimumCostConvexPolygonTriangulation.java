
package geometry;

import java.awt.geom.Point2D;


public class MinimumCostConvexPolygonTriangulation {


  private static double cost(Point2D i, Point2D j, Point2D k) {
    return i.distance(j) + i.distance(k) + j.distance(k);
  }


  public static double minimumCostTriangulation(Point2D[] polygon) {
    int len = polygon.length;
    if (len < 3) return 0;

    double[][] dp = new double[len][len];
    for (int i = 2; i < len; i++) {
      for (int j = 0; j + i < len; j++) {
        dp[j][j + i] = Integer.MAX_VALUE;
        for (int k = j + 1; k < j + i; k++) {
          dp[j][j + i] =
              Math.min(
                  dp[j][j + i],
                  dp[j][k] + dp[k][j + i] + cost(polygon[j], polygon[j + i], polygon[k]));
        }
      }
    }
    return dp[0][len - 1];
  }
}
