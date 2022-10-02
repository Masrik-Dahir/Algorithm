
package math;

public class RelativelyPrime {


  private static long gcf(long a, long b) {
    return b == 0 ? a : gcf(b, a % b);
  }


  public static boolean areCoprime(long a, long b) {
    return gcf(a, b) == 1;
  }

  public static void main(String[] args) {
    System.out.println(areCoprime(5, 7));
    System.out.println(areCoprime(12, 18));
  }
}
