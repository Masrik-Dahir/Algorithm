
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class ConvexPolygonContainsPoint {


  private static final double EPS = 1e-9;








  public static boolean containsPoint(Point2D[] hull, Point2D p) {

    Point2D p0 = hull[0];
    int lo = 1, hi = hull.length - 2, mid = (lo + hi) >>> 1;

    while (lo != hi) {
      Point2D p1 = hull[mid];
      double sign = collinear(p0, p1, p);
      if (sign >= 0) lo = mid;
      else if (sign < 0) hi = mid;
      mid = (lo + hi) >>> 1;
      if (hi - lo == 1) {
        if (collinear(p0, hull[hi], p) >= 0) lo = hi;
        else hi = lo;
      }
    }

    Point2D p1 = hull[lo], p2 = hull[lo + 1];
    double boundSign = collinear(p1, p2, p);
    double segSign1 = collinear(p0, p1, p);
    double segSign2 = collinear(p0, p2, p);

    return (boundSign >= 0 && segSign1 >= 0 && segSign2 <= 0);
  }






  private static int collinear(Point2D a, Point2D b, Point2D c) {
    double ax = a.getX(), ay = a.getY();
    double bx = b.getX(), by = b.getY();
    double cx = c.getX(), cy = c.getY();
    double area = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax);
    if (abs(area) < EPS) return 0;
    return (int) signum(area);
  }
}
