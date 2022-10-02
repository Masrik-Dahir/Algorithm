
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class PointCircleTangent {


  public static final double EPS = 0.0000001;




  private static double arcsinSafe(double x) {
    if (x <= -1.0) return -PI / 2.0;
    if (x >= +1.0) return +PI / 2.0;
    return asin(x);
  }



  public static Point2D[] pointCircleTangentPoints(Point2D center, double radius, Point2D pt) {

    double px = pt.getX(), py = pt.getY();
    double cx = center.getX(), cy = center.getY();


    double dx = cx - px;
    double dy = cy - py;
    double dist = sqrt(dx * dx + dy * dy);


    if (dist < radius) return new Point2D[] {};

    double angle, angle1, angle2;

    angle1 = arcsinSafe(radius / dist);
    angle2 = atan2(dy, dx);

    angle = angle2 - angle1;
    Point2D p1 = new Point2D.Double(cx + radius * sin(angle), cy + radius * -cos(angle));

    angle = angle1 + angle2;
    Point2D p2 = new Point2D.Double(cx + radius * -sin(angle), cy + radius * cos(angle));



    if (p1.distance(p2) < EPS) return new Point2D[] {pt};
    return new Point2D[] {p1, p2};
  }


  public static void main(String[] args) {







    double radius = 2.0;
    Point2D circleCenter = new Point2D.Double(5, 0);


    Point2D origin = new Point2D.Double(0, 0);
    Point2D[] points = pointCircleTangentPoints(circleCenter, radius, origin);
    if (points.length == 2) {
      Point2D pt1 = points[0];
      Point2D pt2 = points[1];
      System.out.println("Points found: " + pt1 + " " + pt2);
    }
  }
}
