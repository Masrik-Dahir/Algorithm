
package datastructures.segmenttree;

public class Node {

  static final int INF = Integer.MAX_VALUE;

  Node left, right;
  int minPos, maxPos, min = 0, sum = 0, lazy = 0;

  public Node(int[] values) {
    if (values == null) throw new IllegalArgumentException("Null input to segment tree.");
    buildTree(0, values.length);
    for (int i = 0; i < values.length; i++) {
      update(i, i + 1, values[i]);
    }
  }

  public Node(int sz) {
    buildTree(0, sz);
  }

  private Node(int l, int r) {
    buildTree(l, r);
  }


  private void buildTree(int l, int r) {

    if (l < 0 || r < 0 || r < l)
      throw new IllegalArgumentException("Illegal range: (" + l + "," + r + ")");

    minPos = l;
    maxPos = r;


    if (l == r - 1) {
      left = right = null;


    } else {
      int mid = (l + r) / 2;
      left = new Node(l, mid);
      right = new Node(mid, r);
    }
  }


  public void update(int l, int r, int change) {


    propagate();


    if (l <= minPos && maxPos <= r) {

      sum += change * (maxPos - minPos);
      min += change;


      if (left != null) left.lazy += change;
      if (right != null) right.lazy += change;


    } else if (r <= minPos || l >= maxPos) {




    } else {

      if (left != null) left.update(l, r, change);
      if (right != null) right.update(l, r, change);
      sum = (left == null ? 0 : left.sum) + (right == null ? 0 : right.sum);
      min = Math.min((left == null ? INF : left.min), (right == null ? INF : right.min));
    }
  }


  public int sum(int l, int r) {


    propagate();


    if (l <= minPos && maxPos <= r) return sum;


    else if (r <= minPos || l >= maxPos) return 0;


    else return (left == null ? 0 : left.sum(l, r)) + (right == null ? 0 : right.sum(l, r));
  }


  public int min(int l, int r) {


    propagate();


    if (l <= minPos && maxPos <= r) return min;


    else if (r <= minPos || l >= maxPos) return INF;


    else
      return Math.min(
          (left == null ? INF : left.min(l, r)), (right == null ? INF : right.min(l, r)));
  }



  private void propagate() {

    if (lazy != 0) {

      sum += lazy * (maxPos - minPos);
      min += lazy;


      if (left != null) left.lazy += lazy;
      if (right != null) right.lazy += lazy;

      lazy = 0;
    }
  }
}
