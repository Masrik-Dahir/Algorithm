
package math;

public class GCD {



  public static long gcd(long a, long b) {
    return b == 0 ? (a < 0 ? -a : a) : gcd(b, a % b);
  }

  public static void main(String[] args) {
    System.out.println(gcd(12, 18));
    System.out.println(gcd(-12, 18));
    System.out.println(gcd(12, -18));
    System.out.println(gcd(-12, -18));

    System.out.println(gcd(5, 0));
    System.out.println(gcd(0, 5));
    System.out.println(gcd(-5, 0));
    System.out.println(gcd(0, -5));
    System.out.println(gcd(0, 0));
  }
}
