
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class Line {



  private double a, b, c;


  private static final double EPS = 0.0000001;


  public Line(Point2D p1, Point2D p2) {
    this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
  }


  public Line(double x1, double y1, double x2, double y2) {
    a = y1 - y2;
    b = x2 - x1;
    c = x2 * y1 - x1 * y2;
    normalise();
  }


  public static Line slopePointToLine(double slope, Point2D pt) {
    Point2D p2 = null;
    if (slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY) {
      p2 = new Point2D.Double(pt.getX(), pt.getY() + 1);
    } else {
      p2 = new Point2D.Double(pt.getX() + 1, pt.getY() + slope);
    }
    return new Line(pt, p2);
  }


  public void normalise() {
    if (abs(b) < EPS) {
      c /= a;
      a = 1;
      b = 0;
    } else {
      a = (abs(a) < EPS) ? 0 : a / b;
      c /= b;
      b = 1;
    }
  }



  public static Line getPerpBisector(double x1, double y1, double x2, double y2) {


    Point2D middle = new Point2D.Double((x1 + x2) / 2.0, (y1 + y2) / 2.0);



    double perpSlope = (x1 - x2) / (y2 - y1);

    if (abs(y2 - y1) < EPS) perpSlope = Double.POSITIVE_INFINITY;
    else if (abs(x1 - x2) < EPS) perpSlope = 0;

    return slopePointToLine(perpSlope, middle);
  }



  public static Point2D intersection(Line l1, Line l2) {

    l1.normalise();
    l2.normalise();


    if (abs(l1.a - l2.a) < EPS && abs(l1.b - l2.b) < EPS) return null;

    double x = Double.NaN, y = Double.NaN;
    if (abs(l1.b) < EPS) {
      x = l1.c / l1.a;
      y = (l2.c - l2.a * x) / l2.b;
    } else if (abs(l2.b) < EPS) {
      x = l2.c / l2.a;
      y = (l1.c - l1.a * x) / l1.b;
    } else if (abs(l1.a) < EPS) {
      y = l1.c / l1.b;
      x = (l2.c - l2.b * y) / l2.a;
    } else if (abs(l2.a) < EPS) {
      y = l2.c / l2.b;
      x = (l1.c - l1.b * y) / l1.a;
    } else {
      x = (l1.c - l2.c) / (l1.a - l2.a);
      y = (l1.c - l1.a * x) / l1.b;
    }

    return new Point2D.Double(x, y);
  }


  @Override
  public String toString() {
    return a + "x + " + b + "y = " + c;
  }
}
