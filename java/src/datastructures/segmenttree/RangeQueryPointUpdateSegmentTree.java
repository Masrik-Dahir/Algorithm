
package datastructures.segmenttree;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class RangeQueryPointUpdateSegmentTree {


  public static enum SegmentCombinationFn {
    SUM,
    MIN,
    MAX
  }



  public static enum RangeUpdateFn {

    ADDITION,

    MULTIPLICATION
  }


  private int n;



  private long[] t;



  private long[] lazy;

  private SegmentCombinationFn segmentCombinationFn;


  private BinaryOperator<Long> combinationFn;


  private BinaryOperator<Long> rangeUpdateFn;

  private BinaryOperator<Long> sumFn = (a, b) -> a + b;
  private BinaryOperator<Long> mulFn = (a, b) -> a * b;
  private BinaryOperator<Long> minFn = (a, b) -> Math.min(a, b);
  private BinaryOperator<Long> maxFn = (a, b) -> Math.max(a, b);

  public RangeQueryPointUpdateSegmentTree(
      long[] values, SegmentCombinationFn segmentCombinationFunction) {

    this(values, segmentCombinationFunction, RangeUpdateFn.ADDITION);
  }

  public RangeQueryPointUpdateSegmentTree(
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
    this.segmentCombinationFn = segmentCombinationFunction;






    int N = 4 * n;

    t = new long[N];
    lazy = new long[N];


    if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
      combinationFn = sumFn;
    } else if (segmentCombinationFunction == SegmentCombinationFn.MIN) {
      Arrays.fill(t, Long.MAX_VALUE);
      combinationFn = minFn;
    } else if (segmentCombinationFunction == SegmentCombinationFn.MAX) {
      Arrays.fill(t, Long.MIN_VALUE);
      combinationFn = maxFn;
    }


    if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
      rangeUpdateFn = sumFn;
    } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
      rangeUpdateFn = mulFn;
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

  
  public long rangeQuery(int l, int r) {
    return rangeQuery(0, 0, n - 1, l, r);
  }

  
  public long rangeQuery2(int l, int r) {
    return rangeQuery2(0, 0, n - 1, l, r);
  }

  
  private long rangeQuery(int i, int tl, int tr, int l, int r) {
    if (l > r) {

      if (segmentCombinationFn == SegmentCombinationFn.SUM) {
        return 0;
      } else if (segmentCombinationFn == SegmentCombinationFn.MIN) {
        return Long.MAX_VALUE;
      } else if (segmentCombinationFn == SegmentCombinationFn.MAX) {
        return Long.MIN_VALUE;
      }
    }
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;



    return combinationFn.apply(
        rangeQuery(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  
  private long rangeQuery2(int i, int tl, int tr, int l, int r) {
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;

    boolean overlapsLeftSegment = (l <= tm);
    boolean overlapsRightSegment = (r > tm);
    if (overlapsLeftSegment && overlapsRightSegment) {
      return combinationFn.apply(
          rangeQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r)),
          rangeQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
    } else if (overlapsLeftSegment) {
      return rangeQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r));
    } else {
      return rangeQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r);
    }
  }


  public void pointUpdate(int i, long newValue) {
    pointUpdate(0, i, 0, n - 1, newValue);
  }

  
  private void pointUpdate(int i, int pos, int tl, int tr, long newValue) {
    if (tl == tr) {
      t[i] = newValue;
      return;
    }
    int tm = (tl + tr) / 2;
    if (pos <= tm) {

      pointUpdate(2 * i + 1, pos, tl, tm, newValue);
    } else {

      pointUpdate(2 * i + 2, pos, tm + 1, tr, newValue);
    }

    t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
  }



  public void rangeUpdate(int l, int r, long x) {
    throw new UnsupportedOperationException("rangeUpdate is not yet implemented");
  }





  public static void main(String[] args) {
    rangeSumQueryExample();
    rangeMinQueryExample();
    rangeMaxQueryExample();
  }

  private static void rangeSumQueryExample() {

    long[] values = {1, 2, 3, 2};
    RangeQueryPointUpdateSegmentTree st =
        new RangeQueryPointUpdateSegmentTree(values, SegmentCombinationFn.SUM);

    int l = 0, r = 3;
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));


  }

  private static void rangeMinQueryExample() {

    long[] values = {1, 2, 3, 2};
    RangeQueryPointUpdateSegmentTree st =
        new RangeQueryPointUpdateSegmentTree(values, SegmentCombinationFn.MIN);

    int l = 0, r = 3;
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));


  }

  private static void rangeMaxQueryExample() {

    long[] values = {1, 2, 3, 2};
    RangeQueryPointUpdateSegmentTree st =
        new RangeQueryPointUpdateSegmentTree(values, SegmentCombinationFn.MAX);

    int l = 0, r = 3;
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));


  }
}
