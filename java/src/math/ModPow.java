
package math;

public class ModPow {




  private static final long MAX = (long) Math.sqrt(Long.MAX_VALUE);
  private static final long MIN = -MAX;


  private static long gcd(long a, long b) {
    return b == 0 ? (a < 0 ? -a : a) : gcd(b, a % b);
  }






  private static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a < 0 ? -a : a, 1L, 0L};
    long[] v = egcd(b, a % b);
    long tmp = v[1] - v[2] * (a / b);
    v[1] = v[2];
    v[2] = tmp;
    return v;
  }



  private static long modInv(long a, long m) {

    a = ((a % m) + m) % m;

    long[] v = egcd(a, m);
    long x = v[1];

    return ((x % m) + m) % m;
  }




  public static long modPow(long a, long n, long mod) {

    if (mod <= 0) throw new ArithmeticException("mod must be > 0");
    if (a > MAX || mod > MAX)
      throw new IllegalArgumentException("Long overflow is upon you, mod or base is too high!");
    if (a < MIN || mod < MIN)
      throw new IllegalArgumentException("Long overflow is upon you, mod or base is too low!");



    if (n < 0) {
      if (gcd(a, mod) != 1)
        throw new ArithmeticException("If n < 0 then must have gcd(a, mod) = 1");
      return modPow(modInv(a, mod), -n, mod);
    }

    if (n == 0L) return 1L;
    long p = a, r = 1L;

    for (long i = 0; n != 0; i++) {
      long mask = 1L << i;
      if ((n & mask) == mask) {
        r = (((r * p) % mod) + mod) % mod;
        n -= mask;
      }
      p = ((p * p) % mod + mod) % mod;
    }

    return ((r % mod) + mod) % mod;
  }


  public static void main(String[] args) {

    java.math.BigInteger A, N, M, r1;
    long a, n, m, r2;

    A = new java.math.BigInteger("3");
    N = new java.math.BigInteger("4");
    M = new java.math.BigInteger("1000000");
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();


    r1 = A.modPow(N, M);
    r2 = modPow(a, n, m);
    System.out.println(r1 + " " + r2);

    A = new java.math.BigInteger("-45");
    N = new java.math.BigInteger("12345");
    M = new java.math.BigInteger("987654321");
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();


    r1 = A.modPow(N, M);
    r2 = modPow(a, n, m);
    System.out.println(r1 + " " + r2);

    A = new java.math.BigInteger("6");
    N = new java.math.BigInteger("-66");
    M = new java.math.BigInteger("101");
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();


    r1 = A.modPow(N, M);
    r2 = modPow(a, n, m);
    System.out.println(r1 + " " + r2);

    A = new java.math.BigInteger("-5");
    N = new java.math.BigInteger("-7");
    M = new java.math.BigInteger("1009");
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();


    r1 = A.modPow(N, M);
    r2 = modPow(a, n, m);
    System.out.println(r1 + " " + r2);

    for (int i = 0; i < 1000; i++) {
      A = new java.math.BigInteger(a + "");
      N = new java.math.BigInteger(n + "");
      M = new java.math.BigInteger(m + "");
      a = Math.random() < 0.5 ? randLong(MAX) : -randLong(MAX);
      n = randLong();
      m = randLong(MAX);
      try {
        r1 = A.modPow(N, M);
        r2 = modPow(a, n, m);
        if (r1.longValue() != r2)
          System.out.printf("Broke with: a = %d, n = %d, m = %d\n", a, n, m);
      } catch (ArithmeticException e) {
      }
    }
  }

  

  static final java.util.Random RANDOM = new java.util.Random();


  public static long randLong(long bound) {
    return java.util.concurrent.ThreadLocalRandom.current().nextLong(1, bound + 1);
  }

  public static long randLong() {
    return RANDOM.nextLong();
  }
}
