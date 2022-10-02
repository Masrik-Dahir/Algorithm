
package datastructures.segmenttree;

public class CompactSegmentTree {

  private int N;



  private long UNIQUE = 8123572096793136074L;


  private long[] tree;

  public CompactSegmentTree(int size) {
    tree = new long[2 * (N = size)];
    java.util.Arrays.fill(tree, UNIQUE);
  }

  public CompactSegmentTree(long[] values) {
    this(values.length);

    for (int i = 0; i < N; i++) modify(i, values[i]);
  }






  private long function(long a, long b) {
    if (a == UNIQUE) return b;
    else if (b == UNIQUE) return a;

    return a + b;



  }


  public void modify(int i, long value) {
    tree[i + N] = function(tree[i + N], value);
    for (i += N; i > 1; i >>= 1) {
      tree[i >> 1] = function(tree[i], tree[i ^ 1]);
    }
  }


  public long query(int l, int r) {
    long res = UNIQUE;
    for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
      if ((l & 1) != 0) res = function(res, tree[l++]);
      if ((r & 1) != 0) res = function(res, tree[--r]);
    }
    if (res == UNIQUE) {
      throw new IllegalStateException("UNIQUE should not be the return value.");
    }
    return res;
  }

  public static void main(String[] args) {

    example2();
  }

  private static void example1() {
    long[] values = new long[] {3, 0, 8, 9, 8, 2, 5, 3, 7, 1};
    CompactSegmentTree st = new CompactSegmentTree(values);
    System.out.println(java.util.Arrays.toString(st.tree));
  }

  private static void example2() {
    long[] values = new long[] {1, 1, 1, 1, 1, 1};
    CompactSegmentTree st = new CompactSegmentTree(values);
    System.out.println(java.util.Arrays.toString(st.tree));

    System.out.println(st.query(0, 6));
    System.out.println(st.query(1, 5));
    System.out.println(st.query(0, 2));
  }
}
