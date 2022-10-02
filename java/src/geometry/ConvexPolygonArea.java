
package geometry;

import java.awt.geom.Point2D;

public class ConvexPolygonArea {




  public static double convexPolygonArea(Point2D[] points) {

    int N = points.length - 1;

    if (N < 3 || points[0] != points[N])
      throw new IllegalArgumentException(
          "Make sure N >= 3 and that the points array has n+1 points and points[0] = points[N]");


    Point2D fp = points[0];
    double area = 0, fpx = fp.getX(), fpy = fp.getY();

    for (int i = 1; i < N; i++) {

      Point2D pt1 = points[i];
      Point2D pt2 = points[i + 1];




      double a = pt1.getX() - fpx;
      double b = pt2.getX() - fpx;
      double c = pt1.getY() - fpy;
      double d = pt2.getY() - fpy;




      area += a * d - b * c;
    }


    return Math.abs(area) / 2.0;
  }


  public static void main(String[] args) {

    int N = 5;


    Point2D[] points = new Point2D[N + 1];


    points[0] = new Point2D.Double(0, 0);
    points[1] = new Point2D.Double(0, 2);
    points[2] = new Point2D.Double(1, 3);
    points[3] = new Point2D.Double(2, 2);
    points[4] = new Point2D.Double(2, 0);
    points[N] = points[0];

    System.out.println(convexPolygonArea(points));


    points[0] = new Point2D.Double(0, 0);
    points[1] = new Point2D.Double(2, 0);
    points[2] = new Point2D.Double(2, 2);
    points[3] = new Point2D.Double(1, 3);
    points[4] = new Point2D.Double(0, 2);
    points[N] = points[0];

    System.out.println(convexPolygonArea(points));
  }
}
