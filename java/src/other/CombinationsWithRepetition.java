
package other;

public class CombinationsWithRepetition {


  private static void combinationsWithRepetition(
      int[] sequence, int[] usedCount, int at, int r, int k) {

    final int N = sequence.length;


    if (at == N) {


      if (r == 0) {


        System.out.print("{ ");
        for (int i = 0; i < N; i++)
          for (int j = 0; j < usedCount[i]; j++) System.out.print(sequence[i] + " ");
        System.out.println("}");
      }

    } else {


      for (int itemCount = 0; itemCount <= k; itemCount++) {


        usedCount[at] = itemCount;

        combinationsWithRepetition(sequence, usedCount, at + 1, r - itemCount, k);
      }
    }
  }



  public static void printCombinationsWithRepetition(int[] sequence, int r, int k) {

    if (sequence == null) return;
    final int n = sequence.length;
    if (r > n) throw new IllegalArgumentException("r must be <= n");
    if (k > r) throw new IllegalArgumentException("k must be <= r");

    int[] usedCount = new int[sequence.length];
    combinationsWithRepetition(sequence, usedCount, 0, r, k);
  }

  public static void main(String[] args) {



    int[] seq = {1, 2, 3, 4};
    printCombinationsWithRepetition(seq, 3, 2);


















  }
}
