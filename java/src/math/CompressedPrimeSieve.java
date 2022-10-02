
package math;

public class CompressedPrimeSieve {
  private static final double NUM_BITS = 128.0;
  private static final int NUM_BITS_SHIFT = 7;


  private static void setBit(long[] arr, int n) {
    if ((n & 1) == 0) return;
    arr[n >> NUM_BITS_SHIFT] |= 1L << ((n - 1) >> 1);
  }



  private static boolean isNotSet(long[] arr, int n) {
    if (n < 2) return false;
    if (n == 2) return true;
    if ((n & 1) == 0) return false;
    long chunk = arr[n >> NUM_BITS_SHIFT];
    long mask = 1L << ((n - 1) >> 1);
    return (chunk & mask) != mask;
  }


  public static boolean isPrime(long[] sieve, int n) {
    return isNotSet(sieve, n);
  }



  public static long[] primeSieve(int limit) {
    final int numChunks = (int) Math.ceil(limit / NUM_BITS);
    final int sqrtLimit = (int) Math.sqrt(limit);


    long[] chunks = new long[numChunks];
    chunks[0] = 1;
    for (int i = 3; i <= sqrtLimit; i += 2)
      if (isNotSet(chunks, i))
        for (int j = i * i; j <= limit; j += i)
          if (isNotSet(chunks, j)) {
            setBit(chunks, j);

          }
    return chunks;
  }

  

  public static void main(String[] args) {
    final int limit = 200;
    long[] sieve = CompressedPrimeSieve.primeSieve(limit);

    for (int i = 0; i <= limit; i++) {
      if (CompressedPrimeSieve.isPrime(sieve, i)) {
        System.out.printf("%d is prime!\n", i);
      }
    }
  }
}
