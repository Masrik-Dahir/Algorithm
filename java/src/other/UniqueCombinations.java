
package other;

import java.util.ArrayList;
import java.util.List;

public class UniqueCombinations {

  public static void combinations(int[] set, int r) {

    if (set == null) return;
    if (r < 0) return;


    java.util.Arrays.sort(set);

    boolean[] used = new boolean[set.length];
    combinations(0, r, used, set);
  }

  private static void combinations(int at, int r, boolean[] used, int[] set) {

    final int n = set.length;


    if (r == 0) {

      List<Integer> subset = new ArrayList<>(r);
      for (int i = 0; i < n; i++) if (used[i]) subset.add(set[i]);
      System.out.println(subset);

    } else {
      for (int i = at; i < n; i++) {



        if (i > at && set[i - 1] == set[i]) continue;

        used[i] = true;
        combinations(i + 1, r - 1, used, set);
        used[i] = false;
      }
    }
  }

  public static void main(String[] args) {


    int r = 2;
    int[] set = {2, 3, 3, 2, 3};
    combinations(set, r);





    r = 3;
    set = new int[] {1, 2, 2, 2, 3, 3, 4, 4};
    combinations(set, r);















  }
}
