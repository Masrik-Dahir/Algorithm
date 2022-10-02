
package math;

import java.util.*;

public class ChineseRemainderTheorem {


  public static long[] eliminateCoefficient(long c, long a, long m) {

    long d = egcd(c, m)[0];

    if (a % d != 0) return null;

    c /= d;
    a /= d;
    m /= d;

    long inv = egcd(c, m)[1];
    m = Math.abs(m);
    a = (((a * inv) % m) + m) % m;

    return new long[] {a, m};
  }



  public static long[][] reduce(long[] a, long[] m) {

    List<Long> aNew = new ArrayList<Long>();
    List<Long> mNew = new ArrayList<Long>();


    for (int i = 0; i < a.length; i++) {
      List<Long> factors = primeFactorization(m[i]);
      Collections.sort(factors);
      ListIterator<Long> iterator = factors.listIterator();
      while (iterator.hasNext()) {
        long val = iterator.next();
        long total = val;
        while (iterator.hasNext()) {
          long nextVal = iterator.next();
          if (nextVal == val) {
            total *= val;
          } else {
            iterator.previous();
            break;
          }
        }
        aNew.add(a[i] % total);
        mNew.add(total);
      }
    }


    for (int i = 0; i < aNew.size(); i++) {
      for (int j = i + 1; j < aNew.size(); j++) {
        if (mNew.get(i) % mNew.get(j) == 0 || mNew.get(j) % mNew.get(i) == 0) {
          if (mNew.get(i) > mNew.get(j)) {
            if ((aNew.get(i) % mNew.get(j)) == aNew.get(j)) {
              aNew.remove(j);
              mNew.remove(j);
              j--;
              continue;
            } else return null;
          } else {
            if ((aNew.get(j) % mNew.get(i)) == aNew.get(i)) {
              aNew.remove(i);
              mNew.remove(i);
              i--;
              break;
            } else return null;
          }
        }
      }
    }


    long[][] res = new long[2][aNew.size()];
    for (int i = 0; i < aNew.size(); i++) {
      res[0][i] = aNew.get(i);
      res[1][i] = mNew.get(i);
    }

    return res;
  }

  public static long[] crt(long[] a, long[] m) {

    long M = 1;
    for (int i = 0; i < m.length; i++) M *= m[i];

    long[] inv = new long[a.length];
    for (int i = 0; i < inv.length; i++) inv[i] = egcd(M / m[i], m[i])[1];

    long x = 0;
    for (int i = 0; i < m.length; i++) {
      x += (M / m[i]) * a[i] * inv[i];
      x = ((x % M) + M) % M;
    }

    return new long[] {x, M};
  }

  private static ArrayList<Long> primeFactorization(long n) {
    ArrayList<Long> factors = new ArrayList<Long>();
    if (n <= 0) throw new IllegalArgumentException();
    else if (n == 1) return factors;
    PriorityQueue<Long> divisorQueue = new PriorityQueue<Long>();
    divisorQueue.add(n);
    while (!divisorQueue.isEmpty()) {
      long divisor = divisorQueue.remove();
      if (isPrime(divisor)) {
        factors.add(divisor);
        continue;
      }
      long next_divisor = pollardRho(divisor);
      if (next_divisor == divisor) {
        divisorQueue.add(divisor);
      } else {
        divisorQueue.add(next_divisor);
        divisorQueue.add(divisor / next_divisor);
      }
    }
    return factors;
  }

  private static long pollardRho(long n) {
    if (n % 2 == 0) return 2;

    long x = 2 + (long) (999999 * Math.random());
    long c = 2 + (long) (999999 * Math.random());
    long y = x;
    long d = 1;
    while (d == 1) {
      x = (x * x + c) % n;
      y = (y * y + c) % n;
      y = (y * y + c) % n;
      d = gcf(Math.abs(x - y), n);
      if (d == n) break;
    }
    return d;
  }


  private static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a, 1, 0};
    else {
      long[] ret = egcd(b, a % b);
      long tmp = ret[1] - ret[2] * (a / b);
      ret[1] = ret[2];
      ret[2] = tmp;
      return ret;
    }
  }

  private static long gcf(long a, long b) {
    return b == 0 ? a : gcf(b, a % b);
  }

  private static boolean isPrime(long n) {
    if (n < 2) return false;
    if (n == 2 || n == 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;

    int limit = (int) Math.sqrt(n);

    for (int i = 5; i <= limit; i += 6) if (n % i == 0 || n % (i + 2) == 0) return false;

    return true;
  }
}
