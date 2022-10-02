
package search;

import java.util.function.DoubleFunction;

public class TernarySearch {


  private static final double EPS = 0.000000001;




  public static double ternarySearch(double low, double high, DoubleFunction<Double> function) {
    Double best = null;
    while (true) {
      double mid1 = (2 * low + high) / 3, mid2 = (low + 2 * high) / 3;
      double res1 = function.apply(mid1), res2 = function.apply(mid2);
      if (res1 > res2) low = mid1;
      else high = mid2;
      if (best != null && Math.abs(best - mid1) < EPS) break;
      best = mid1;
    }
    return best;
  }

  public static void main(String[] args) {



    DoubleFunction<Double> function = (x) -> (x * x + 3 * x + 5);
    double root = ternarySearch(-100.0, +100.0, function);
    System.out.printf("%.4f\n", root);
  }
}
