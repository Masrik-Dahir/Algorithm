
package linearalgebra;

public class Simplex {

  static final double EPS = 1e-9;

















  public static double simplex(double[][] m) {
    while (true) {
      double min = -EPS;
      int c = -1;
      for (int j = 1; j < m[0].length; j++) {
        if (m[0][j] < min) {
          min = m[0][j];
          c = j;
        }
      }
      if (c < 0) break;
      min = Double.MAX_VALUE;
      int r = -1;
      for (int i = 1; i < m.length; i++) {
        if (m[i][c] > EPS) {
          double v = m[i][0] / m[i][c];
          if (v < min) {
            min = v;
            r = i;
          }
        }
      }
      double v = m[r][c];
      for (int j = 0; j < m[r].length; j++) m[r][j] /= v;
      for (int i = 0; i < m.length; i++) {
        if (i != r) {
          v = m[i][c];
          for (int j = 0; j < m[i].length; j++) m[i][j] -= m[r][j] * v;
        }
      }
    }
    return m[0][0];
  }
}
