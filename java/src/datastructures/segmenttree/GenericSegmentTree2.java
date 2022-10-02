
package datastructures.segmenttree;

import java.util.function.BinaryOperator;

public class GenericSegmentTree2 {


  public static enum SegmentCombinationFn {
    SUM,
    MIN,
    MAX
  }



  public static enum RangeUpdateFn {

    ASSIGN,

    ADDITION,

    MULTIPLICATION
  }

  private static class Segment {


    int i;

    Long value;
    Long lazy;



    Long min;
    Long max;


    int tl;
    int tr;

    public Segment(int i, Long value, Long min, Long max, int tl, int tr) {
      this.i = i;
      this.value = value;
      this.min = min;
      this.max = max;
      this.tl = tl;
      this.tr = tr;
    }
















    @Override
    public String toString() {
      return String.format("[%d, %d], value = %d, lazy = %d", tl, tr, value, lazy);
    }
  }


  private int n;



  private Segment[] st;


  private BinaryOperator<Long> combinationFn;

  private interface Ruf {
    Long apply(Segment segment, Long delta);
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




  private Ruf minQuerySumUpdate = (s, x) -> safeSum(s.value, x);
  private Ruf lminQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);


  private Ruf minQueryMulUpdate =
      (s, x) -> {
        if (x == 0) {
          return 0L;
        } else if (x < 0) {

          if (safeMul(s.value, x) == s.min) {
            return s.max;
          } else {
            return s.min;
          }
        } else {
          return safeMul(s.value, x);
        }
      };
  private Ruf lminQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  private Ruf minQueryAssignUpdate = (s, x) -> x;
  private Ruf lminQueryAssignUpdate = (s, x) -> x;

  private Ruf maxQuerySumUpdate = (s, x) -> safeSum(s.value, x);
  private Ruf lmaxQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);


  private Ruf maxQueryMulUpdate =
      (s, x) -> {
        if (x == 0) {
          return 0L;
        } else if (x < 0) {
          if (safeMul(s.value, x) == s.min) {
            return s.max;
          } else {
            return s.min;
          }
        } else {
          return safeMul(s.value, x);
        }
      };
  private Ruf lmaxQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  private Ruf maxQueryAssignUpdate = (s, x) -> x;
  private Ruf lmaxQueryAssignUpdate = (s, x) -> x;

  private Ruf sumQuerySumUpdate = (s, x) -> s.value + (s.tr - s.tl + 1) * x;
  private Ruf lsumQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  private Ruf sumQueryMulUpdate = (s, x) -> safeMul(s.value, x);
  private Ruf lsumQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  private Ruf sumQueryAssignUpdate = (s, x) -> (s.tr - s.tl + 1) * x;
  private Ruf lsumQueryAssignUpdate = (s, x) -> x;

  public GenericSegmentTree2(
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

    st = new Segment[N];


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
    } else {
      throw new UnsupportedOperationException(
          "Combination function not supported: " + segmentCombinationFunction);
    }

    buildSegmentTree(0, 0, n - 1, values);
  }

  
  private void buildSegmentTree(int i, int tl, int tr, long[] values) {
    if (tl == tr) {
      st[i] = new Segment(i, values[tl], values[tl], values[tl], tl, tr);
      return;
    }
    int tm = (tl + tr) / 2;
    buildSegmentTree(2 * i + 1, tl, tm, values);
    buildSegmentTree(2 * i + 2, tm + 1, tr, values);

    Long segmentValue = combinationFn.apply(st[2 * i + 1].value, st[2 * i + 2].value);
    Long minValue = Math.min(st[2 * i + 1].min, st[2 * i + 2].min);
    Long maxValue = Math.max(st[2 * i + 1].max, st[2 * i + 2].max);
    Segment segment = new Segment(i, segmentValue, minValue, maxValue, tl, tr);

    st[i] = segment;
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
      return st[i].value;
    }
    int tm = (tl + tr) / 2;



    return combinationFn.apply(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }


  private void propagate1(int i, int tl, int tr) {
    if (st[i].lazy != null) {

      st[i].min = st[i].min * st[i].lazy;
      st[i].max = st[i].max * st[i].lazy;


      st[i].value = ruf.apply(st[i], st[i].lazy);

      propagateLazy1(i, tl, tr, st[i].lazy);
      st[i].lazy = null;
    }
  }

  private void propagateLazy1(int i, int tl, int tr, long delta) {

    if (tl == tr) return;
    st[2 * i + 1].lazy = lruf.apply(st[2 * i + 1], delta);
    st[2 * i + 2].lazy = lruf.apply(st[2 * i + 2], delta);
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

      st[i].min = st[i].min * x;
      st[i].max = st[i].max * x;

      st[i].value = ruf.apply(st[i], x);
      propagateLazy1(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;



      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      st[i].value = combinationFn.apply(st[2 * i + 1].value, st[2 * i + 2].value);
      st[i].max = Math.max(st[2 * i + 1].max, st[2 * i + 2].max);
      st[i].min = Math.min(st[2 * i + 1].min, st[2 * i + 2].min);
    }
  }






































  public void printDebugInfo() {
    printDebugInfo(0);
    System.out.println();
  }

  private void printDebugInfo(int i) {
    System.out.println(st[i]);
    if (st[i].tl == st[i].tr) {
      return;
    }
    printDebugInfo(2 * i + 1);
    printDebugInfo(2 * i + 2);
  }





  public static void main(String[] args) {
    minQuerySumUpdate();
    sumQuerySumUpdateExample();
    minQueryAssignUpdateExample();
  }

  private static void minQuerySumUpdate() {

    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(v, SegmentCombinationFn.MIN, RangeUpdateFn.ADDITION);

    int l = 1;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 1) System.out.println("Error");
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, q);

    st.printDebugInfo();
  }

  private static void sumQuerySumUpdateExample() {

    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(v, SegmentCombinationFn.SUM, RangeUpdateFn.ADDITION);

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

  private static void minQueryAssignUpdateExample() {

    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);

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
