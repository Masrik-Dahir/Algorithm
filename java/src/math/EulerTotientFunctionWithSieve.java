
package math;

public class EulerTotientFunctionWithSieve {





  private static int MAX = 1000000;
  private static int[] PRIMES = sieve(MAX);




  public static int totient(int n) {

    if (n >= MAX - 1) throw new IllegalStateException("MAX not large enough!");
    int ans = n;

    for (int i = 1, p = PRIMES[0]; p * p <= n; i++) {

      if (n % p == 0) ans -= ans / p;
      while (n % p == 0) n /= p;
      p = PRIMES[i];
    }


    if (n != 1) ans -= ans / n;
    return ans;
  }


  private static int[] sieve(int limit) {

    if (limit <= 2) return new int[0];



    final int numPrimes = (int) (1.25506 * limit / Math.log((double) limit));
    int[] primes = new int[numPrimes];
    int index = 0;

    boolean[] isComposite = new boolean[limit];
    final int sqrtLimit = (int) Math.sqrt(limit);
    for (int i = 2; i <= sqrtLimit; i++) {
      if (!isComposite[i]) {
        primes[index++] = i;
        for (int j = i * i; j < limit; j += i) isComposite[j] = true;
      }
    }
    for (int i = sqrtLimit + 1; i < limit; i++) if (!isComposite[i]) primes[index++] = i;
    return java.util.Arrays.copyOf(primes, index);
  }

  public static void main(String[] args) {


    System.out.printf("phi(15) = %d\n", totient(15));

    System.out.println();

    for (int x = 1; x <= 11; x++) {
      System.out.printf("phi(%d) = %d\n", x, totient(x));
    }
  }
}
