
package graphtheory;

public class TspBruteForce {




  public static int[] tsp(double[][] matrix) {

    int n = matrix.length;
    int[] permutation = new int[n];
    for (int i = 0; i < n; i++) permutation[i] = i;

    int[] bestTour = permutation.clone();
    double bestTourCost = Double.POSITIVE_INFINITY;


    do {

      double tourCost = computeTourCost(permutation, matrix);

      if (tourCost < bestTourCost) {
        bestTourCost = tourCost;
        bestTour = permutation.clone();
      }

    } while (nextPermutation(permutation));

    return bestTour;
  }

  public static double computeTourCost(int[] tour, double[][] matrix) {

    double cost = 0;


    for (int i = 1; i < matrix.length; i++) {
      int from = tour[i - 1];
      int to = tour[i];
      cost += matrix[from][to];
    }


    int last = tour[matrix.length - 1];
    int first = tour[0];
    return cost + matrix[last][first];
  }





  public static boolean nextPermutation(int[] sequence) {
    int first = getFirst(sequence);
    if (first == -1) return false;
    int toSwap = sequence.length - 1;
    while (sequence[first] >= sequence[toSwap]) --toSwap;
    swap(sequence, first++, toSwap);
    toSwap = sequence.length - 1;
    while (first < toSwap) swap(sequence, first++, toSwap--);
    return true;
  }

  private static int getFirst(int[] sequence) {
    for (int i = sequence.length - 2; i >= 0; --i) if (sequence[i] < sequence[i + 1]) return i;
    return -1;
  }

  private static void swap(int[] sequence, int i, int j) {
    int tmp = sequence[i];
    sequence[i] = sequence[j];
    sequence[j] = tmp;
  }

  public static void main(String[] args) {

    int n = 10;
    double[][] matrix = new double[n][n];
    for (double[] row : matrix) java.util.Arrays.fill(row, 100);


    int edgeCost = 5;
    int[] optimalTour = {2, 7, 6, 1, 9, 8, 5, 3, 4, 0, 2};
    for (int i = 1; i < optimalTour.length; i++)
      matrix[optimalTour[i - 1]][optimalTour[i]] = edgeCost;

    int[] bestTour = tsp(matrix);
    System.out.println(java.util.Arrays.toString(bestTour));

    double tourCost = computeTourCost(bestTour, matrix);
    System.out.println("Tour cost: " + tourCost);
  }
}
