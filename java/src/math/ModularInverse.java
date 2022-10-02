
package math;

public class ModularInverse {






  private static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a, 1L, 0L};
    long[] v = egcd(b, a % b);
    long tmp = v[1] - v[2] * (a / b);
    v[1] = v[2];
    v[2] = tmp;
    return v;
  }



  public static Long modInv(long a, long m) {

    if (m <= 0) throw new ArithmeticException("mod must be > 0");


    a = ((a % m) + m) % m;

    long[] v = egcd(a, m);
    long gcd = v[0];
    long x = v[1];

    if (gcd != 1) return null;
    return ((x + m) % m) % m;
  }

  public static void main(String[] args) {


    System.out.println(modInv(2, 5));



    System.out.println(modInv(4, 18));
  }
}
