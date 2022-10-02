
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class CollinearPoints {


  private static final double EPS = 1e-9;






  public static int collinear(Point2D a, Point2D b, Point2D c) {

    if (a.distance(b) < EPS) throw new IllegalArgumentException("a cannot equal b");











    double v1_x = b.getX() - a.getX();
    double v1_y = b.getY() - a.getY();
    double v2_x = c.getX() - a.getX();
    double v2_y = c.getY() - a.getY();




    double determinant = (v1_x * v2_y) - (v2_x * v1_y);


    if (abs(determinant) < EPS) return 0;


    return (determinant < 0 ? -1 : +1);
  }


  public static int collinear2(Point2D a, Point2D b, Point2D c) {
    double area =
        (b.getX() - a.getX()) * (c.getY() - a.getY())
            - (b.getY() - a.getY()) * (c.getX() - a.getX());
    if (abs(area) < EPS) return 0;
    return (int) signum(area);
  }


  public static void main(String[] args) {

    Point2D a = new Point2D.Double(1, 1);
    Point2D b = new Point2D.Double(3, 3);
    Point2D c = new Point2D.Double(7, 7);


    System.out.println(collinear(a, b, c));


    c = new Point2D.Double(2, 7);
    System.out.println(collinear(a, b, c));


    c = new Point2D.Double(2, -7);
    System.out.println(collinear(a, b, c));

    System.out.println(collinear2(a, b, c));
  }
}
