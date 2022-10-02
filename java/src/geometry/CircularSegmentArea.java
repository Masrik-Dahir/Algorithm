
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class CircularSegmentArea {

  public static double circularSegmentArea(Point2D center, double r, Point2D p1, Point2D p2) {

    double cx = center.getX(), cy = center.getY();
    double x1 = p1.getX(), y1 = p1.getY();
    double x2 = p2.getX(), y2 = p2.getY();

    double circleArea = PI * r * r;








    double halfCircleArea = circleArea / 2.0;
    double threePointsArea = triangleArea(cx, cy, x1, y1, x2, y2);



    double v1x = x1 - cx, v1y = y1 - cy;
    double v2x = x2 - cx, v2y = y2 - cy;


    v1x = -v1x;
    v1y = -v1y;


    double angle = angleBetweenVectors(v1x, v1y, v2x, v2y);
    double pizzaSliceArea = circleArea * (angle / (2.0 * PI));

    return circleArea - (halfCircleArea + threePointsArea + pizzaSliceArea);
  }


  public static double triangleArea(
      double ax, double ay, double bx, double by, double cx, double cy) {





    double v1_x = bx - ax;
    double v1_y = by - ay;
    double v2_x = cx - ax;
    double v2_y = cy - ay;



    double determinant = v1_x * v2_y - v2_x * v1_y;


    return abs(determinant) / 2.0;
  }


  private static double angleBetweenVectors(double v1x, double v1y, double v2x, double v2y) {




    double dotproduct = (v1x * v2x) + (v1y * v2y);
    double v1Length = sqrt(v1x * v1x + v1y * v1y);
    double v2Length = sqrt(v2x * v2x + v2y * v2y);

    double value = dotproduct / (v1Length * v2Length);



    if (value <= -1.0) return PI;
    if (value >= +1.0) return 0;
    return acos(value);
  }

  public static void main(String[] args) {

    Point2D center = new Point2D.Double(0, 0);
    Point2D p1 = new Point2D.Double(2, 0);
    Point2D p2 = new Point2D.Double(-2, 0);
    System.out.println(circularSegmentArea(center, 2.0, p1, p2));

    p1 = new Point2D.Double(0, 3);
    p2 = new Point2D.Double(3, 0);
    System.out.println(circularSegmentArea(center, 3.0, p1, p2));
  }
}
