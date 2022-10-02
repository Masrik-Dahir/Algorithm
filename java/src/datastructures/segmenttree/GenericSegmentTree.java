
package datastructures.segmenttree;

import java.util.function.BinaryOperator;

public class GenericSegmentTree {


  public static enum SegmentCombinationFn {
    SUM,
    MIN,
    MAX,
    GCD,
    PRODUCT
  }



  public static enum RangeUpdateFn {

    ASSIGN,

    ADDITION,

    MULTIPLICATION
  }


  private int n;



  private Long[] t;



  private Long[] lazy;


  private BinaryOperator<Long> combinationFn;


  private interface Ruf {




    Long apply(Long base, long tl, long tr, Long delta);
  }



  private Ruf ruf;




  private Ruf lruf;

  private long safeSum(Long a, Long b) {
    if (a == null) a = 0L;
    if (b == null) b = 0L;
    return a + b;
  }

  private Long safeMul(Long a, Long b) {
    if (a == null) a = 1L;
    if (b == null) b = 1L;
    return a * b;
  }

  private Long safeMin(Long a, Long b) {
    if (a == null) return b;
    if (b == null) return a;
    return Math.min(a, b);
  }

  private Long safeMax(Long a, Long b) {
    if (a == null) return b;
    if (b == null) return a;
    return Math.max(a, b);
  }

  private BinaryOperator<Long> sumCombinationFn = (a, b) -> safeSum(a, b);
  private BinaryOperator<Long> minCombinationFn = (a, b) -> safeMin(a, b);
  private BinaryOperator<Long> maxCombinationFn = (a, b) -> safeMax(a, b);
  private BinaryOperator<Long> productCombinationFn = (a, b) -> safeMul(a, b);
  private BinaryOperator<Long> gcdCombinationFn =
      (a, b) -> {
        if (a == null) return b;
        if (b == null) return a;
        long gcd = a;
        while (b != 0) {
          gcd = b;
          b = a % b;
          a = gcd;
        }
        return Math.abs(gcd);
      };




  private Ruf minQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);
  private Ruf lminQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);


  private Ruf minQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lminQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf minQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lminQueryAssignUpdate = (b, tl, tr, d) -> d;

  private Ruf maxQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);
  private Ruf lmaxQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);


  private Ruf maxQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lmaxQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf maxQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lmaxQueryAssignUpdate = (b, tl, tr, d) -> d;

  private Ruf sumQuerySumUpdate = (b, tl, tr, d) -> b + (tr - tl + 1) * d;
  private Ruf lsumQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);

  private Ruf sumQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lsumQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf sumQueryAssignUpdate = (b, tl, tr, d) -> (tr - tl + 1) * d;
  private Ruf lsumQueryAssignUpdate = (b, tl, tr, d) -> d;



  private Ruf gcdQuerySumUpdate = (b, tl, tr, d) -> null;
  private Ruf lgcdQuerySumUpdate = (b, tl, tr, d) -> null;

  private Ruf gcdQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lgcdQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf gcdQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lgcdQueryAssignUpdate = (b, tl, tr, d) -> d;

  private Ruf productQuerySumUpdate = (b, tl, tr, d) -> b + (long) (Math.pow(d, (tr - tl + 1)));
  private Ruf lproductQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);

  private Ruf productQueryMulUpdate = (b, tl, tr, d) -> b * (long) (Math.pow(d, (tr - tl + 1)));
  private Ruf lproductQueryMulUpdate =
      (b, tl, tr, d) -> safeMul(b, d);

  private Ruf productQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lproductQueryAssignUpdate = (b, tl, tr, d) -> d;

  public GenericSegmentTree(
      long[] values,
      SegmentCombinationFn segmentCombinationFunction,
      RangeUpdateFn rangeUpdateFunction) {
    if (values == null) {
      throw new IllegalArgumentException("Segment tree values cannot be null.");
    }
    if (segmentCombinationFunction == null) {
      throw new IllegalArgumentException("Please specify a valid segment combination function.");
    }
    if (rangeUpdateFunction == null) {
      throw new IllegalArgumentException("Please specify a valid range update function.");
    }
    n = values.length;






    int N = 4 * n;

    t = new Long[N];

    lazy = new Long[N];


    if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
      combinationFn = sumCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = sumQuerySumUpdate;
        lruf = lsumQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = sumQueryAssignUpdate;
        lruf = lsumQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = sumQueryMulUpdate;
        lruf = lsumQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.MIN) {
      combinationFn = minCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = minQuerySumUpdate;
        lruf = lminQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = minQueryAssignUpdate;
        lruf = lminQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = minQueryMulUpdate;
        lruf = lminQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.MAX) {
      combinationFn = maxCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = maxQuerySumUpdate;
        lruf = lmaxQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = maxQueryAssignUpdate;
        lruf = lmaxQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = maxQueryMulUpdate;
        lruf = lmaxQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.GCD) {
      combinationFn = gcdCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = gcdQuerySumUpdate;
        lruf = lgcdQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = gcdQueryAssignUpdate;
        lruf = lgcdQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = gcdQueryMulUpdate;
        lruf = lgcdQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.PRODUCT) {
      combinationFn = productCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = productQuerySumUpdate;
        lruf = lproductQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = productQueryAssignUpdate;
        lruf = lproductQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = productQueryMulUpdate;
        lruf = lproductQueryMulUpdate;
      }
    } else {
      throw new UnsupportedOperationException(
          "Combination function not supported: " + segmentCombinationFunction);
    }

    buildSegmentTree(0, 0, n - 1, values);
  }

  
  private void buildSegmentTree(int i, int tl, int tr, long[] values) {
    if (tl == tr) {
      t[i] = values[tl];
      return;
    }
    int tm = (tl + tr) / 2;
    buildSegmentTree(2 * i + 1, tl, tm, values);
    buildSegmentTree(2 * i + 2, tm + 1, tr, values);

    t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
  }

  
  public Long rangeQuery1(int l, int r) {
    return rangeQuery1(0, 0, n - 1, l, r);
  }

  
  private Long rangeQuery1(int i, int tl, int tr, int l, int r) {

    if (l > r) {
      return null;
    }
    propagate1(i, tl, tr);
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;



    return combinationFn.apply(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }


  private void propagate1(int i, int tl, int tr) {

    if (lazy[i] == null) {
      return;
    }

    t[i] = ruf.apply(t[i], tl, tr, lazy[i]);

    propagateLazy1(i, tl, tr, lazy[i]);
    lazy[i] = null;
  }

  private void propagateLazy1(int i, int tl, int tr, long delta) {

    if (tl == tr) return;
    lazy[2 * i + 1] = lruf.apply(lazy[2 * i + 1], tl, tr, delta);
    lazy[2 * i + 2] = lruf.apply(lazy[2 * i + 2], tl, tr, delta);
  }

  public void rangeUpdate1(int l, int r, long x) {
    rangeUpdate1(0, 0, n - 1, l, r, x);
  }

  private void rangeUpdate1(int i, int tl, int tr, int l, int r, long x) {
    propagate1(i, tl, tr);
    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = ruf.apply(t[i], tl, tr, x);
      propagateLazy1(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;



      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
    }
  }






































  public void printDebugInfo() {
    printDebugInfo(0, 0, n - 1);
    System.out.println();
  }

  private void printDebugInfo(int i, int tl, int tr) {
    System.out.printf("[%d, %d], t[i] = %d, lazy[i] = %d\n", tl, tr, t[i], lazy[i]);
    if (tl == tr) {
      return;
    }
    int tm = (tl + tr) / 2;
    printDebugInfo(2 * i + 1, tl, tm);
    printDebugInfo(2 * i + 2, tm + 1, tr);
  }





  public static void main(String[] args) {
    t();





  }

  private static void productQueryMulUpdateExample() {

    long[] v = {3, 2, 2, 1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.PRODUCT, RangeUpdateFn.MULTIPLICATION);

    int l = 0;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, q);



    st.rangeUpdate1(1, 2, 4);
    q = st.rangeQuery1(l, r);
    if (q != 192) System.out.println("Error");
    System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));



    st.rangeUpdate1(2, 3, 2);
    q = st.rangeQuery1(l, r);
    if (q != 768) System.out.println("Error");
    System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));









  }

  private static void gcdQueryMulUpdateExample() {

    long[] v = {12, 24, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.GCD, RangeUpdateFn.MULTIPLICATION);

    int l = 0;
    int r = 2;
    long q = st.rangeQuery1(l, r);
    if (q != 3) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate1(2, 2, 2);
    q = st.rangeQuery1(l, r);
    if (q != 6) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));

    r = 1;
    q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }

  private static void gcdQueryAssignUpdateExample() {

    long[] v = {12, 24, 3, 12, 48};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.GCD, RangeUpdateFn.ASSIGN);

    int l = 0;
    int r = 2;
    long q = st.rangeQuery1(l, r);
    if (q != 3) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, q);


    st.rangeUpdate1(2, 2, 48);
    q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));


    st.rangeUpdate1(2, 3, 24);
    l = 0;
    r = 4;
    q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }

  private static void sumQuerySumUpdateExample() {

    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.SUM, RangeUpdateFn.ADDITION);

    int l = 1;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 8) System.out.println("Error");
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate1(1, 3, 3);
    q = st.rangeQuery1(l, r);
    if (q != 17) System.out.println("Error");
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }

  private static void t() {
    long[] v = {1, 4, 3, 0, 5, 8, -2, 7, 5, 2, 9};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);
    st.printDebugInfo();
  }

  private static void minQueryAssignUpdateExample() {

    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);



    int l = 1;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 1) System.out.println("Error");
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate1(1, 3, 3);
    l = 0;
    r = 1;
    q = st.rangeQuery1(l, r);
    if (q != 2) System.out.println("Error");
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }
}
