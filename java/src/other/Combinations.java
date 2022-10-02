
package other;

public class Combinations {


  public static void combinationsChooseR(int[] set, int r) {

    if (r < 0) return;
    if (set == null) return;

    boolean[] used = new boolean[set.length];
    combinations(set, r, 0, used);
  }




  private static void combinations(int[] set, int r, int at, boolean[] used) {

    final int N = set.length;


    int elementsLeftToPick = N - at;
    if (elementsLeftToPick < r) return;


    if (r == 0) {

      System.out.print("{ ");
      for (int i = 0; i < N; i++) if (used[i]) System.out.print(set[i] + " ");
      System.out.println("}");

    } else {

      for (int i = at; i < N; i++) {


        used[i] = true;

        combinations(set, r - 1, i + 1, used);


        used[i] = false;
      }
    }
  }





  public static boolean nextCombination(int[] selection, int N, int r) {
    if (r > N) throw new IllegalArgumentException("r must be <= N");
    int i = r - 1;
    while (selection[i] == N - r + i) if (--i < 0) return false;
    selection[i]++;
    for (int j = i + 1; j < r; j++) selection[j] = selection[i] + j - i;
    return true;
  }

  public static void main(String[] args) {


    int R = 3;
    int[] set = {1, 2, 3, 4, 5};
    combinationsChooseR(set, R);













    String[] colors = {"red", "purple", "green", "yellow", "blue", "pink"};
    R = 3;


    int[] selection = {0, 1, 2};
    do {


      for (int index : selection) System.out.print(colors[index] + " ");
      System.out.println();

    } while (nextCombination(selection, colors.length, R));






















  }
}
