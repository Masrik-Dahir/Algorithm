
package geometry;

import static java.lang.Math.*;

import java.awt.geom.*;

public class TriangleArea {



  public static double triangleArea(double side1, double side2, double side3) {


    double s = (side1 + side2 + side3) / 2.0;


    return sqrt(s * (s - side1) * (s - side2) * (s - side3));
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


  public static double triangleArea(Point2D a, Point2D b, Point2D c) {
    return triangleArea(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
  }


  public static double triangleArea(double base, double height) {
    return base * (height / 2.0);
  }


  public static void main(String[] args) {

    Point2D a = new Point2D.Double(0, 1);
    Point2D b = new Point2D.Double(2, 0);
    Point2D c = new Point2D.Double(3, 4);

    System.out.println(triangleArea(a, b, c));

    double side1 = a.distance(b);
    double side2 = a.distance(c);
    double side3 = b.distance(c);

    System.out.println(triangleArea(side1, side2, side3));
  }
}
