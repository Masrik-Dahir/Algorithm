
package linearalgebra;

class ModularLinearAlgebra {








  static void rref(int[][] arr, int prime, int[] inv) {
    int n = arr.length, m = arr[0].length;
    int r = 0;
    for (int i = 0; i < m - 1 && r < n; i++) {
      if (arr[r][i] == 0) {
        for (int k = r + 1; k < n; k++) {
          if (arr[k][i] != 0) {
            int[] t = arr[r];
            arr[r] = arr[k];
            arr[k] = t;
            break;
          }
        }
      }
      if (arr[r][i] == 0) {
        continue;
      }
      int inverse = inv[arr[r][i]];
      for (int k = i; k < m; k++) arr[r][k] = (arr[r][k] * inverse) % prime;
      for (int j = 0; j < n; j++) {
        int c = arr[j][i];
        if (j == r || c == 0) continue;
        arr[j][i] = 0;
        for (int k = i + 1; k < m; k++) arr[j][k] = (arr[j][k] - c * arr[r][k] + c * prime) % prime;
      }
      r++;
    }
  }



  static int[][] inverse(int[][] arr, int prime, int[] modInv) {
    if (arr.length != arr[0].length) return null;
    int n = arr.length;
    int[][] augmented = new int[n][n * 2];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        augmented[i][j] = arr[i][j];
      }
      augmented[i][i + n] = 1;
    }
    rref(augmented, prime, modInv);
    int[][] inv = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i == j && augmented[i][j] != 1) return null;
        else if (augmented[i][j] != 0) return null;
        inv[i][j] = augmented[i][j + n];
      }
    }
    return inv;
  }



  static boolean isInconsistent(int[][] arr) {
    int nCols = arr[0].length;
    outer:
    for (int y = 0; y < arr.length; y++) {
      if (arr[y][nCols - 1] != 0) {
        for (int x = 0; x < nCols - 1; x++) {
          if (arr[y][x] != 0) continue outer;
        }
        return true;
      }
    }
    return false;
  }




  static boolean hasMultipleSolutions(int[][] arr) {
    int nCols = arr[0].length;
    int nEmptyRows = 0;
    outer:
    for (int y = 0; y < arr.length; y++) {
      for (int x = 0; x < nCols; x++) {
        if (arr[y][x] != 0) continue outer;
      }
      nEmptyRows++;
    }
    return nCols - 1 > arr.length - nEmptyRows;
  }


  static int[] egcd(int a, int b) {
    if (b == 0) return new int[] {a, 1, 0};
    int[] ret = egcd(b, a % b);
    int tmp = ret[1] - ret[2] * (a / b);
    ret[1] = ret[2];
    ret[2] = tmp;
    return ret;
  }


  static int modInv(int x, int m) {
    return (egcd(x, m)[1] + m) % m;
  }

  public static void main(String[] args) {








    int p = 7;
    int[][] augmentedMatrix = {
      {5, 1, 6, 0},
      {3, 0, 4, 1},
      {1, 5, 2, 4}
    };




    int[] inv = new int[p];
    for (int i = 1; i < p; i++) {
      inv[i] = modInv(i, p);
    }


    rref(augmentedMatrix, p, inv);

    if (!isInconsistent(augmentedMatrix) && !hasMultipleSolutions(augmentedMatrix)) {

      int x = augmentedMatrix[0][3];
      int y = augmentedMatrix[1][3];
      int z = augmentedMatrix[2][3];


      System.out.printf("x = %d, y = %d, z = %d\n", x, y, z);
    }
  }
}
