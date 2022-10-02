
package geometry;

import static java.lang.Math.*;

public class CoplanarPoints {

  private static final double EPS = 1e-7;


  public static class Vector {
    double x, y, z;

    public Vector(double xx, double yy, double zz) {
      x = xx;
      y = yy;
      z = zz;
    }
  }



  public static boolean coplanar(
      double ax,
      double ay,
      double az,
      double bx,
      double by,
      double bz,
      double cx,
      double cy,
      double cz,
      double dx,
      double dy,
      double dz) {












    Vector v1 = new Vector(bx - ax, by - ay, bz - az);
    Vector v2 = new Vector(cx - ax, cy - ay, cz - az);
    Vector v3 = new Vector(dx - ax, dy - ay, dz - az);


    Vector v4 = cross(v1, v2);


    return abs(dot(v3, v4)) < EPS;
  }


  private static Vector cross(Vector v1, Vector v2) {
    double v3x = v1.y * v2.z - v1.z * v2.y;
    double v3y = v1.z * v2.x - v1.x * v2.z;
    double v3z = v1.x * v2.y - v1.y * v2.x;
    return new Vector(v3x, v3y, v3z);
  }


  private static double dot(Vector v1, Vector v2) {
    return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
  }


  public static void main(String[] args) {

    System.out.println(
        "The points (0,0,0), (1,0,1), (0,1,1), (1,1,2) are coplanar: "
            + coplanar(0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 2));

    System.out.println(
        "The points (0,0,0), (3,3,3), (3,0,0), (0,4,0) are coplanar: "
            + coplanar(0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 4, 0));

    System.out.println(
        "The points (0,0,0), (1,1,1), (2,2,2), (3,3,3) are coplanar: "
            + coplanar(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3));
  }
}
