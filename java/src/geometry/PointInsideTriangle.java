
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class PointInsideTriangle {

  private static final double EPS = 1e-8;





  public static boolean pointInsideTriangle(Point2D a, Point2D b, Point2D c, Point2D p) {


    if (collinear(a, b, c) == 0) {
      throw new IllegalArgumentException("points a,b,c do not form a triangle!");
    }



    int dir1 = collinear(a, b, p);
    int dir2 = collinear(b, c, p);
    int dir3 = collinear(c, a, p);



    return (dir1 <= 0 && dir2 <= 0 && dir3 <= 0) || (dir1 >= 0 && dir2 >= 0 && dir3 >= 0);
  }


  public static boolean pointInsideTriangle2(Point2D a, Point2D b, Point2D c, Point2D p) {


    if (collinear(a, b, c) == 0) {
      throw new IllegalArgumentException("points a,b,c do not form a triangle!");
    }


    boolean dir1 = collinear(a, b, p) < 0;
    boolean dir2 = collinear(b, c, p) < 0;
    boolean dir3 = collinear(c, a, p) < 0;

    return (dir1 == dir2) && (dir2 == dir3);
  }






  private static int collinear(Point2D a, Point2D b, Point2D c) {
    double ax = a.getX(), ay = a.getY();
    double bx = b.getX(), by = b.getY();
    double cx = c.getX(), cy = c.getY();
    double area = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax);
    if (abs(area) < EPS) return 0;
    return (int) signum(area);
  }

  public static void main(String[] args) {

    Point2D a = new Point2D.Double(0, 5);
    Point2D b = new Point2D.Double(0, 0);
    Point2D c = new Point2D.Double(5, 0);

    for (int i = -2; i < 7; i++) {
      for (int j = -2; j < 7; j++) {
        Point2D p = new Point2D.Double(i, j);
        if (pointInsideTriangle(a, b, c, p)) {
          System.out.printf("(%.3f,%.3f) is inside the triangle\n", p.getX(), p.getY());
        }
      }
    }

    System.out.println();

    for (int i = -2; i < 7; i++) {
      for (int j = -2; j < 7; j++) {
        Point2D p = new Point2D.Double(i, j);
        if (pointInsideTriangle2(a, b, c, p)) {
          System.out.printf("(%.3f,%.3f) is inside the triangle\n", p.getX(), p.getY());
        }
      }
    }
  }
}
