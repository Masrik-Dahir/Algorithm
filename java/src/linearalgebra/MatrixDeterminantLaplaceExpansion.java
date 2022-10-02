
package linearalgebra;

public class MatrixDeterminantLaplaceExpansion {


  static final double EPS = 0.00000001;

  public static void main(String[] args) {

    double[][] m = {{6}};
    System.out.println(determinant(m));

    m =
        new double[][] {
          {1, 2},
          {3, 4}
        };
    System.out.println(determinant(m));

    m =
        new double[][] {
          {1, -2, 3},
          {4, -5, 6},
          {7, -8, 10}
        };
    System.out.println(determinant(m));

    m =
        new double[][] {
          {1, -2, 3, 7},
          {4, -5, 6, 2},
          {7, -8, 10, 3},
          {-8, 10, 3, 2}
        };
    System.out.println(determinant(m));

    m =
        new double[][] {
          {1, -2, 3, 7},
          {4, -5, 6, 2},
          {7, -8, 10, 3},
          {-8, 10, 3, 2}
        };
    System.out.println(determinant(m));

    m =
        new double[][] {
          {1, -2, 3, 7, 12},
          {4, -5, 6, 2, 4},
          {7, -8, 10, 3, 1},
          {-8, 10, 8, 3, 2},
          {5, 5, 5, 5, 5}
        };
    System.out.println(determinant(m));

    System.out.println();

    for (int n = 1; ; n++) {
      m = new double[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++) m[i][j] = Math.floor(Math.random() * 10);
      System.out.printf("Found determinant of %dx%d matrix to be: %.4f\n", n, n, determinant(m));
    }
  }



  public static double determinant(double[][] matrix) {

    final int n = matrix.length;


    if (n == 1) return matrix[0][0];


    if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];


    return laplace(matrix);
  }




  private static double laplace(double[][] m) {

    final int n = m.length;


    if (n == 3) {
      double a = m[0][0], b = m[0][1], c = m[0][2];
      double d = m[1][0], e = m[1][1], f = m[1][2];
      double g = m[2][0], h = m[2][1], i = m[2][2];
      return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }

    int det = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {

        double c = m[i][j];
        if (Math.abs(c) > EPS) {
          double[][] newMatrix = constructMatrix(m, j);
          double parity = ((j & 1) == 0) ? +1 : -1;
          det += parity * c * laplace(newMatrix);
        }
      }
    }

    return det;
  }





  private static double[][] constructMatrix(double[][] m, int skipColumn) {

    int n = m.length;
    double[][] newMatrix = new double[n - 1][n - 1];

    int ii = 0;
    for (int i = 1; i < n; i++, ii++) {
      int jj = 0;
      for (int j = 0; j < n; j++) {
        if (j == skipColumn) continue;
        double v = m[i][j];
        newMatrix[ii][jj++] = v;
      }
    }

    return newMatrix;
  }
}
