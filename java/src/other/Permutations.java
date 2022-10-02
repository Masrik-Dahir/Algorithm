
package other;

public class Permutations {




  public static void generatePermutations(Object[] sequence) {
    if (sequence == null) return;
    boolean[] used = new boolean[sequence.length];
    int[] picked = new int[sequence.length];
    permutations(0, used, picked, sequence);
  }






  private static void permutations(int at, boolean[] used, int[] picked, Object[] sequence) {

    final int N = sequence.length;


    if (at == N) {


      System.out.print("[ ");
      for (int i = 0; i < N; i++) System.out.print(sequence[picked[i]] + " ");
      System.out.println("]");

    } else {

      for (int i = 0; i < N; i++) {



        if (!used[i]) {



          used[i] = true;
          picked[at] = i;
          permutations(at + 1, used, picked, sequence);


          used[i] = false;
        }
      }
    }
  }







  static <T extends Comparable<? super T>> boolean nextPermutation(T[] sequence) {
    int first = getFirst(sequence);
    if (first == -1) return false;
    int toSwap = sequence.length - 1;
    while (sequence[first].compareTo(sequence[toSwap]) >= 0) --toSwap;
    swap(sequence, first++, toSwap);
    toSwap = sequence.length - 1;
    while (first < toSwap) swap(sequence, first++, toSwap--);
    return true;
  }

  static <T extends Comparable<? super T>> int getFirst(T[] sequence) {
    for (int i = sequence.length - 2; i >= 0; --i)
      if (sequence[i].compareTo(sequence[i + 1]) < 0) return i;
    return -1;
  }

  static <T extends Comparable<? super T>> void swap(T[] sequence, int i, int j) {
    T tmp = sequence[i];
    sequence[i] = sequence[j];
    sequence[j] = tmp;
  }

  public static void main(String[] args) {

    Integer[] sequence = {1, 1, 2, 3};
    generatePermutations(sequence);


























    String[] alpha = {"A", "B", "C", "D"};
    do {

      System.out.println(java.util.Arrays.toString(alpha));


    } while (nextPermutation(alpha));


























  }
}
