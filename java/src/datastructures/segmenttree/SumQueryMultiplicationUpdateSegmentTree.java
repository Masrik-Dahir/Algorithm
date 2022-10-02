
package datastructures.segmenttree;

public class SumQueryMultiplicationUpdateSegmentTree {


  private final int n;



  private Long[] t;



  private Long[] lazy;


  private Long sumFunction(Long a, Long b) {
    if (a == null) a = 0L;
    if (b == null) b = 0L;
    return a + b;
  }


  private Long multRuf(Long base, int tl, int tr, Long delta) {


    if (base == null) base = 1L;
    if (delta == null) delta = 1L;
    return base * delta;
  }


  private long multLruf(Long delta1, Long delta2) {


    if (delta1 == null) delta1 = 1L;
    if (delta2 == null) delta2 = 1L;


    return delta1 * delta2;
  }

  public SumQueryMultiplicationUpdateSegmentTree(long[] values) {
    if (values == null) {
      throw new IllegalArgumentException("Segment tree values cannot be null.");
    }
    n = values.length;






    int N = 4 * n;

    t = new Long[N];
    lazy = new Long[N];

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

    t[i] = sumFunction(t[2 * i + 1], t[2 * i + 2]);
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



    return sumFunction(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  public void rangeUpdate1(int l, int r, long x) {
    rangeUpdate1(0, 0, n - 1, l, r, x);
  }

  private void propagateLazy(int i, int tl, int tr, long val) {

    if (tl == tr) return;
    lazy[2 * i + 1] = multLruf(lazy[2 * i + 1], val);
    lazy[2 * i + 2] = multLruf(lazy[2 * i + 2], val);
  }

  private void propagate1(int i, int tl, int tr) {


    if (lazy[i] != null) {
      t[i] = multRuf(t[i],  0,  0, lazy[i]);

      propagateLazy(i, tl, tr, lazy[i]);
      lazy[i] = null;
    }
  }

  private void rangeUpdate1(int i, int tl, int tr, int l, int r, long x) {
    propagate1(i, tl, tr);
    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = multRuf(t[i],  0,  0, x);
      propagateLazy(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;



      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = sumFunction(t[2 * i + 1], t[2 * i + 2]);
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

    long[] v = {2, 1, 3, 4, -1};
    SumQuerySumUpdateSegmentTree st = new SumQuerySumUpdateSegmentTree(v);
  }
}
