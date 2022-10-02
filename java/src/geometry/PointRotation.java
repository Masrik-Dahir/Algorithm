
package geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class PointRotation {




  public static Point2D rotatePoint(Point2D fp, Point2D pt, double angle) {

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

  public static void main(String[] args) {





    double angle = PI / 4.0;
    Point2D fixedPoint = new Point2D.Double(3, 5);
    Point2D point = new Point2D.Double(4, 5);


    Point2D rotatedPoint = point;
    for (int i = 0; i < 8; i++) {
      System.out.println("Rotated point is now at: " + rotatedPoint);
      rotatedPoint = rotatePoint(fixedPoint, rotatedPoint, angle);
    }
    System.out.println("Rotated point is now at: " + rotatedPoint);
  }
}
