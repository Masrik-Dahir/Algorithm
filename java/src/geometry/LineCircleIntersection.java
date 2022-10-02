
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class LineCircleIntersection {

  private static final double EPS = 1e-9;




  public static Point2D[] lineCircleIntersection(
      double a, double b, double c, double x, double y, double r) {







    double A = a * a + b * b;
    double B = 2 * a * b * y - 2 * a * c - 2 * b * b * x;
    double C = b * b * x * x + b * b * y * y - 2 * b * c * y + c * c - b * b * r * r;

    double D = B * B - 4 * A * C;
    double x1, y1, x2, y2;


    if (abs(b) < EPS) {


      double vx = c / a;


      if (abs(x - vx) > r) return new Point2D[] {};


      if (abs((vx - r) - x) < EPS || abs((vx + r) - x) < EPS)
        return new Point2D[] {new Point2D.Double(vx, y)};

      double dx = abs(vx - x);
      double dy = sqrt(r * r - dx * dx);

      return new Point2D[] {new Point2D.Double(vx, y + dy), new Point2D.Double(vx, y - dy)};


    } else if (abs(D) < EPS) {

      x1 = -B / (2 * A);
      y1 = (c - a * x1) / b;

      return new Point2D[] {new Point2D.Double(x1, y1)};


    } else if (D < 0) {
      return new Point2D[] {};


    } else {

      D = sqrt(D);

      x1 = (-B + D) / (2 * A);
      y1 = (c - a * x1) / b;

      x2 = (-B - D) / (2 * A);
      y2 = (c - a * x2) / b;

      return new Point2D[] {new Point2D.Double(x1, y1), new Point2D.Double(x2, y2)};
    }
  }

  public static void main(String[] args) {


    display(lineCircleIntersection(1, 0, 1, 0, 0, 1));


    display(lineCircleIntersection(0, 1, 1, 0, 0, 1));


    display(lineCircleIntersection(1, 0, -1, 0, 0, 1));
  }

  private static void display(Point2D[] pts) {
    if (pts == null) System.out.println("null");
    else {
      if (pts.length == 0) System.out.println("[]");
      else for (Point2D p : pts) System.out.println(p);
    }
  }
}
