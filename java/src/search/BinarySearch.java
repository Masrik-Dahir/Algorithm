
package search;

import java.util.function.DoubleFunction;

public class BinarySearch {



  private static final double EPS = 0.00000001;

  public static double binarySearch(
      double lo, double hi, double target, DoubleFunction<Double> function) {

    if (hi <= lo) throw new IllegalArgumentException("hi should be greater than lo");

    double mid;
    do {


      mid = (hi + lo) / 2.0;



      double value = function.apply(mid);

      if (value > target) {
        hi = mid;
      } else {
        lo = mid;
      }

    } while ((hi - lo) > EPS);

    return mid;
  }

  public static void main(String[] args) {












    double lo = 0.0;
    double hi = 875.0;
    double target = 875.0;

    DoubleFunction<Double> function = (x) -> (x * x);

    double sqrtVal = binarySearch(lo, hi, target, function);
    System.out.printf("sqrt(%.2f) = %.5f, x^2 = %.5f\n", target, sqrtVal, (sqrtVal * sqrtVal));









    double radiusLowerBound = 0;
    double radiusUpperBound = 1000;
    double volume = 100.0;
    DoubleFunction<Double> sphereVolumeFunction = (r) -> ((4.0 / 3.0) * Math.PI * r * r * r);

    double sphereRadius =
        binarySearch(radiusLowerBound, radiusUpperBound, volume, sphereVolumeFunction);

    System.out.printf("Sphere radius = %.5fm\n", sphereRadius);
  }
}
