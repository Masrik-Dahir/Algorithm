
package math;

public class ExtendedEuclideanAlgorithm {






  public static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a, 1, 0};
    else {
      long[] ret = egcd(b, a % b);
      long tmp = ret[1] - ret[2] * (a / b);
      ret[1] = ret[2];
      ret[2] = tmp;
      return ret;
    }
  }
}
