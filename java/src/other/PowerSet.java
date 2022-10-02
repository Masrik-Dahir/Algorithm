
package other;

public class PowerSet {



  static void powerSetUsingBinary(int[] set) {

    final int N = set.length;
    final int MAX_VAL = 1 << N;

    for (int subset = 0; subset < MAX_VAL; subset++) {
      System.out.print("{ ");
      for (int i = 0; i < N; i++) {
        int mask = 1 << i;
        if ((subset & mask) == mask) System.out.print(set[i] + " ");
      }
      System.out.println("}");
    }
  }



  static void powerSetRecursive(int at, int[] set, boolean[] used) {

    if (at == set.length) {


      System.out.print("{ ");
      for (int i = 0; i < set.length; i++) if (used[i]) System.out.print(set[i] + " ");
      System.out.println("}");

    } else {


      used[at] = true;
      powerSetRecursive(at + 1, set, used);


      used[at] = false;
      powerSetRecursive(at + 1, set, used);
    }
  }

  public static void main(String[] args) {


    int[] set = {1, 2, 3};

    powerSetUsingBinary(set);










    System.out.println();

    powerSetRecursive(0, set, new boolean[set.length]);










  }
}
