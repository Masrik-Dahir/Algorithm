
package geometry;

import static java.lang.Math.abs;

import java.awt.geom.*;
import java.util.*;

public class ConvexHullMonotoneChainsAlgorithm {


  private static final double EPS = 1e-5;


  private static class PointComparator implements Comparator<Point2D> {
    public int compare(Point2D p1, Point2D p2) {
      if (abs(p1.getX() - p2.getX()) < EPS) {
        if (abs(p1.getY() - p2.getY()) < EPS) return 0;
        else if (p1.getY() > p2.getY()) return 1;
      } else if (p1.getX() > p2.getX()) return 1;
      return -1;
    }
  }



  public static Point2D[] convexHull(Point2D[] pts) {

    int n = pts.length, k = 0;
    if (n <= 1) return pts;

    Point2D[] hull = new Point2D[2 * n];
    Arrays.sort(pts, new PointComparator());


    for (int i = 0; i < n; i++) {
      while (k >= 2 && orientation(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
      hull[k++] = pts[i];
    }

    int lastUpperChainIndex = k;


    for (int i = n - 2; i >= 0; i--) {
      while (k > lastUpperChainIndex && orientation(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
      hull[k++] = pts[i];
    }


    int index = 1;
    Point2D lastPt = hull[0];
    for (int i = 1; i < k - 1; i++) {
      if (!hull[i].equals(lastPt)) {
        hull[index++] = lastPt = hull[i];
      }
    }

    return Arrays.copyOfRange(hull, 0, index);
  }







  private static int orientation(Point2D a, Point2D b, Point2D c) {
    double value =
        (b.getY() - a.getY()) * (c.getX() - b.getX())
            - (b.getX() - a.getX()) * (c.getY() - b.getY());
    if (abs(value) < EPS) return 0;
    return (value > 0) ? -1 : +1;
  }
}
