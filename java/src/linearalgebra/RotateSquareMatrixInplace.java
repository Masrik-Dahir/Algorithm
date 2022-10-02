
package linearalgebra;

public class RotateSquareMatrixInplace {


  static void rotate(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n / 2; i++) {
      int invI = n - i - 1;
      for (int j = i; j < invI; j++) {
        int invJ = n - j - 1, tmp = matrix[i][j];
        matrix[i][j] = matrix[invJ][i];
        matrix[invJ][i] = matrix[invI][invJ];
        matrix[invI][invJ] = matrix[j][invI];
        matrix[j][invI] = tmp;
      }
    }
  }

  public static void main(String[] args) {

    int[][] matrix = {
      {1, 2, 3, 4, 5},
      {6, 7, 8, 9, 10},
      {11, 12, 13, 14, 15},
      {16, 17, 18, 19, 20},
      {21, 22, 23, 24, 25}
    };

    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));







    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));







    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));







    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));







  }
}
