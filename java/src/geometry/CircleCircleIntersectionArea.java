
package geometry;

import static java.lang.Math.*;

import java.awt.geom.*;

public class CircleCircleIntersectionArea {

  private static final double EPS = 1e-6;




  private static double arccosSafe(double x) {
    if (x >= +1.0) return 0;
    if (x <= -1.0) return PI;
    return acos(x);
  }

  public static double circleCircleIntersectionArea(Point2D c1, double r1, Point2D c2, double r2) {

    double r = r1, R = r2;
    Point2D c = c1, C = c2;

    if (r1 > r2) {
      r = r2;
      R = r1;
      c = c2;
      C = c1;
    }

    double dist = c1.distance(c2);
    Point2D[] intersections = circleCircleIntersection(c1, r1, c2, r2);


    if (intersections == null) {
      return PI * r * r;


    } else if (intersections.length == 0) {
      return 0;


    } else if (intersections.length == 1) {


      if (dist < R) {
        return PI * r * r;
      } else {
        return 0;
      }


    } else {


      double d = dist;
      Double part1 = r * r * arccosSafe((d * d + r * r - R * R) / (2 * d * r));
      Double part2 = R * R * arccosSafe((d * d + R * R - r * r) / (2 * d * R));
      Double part3 = 0.5 * sqrt((-d + r + R) * (d + r - R) * (d - r + R) * (d + r + R));

      return part1 + part2 - part3;
    }
  }





  public static Point2D[] circleCircleIntersection(Point2D c1, double r1, Point2D c2, double r2) {


    double r, R;



    Point2D c, C;


    if (r1 < r2) {
      r = r1;
      R = r2;
      c = c1;
      C = c2;
    } else {
      r = r2;
      R = r1;
      c = c2;
      C = c1;
    }

    double dist = c1.distance(c2);


    if (dist < EPS && abs(r - R) < EPS) return null;


    if (r + dist < R) return null;


    if (r + R < dist) return new Point2D[] {};



    double cx = c.getX();
    double Cx = C.getX();
    double cy = c.getY();
    double Cy = C.getY();


    double vx = cx - Cx;
    double vy = cy - Cy;



    double x = (vx / dist) * R + Cx;
    double y = (vy / dist) * R + Cy;
    Point2D point = new Point2D.Double(x, y);


    if (abs(r + R - dist) < EPS || abs(R - (r + dist)) < EPS) return new Point2D[] {point};


    double angle = arccosSafe((r * r - dist * dist - R * R) / (-2.0 * dist * R));


    Point2D pt1 = rotatePoint(C, point, angle);
    Point2D pt2 = rotatePoint(C, point, -angle);

    return new Point2D[] {pt1, pt2};
  }




  private static Point2D rotatePoint(Point2D fp, Point2D pt, double angle) {

    double fpx = fp.getX();
    double fpy = fp.getY();
    double ptx = pt.getX();
    double pty = pt.getY();



    double x = ptx - fpx;
    double y = pty - fpy;




    double xRotated = x * cos(angle) + y * sin(angle);
    double yRotated = y * cos(angle) - x * sin(angle);



    return new Point2D.Double(fpx + xRotated, fpy + yRotated);
  }
}
