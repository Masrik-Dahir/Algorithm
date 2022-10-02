
package datastructures.priorityqueue;

@SuppressWarnings("unchecked")
public class MinDHeap<T extends Comparable<T>> {

  private T[] heap;
  private int d, n, sz;
  private int[] child, parent;


  public MinDHeap(int degree, int maxNodes) {
    d = Math.max(2, degree);
    n = Math.max(d, maxNodes);

    heap = (T[]) new Comparable[n];
    child = new int[n];
    parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = (i - 1) / d;
      child[i] = i * d + 1;
    }
  }


  public int size() {
    return sz;
  }


  public boolean isEmpty() {
    return sz == 0;
  }


  public void clear() {
    java.util.Arrays.fill(heap, null);
    sz = 0;
  }


  public T peek() {
    if (isEmpty()) return null;
    return heap[0];
  }



  public T poll() {
    if (isEmpty()) return null;
    T root = heap[0];
    heap[0] = heap[--sz];
    heap[sz] = null;
    sink(0);
    return root;
  }


  public void add(T elem) {
    if (elem == null) throw new IllegalArgumentException("No null elements please :)");
    heap[sz] = elem;
    swim(sz);
    sz++;
  }

  private void sink(int i) {
    for (int j = minChild(i); j != -1; ) {
      swap(i, j);
      i = j;
      j = minChild(i);
    }
  }

  private void swim(int i) {
    while (less(i, parent[i])) {
      swap(i, parent[i]);
      i = parent[i];
    }
  }


  private int minChild(int i) {
    int index = -1, from = child[i], to = Math.min(sz, from + d);
    for (int j = from; j < to; j++) if (less(j, i)) index = i = j;
    return index;
  }

  private boolean less(int i, int j) {
    return heap[i].compareTo(heap[j]) < 0;
  }

  private void swap(int i, int j) {
    T tmp = heap[i];
    heap[i] = heap[j];
    heap[j] = tmp;
  }
}
