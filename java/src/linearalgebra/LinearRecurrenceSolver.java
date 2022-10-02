
package linearalgebra;

class LinearRecurrenceSolver {

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



  static long[][] createTransformationMatrix(long[] coeffs, int size) {

    long T[][] = new long[size][size];
    for (int i = 0; i + 1 < size; i++) T[i][i + 1] = 1L;
    for (int i = 0; i < size - 1; i++) T[size - 2][i] = coeffs[coeffs.length - i - 1];
    T[size - 1][size - 1] = T[size - 2][size - 1] = 1L;
    return T;
  }


  static long solveRecurrence(long[] coefficients, long f_0, long k, long n) {

    if (n < 0) throw new IllegalArgumentException("n should probably be >= 0");
    long[] initialValues = computeInitialValues(coefficients, f_0, k);


    if (n < initialValues.length) return initialValues[(int) n];


    final int size = initialValues.length + 1;

    long[][] T = createTransformationMatrix(coefficients, size);
    long[][] result = matrixPower(T, n);



    long ans = 0L;
    for (int j = 0; j < size; j++) {
      if (j == size - 1) {
        ans = ans + result[0][j] * k;
      } else {
        ans = ans + result[0][j] * initialValues[j];
      }
    }

    return ans;
  }


  static long[] computeInitialValues(long[] coeffs, long f_0, long k) {

    final int N = coeffs.length;
    long[] DP = new long[N];
    DP[0] = f_0;

    for (int n = 1; n < N; n++) {
      for (int i = 1; i <= n; i++) DP[n] += DP[n - i] * coeffs[i - 1];
      DP[n] += k;
    }

    return DP;
  }

  public static void main(String[] args) {


    long[] coefficients = {1, 1};
    long k = 0;

    for (int i = 0; i <= 10; i++) {
      long fib = solveRecurrence(coefficients, 1, k, i);
      System.out.println(fib);
    }




    long[] coefficients2 = {2, 0, 1};
    k = 2;

    final int N = 25;
    long[] DP = new long[N + 1];



    for (int n = 0; n <= N; n++) {
      if (n - 1 >= 0) DP[n] += 2 * DP[n - 1];
      if (n - 3 >= 0) DP[n] += DP[n - 3];
      DP[n] += k;
    }

    long answer = solveRecurrence(coefficients2, k, k, N);
    if (DP[N] != answer) throw new RuntimeException("Wrong answer!");
    System.out.printf("f(%d) = %d\n", N, answer);
  }
}
