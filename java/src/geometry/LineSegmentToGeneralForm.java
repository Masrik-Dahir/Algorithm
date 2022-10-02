
package geometry;

public class LineSegmentToGeneralForm {




  public static double[] segmentToGeneralForm(double x1, double y1, double x2, double y2) {







    double[] abc = new double[3];

    abc[0] = y1 - y2;
    abc[1] = x2 - x1;
    abc[2] = x1 * y2 - y1 * x2;

    return abc;
  }


  public static void main(String[] args) {



    double[] abc = segmentToGeneralForm(1, 1, 3, -4);
    System.out.printf("%.2fx + %.2fy + %.2f = 0\n", abc[0], abc[1], abc[2]);
  }
}
