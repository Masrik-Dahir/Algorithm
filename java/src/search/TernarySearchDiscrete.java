
package search;

public class TernarySearchDiscrete {




  static final double EPS = 0.000000001;


  static final double[] function = {16, 12, 10, 3, 6, 7, 9, 10, 11, 12, 13, 17};




  static double f(int i) {
    return function[i];
  }

  static double discreteTernarySearch(int lo, int hi) {
    while (lo != hi) {
      if (hi - lo == 1) return Math.min(f(lo), f(hi));
      if (hi - lo == 2) return Math.min(f(lo), Math.min(f(lo + 1), f(hi)));
      int mid1 = (2 * lo + hi) / 3, mid2 = (lo + 2 * hi) / 3;
      double res1 = f(mid1), res2 = f(mid2);
      if (Math.abs(res1 - res2) < 0.000000001) {
        lo = mid1;
        hi = mid2;
      } else if (res1 > res2) lo = mid1;
      else hi = mid2;
    }
    return f(lo);
  }

  public static void main(String[] args) {

    int lo = 0;
    int hi = function.length - 1;



    double minValue = discreteTernarySearch(lo, hi);
    System.out.printf("%.4f\n", minValue);
  }
}
