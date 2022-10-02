
package geometry;

import static java.lang.Math.*;

public class AngleBetweenVectors2D {


  public static double angleBetweenVectors(double v1x, double v1y, double v2x, double v2y) {




    double dotproduct = (v1x * v2x) + (v1y * v2y);
    double v1Length = sqrt(v1x * v1x + v1y * v1y);
    double v2Length = sqrt(v2x * v2x + v2y * v2y);

    double value = dotproduct / (v1Length * v2Length);



    if (value <= -1.0) return PI;
    if (value >= +1.0) return 0;
    return acos(value);
  }

  

  public static void main(String[] args) {
    System.out.println("Angle between   (0,1),   (1,0): " + angleBetweenVectors(0, 1, 1, 0));
    System.out.println("Angle between   (0,1),  (0,-1): " + angleBetweenVectors(0, 1, 0, -1));
    System.out.println("Angle between   (2,0), (-1,-1): " + angleBetweenVectors(2, 0, -1, -1));
    System.out.println("Angle between (-1,-1),   (2,0): " + angleBetweenVectors(-1, -1, 2, 0));
    System.out.println("Angle between   (5,0),   (5,0): " + angleBetweenVectors(5, 0, 5, 0));
    System.out.println("Angle between (-1,-1),  (1,-1): " + angleBetweenVectors(-1, -1, 1, -1));
    System.out.println("Angle between  (1,-1), (-1,-1): " + angleBetweenVectors(1, -1, -1, -1));
    System.out.println("Angle between  (0,-1),   (1,1): " + angleBetweenVectors(0, -1, 1, 1));
    System.out.println("Angle between  (-6,6),   (3,0): " + angleBetweenVectors(-6, 6, 3, 0));
    System.out.println("Angle between  (0,-1),  (1,-1): " + angleBetweenVectors(0, -1, 1, -1));
  }
}
