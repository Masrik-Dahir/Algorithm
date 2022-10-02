
package geometry;

import static java.lang.Math.*;

public class AngleBetweenVectors3D {


  public static double angleBetweenVectors(
      double v1x, double v1y, double v1z, double v2x, double v2y, double v2z) {



    double dotproduct = (v1x * v2x) + (v1y * v2y) + (v1z * v2z);
    double v1Length = sqrt(v1x * v1x + v1y * v1y + v1z * v1z);
    double v2Length = sqrt(v2x * v2x + v2y * v2y + v2z * v2z);

    double value = dotproduct / (v1Length * v2Length);



    if (value <= -1.0) return PI;
    if (value >= +1.0) return 0;
    return acos(value);
  }

  public static void main(String[] args) {
    System.out.println(
        "Angle between  (1,1,0),  (1,1,1): " + angleBetweenVectors(1, 1, 0, 1, 1, 1));
    System.out.println(
        "Angle between  (5,5,5),  (5,5,5): " + angleBetweenVectors(5, 5, 5, 5, 5, 5));
    System.out.println(
        "Angle between  (-5,-5,-5),  (-5,-5,-5): " + angleBetweenVectors(-5, -5, -5, -5, -5, -5));
    System.out.println(
        "Angle between  (2,2,2),  (-1,-1,-1): " + angleBetweenVectors(2, 2, 2, -1, -1, -1));
    System.out.println(
        "Angle between  (4,-6,5), (-3,7,12): " + angleBetweenVectors(4, -6, 5, -3, 7, 12));
  }
}
