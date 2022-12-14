
package math;

import java.math.BigInteger;

public class NChooseRModPrime {

  public static long compute(int N, int R, int P) {
    if (R == 0) return 1;

    long[] factorial = new long[N + 1];
    factorial[0] = 1;

    for (int i = 1; i <= N; i++) {
      factorial[i] = factorial[i - 1] * i % P;
    }

    return (factorial[N]
            * ModularInverse.modInv(factorial[R], P)
            % P
            * ModularInverse.modInv(factorial[N - R], P)
            % P)
        % P;
  }


  private static String bigIntegerNChooseRModP(int N, int R, int P) {
    if (R == 0) return "1";
    BigInteger num = new BigInteger("1");
    BigInteger den = new BigInteger("1");
    while (R > 0) {
      num = num.multiply(new BigInteger("" + N));
      den = den.multiply(new BigInteger("" + R));
      BigInteger gcd = num.gcd(den);
      num = num.divide(gcd);
      den = den.divide(gcd);
      N--;
      R--;
    }
    num = num.divide(den);
    num = num.mod(new BigInteger("" + P));
    return num.toString();
  }

  public static void main(String args[]) {
    int N = 500;
    int R = 250;
    int P = 1000000007;
    int expected = Integer.parseInt(bigIntegerNChooseRModP(N, R, P));
    long actual = compute(N, R, P);
    System.out.println(expected);
    System.out.println(actual);
  }
}
