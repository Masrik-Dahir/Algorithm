
package other;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

  int[] values;
  public int N, lo, hi;

  Deque<Integer> deque = new ArrayDeque<>();

  public SlidingWindowMaximum(int[] values) {
    if (values == null) throw new IllegalArgumentException();
    this.values = values;
    N = values.length;
  }


  public void advance() {


    while (!deque.isEmpty() && values[deque.peekLast()] < values[hi])
      deque.removeLast();


    deque.addLast(hi);


    hi++;
  }


  public void shrink() {


    lo++;



    while (!deque.isEmpty() && deque.peekFirst() < lo) deque.removeFirst();
  }


  public int getMax() {
    if (lo >= hi) throw new IllegalStateException("Make sure lo < hi");
    return values[deque.peekFirst()];
  }
}
