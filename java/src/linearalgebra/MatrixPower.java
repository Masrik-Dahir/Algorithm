
package linearalgebra;

public class MatrixPower {

  static long[][] matrixDeepCopy(long[][] M) {
    final int N = M.length;
    long[][] newMatrix = new long[N][N];
    for (int i = 0; i < N; i++) newMatrix[i] = M[i].clone();
    return newMatrix;
  }


  static long[][] squareMatrixMult(long[][] m1, long[][] m2) {

    final int N = m1.length;
    long[][] newMatrix = new long[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        for (int k = 0; k < N; k++)

          newMatrix[i][j] = newMatrix[i][j] + m1[i][k] * m2[k][j];

    return newMatrix;
  }





  static long[][] matrixPower(long[][] matrix, long p) {

    if (p < 0) return null;

    final int N = matrix.length;
    long[][] newMatrix = null;


    if (p == 0) {
      newMatrix = new long[N][N];
      for (int i = 0; i < N; i++) newMatrix[i][i] = 1L;
    } else {

      long[][] P = matrixDeepCopy(matrix);

      while (p > 0) {

        if ((p & 1L) == 1L) {
          if (newMatrix == null) newMatrix = matrixDeepCopy(P);
          else newMatrix = squareMatrixMult(newMatrix, P);
        }


        P = squareMatrixMult(P, P);
        p >>= 1L;
      }
    }

    return newMatrix;
  }

  public static void main(String[] args) {

    long[][] matrix = {{2}};

    System.out.println(matrixPower(matrix, 0)[0][0]);
    System.out.println(matrixPower(matrix, 1)[0][0]);
    System.out.println(matrixPower(matrix, 2)[0][0]);
    System.out.println(matrixPower(matrix, 3)[0][0]);
    System.out.println(matrixPower(matrix, 4)[0][0]);
    System.out.println(matrixPower(matrix, 5)[0][0]);
    System.out.println(matrixPower(matrix, 6)[0][0]);

    long[][] matrix2 = {
      {1, 2},
      {3, 4}
    };

    long[][] result = matrixPower(matrix2, 5);
    print2DMatrix(result);




    result = matrixPower(matrix2, 23);
    print2DMatrix(result);




    long[][] identity = {
      {1, 0, 0, 0, 0, 0},
      {0, 1, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 0},
      {0, 0, 0, 1, 0, 0},
      {0, 0, 0, 0, 1, 0},
      {0, 0, 0, 0, 0, 1}
    };

    result = matrixPower(identity, 987654321987654321L);
    print2DMatrix(result);








  }

  static void print2DMatrix(long[][] M) {
    for (long[] m : M) System.out.println(java.util.Arrays.toString(m));
    System.out.println();
  }
}
