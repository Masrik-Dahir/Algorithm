
package datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BinaryHeapQuickRemovals<T extends Comparable<T>> {


  private List<T> heap = null;





  private Map<T, TreeSet<Integer>> map = new HashMap<>();


  public BinaryHeapQuickRemovals() {
    this(1);
  }


  public BinaryHeapQuickRemovals(int sz) {
    heap = new ArrayList<>(sz);
  }



  public BinaryHeapQuickRemovals(T[] elems) {

    int heapSize = elems.length;
    heap = new ArrayList<T>(heapSize);


    for (int i = 0; i < heapSize; i++) {
      mapAdd(elems[i], i);
      heap.add(elems[i]);
    }


    for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) sink(i);
  }


  public BinaryHeapQuickRemovals(Collection<T> elems) {
    this(elems.size());
    for (T elem : elems) add(elem);
  }


  public boolean isEmpty() {
    return size() == 0;
  }


  public void clear() {
    heap.clear();
    map.clear();
  }


  public int size() {
    return heap.size();
  }




  public T peek() {
    if (isEmpty()) return null;
    return heap.get(0);
  }


  public T poll() {
    return removeAt(0);
  }


  public boolean contains(T elem) {


    if (elem == null) return false;
    return map.containsKey(elem);







  }



  public void add(T elem) {

    if (elem == null) throw new IllegalArgumentException();

    heap.add(elem);
    int indexOfLastElem = size() - 1;
    mapAdd(elem, indexOfLastElem);

    swim(indexOfLastElem);
  }



  private boolean less(int i, int j) {

    T node1 = heap.get(i);
    T node2 = heap.get(j);
    return node1.compareTo(node2) <= 0;
  }


  private void swim(int k) {


    int parent = (k - 1) / 2;



    while (k > 0 && less(k, parent)) {


      swap(parent, k);
      k = parent;


      parent = (k - 1) / 2;
    }
  }


  private void sink(int k) {
    int heapSize = size();

    while (true) {

      int left = 2 * k + 1;
      int right = 2 * k + 2;
      int smallest = left;



      if (right < heapSize && less(right, left)) smallest = right;



      if (left >= heapSize || less(k, smallest)) break;


      swap(smallest, k);
      k = smallest;
    }
  }


  private void swap(int i, int j) {

    T i_elem = heap.get(i);
    T j_elem = heap.get(j);

    heap.set(i, j_elem);
    heap.set(j, i_elem);

    mapSwap(i_elem, j_elem, i, j);
  }


  public boolean remove(T element) {

    if (element == null) return false;










    Integer index = mapGet(element);
    if (index != null) removeAt(index);
    return index != null;
  }


  private T removeAt(int i) {

    if (isEmpty()) return null;

    int indexOfLastElem = size() - 1;
    T removed_data = heap.get(i);
    swap(i, indexOfLastElem);


    heap.remove(indexOfLastElem);
    mapRemove(removed_data, indexOfLastElem);


    if (i == indexOfLastElem) return removed_data;

    T elem = heap.get(i);


    sink(i);


    if (heap.get(i).equals(elem)) swim(i);

    return removed_data;
  }





  public boolean isMinHeap(int k) {


    int heapSize = size();
    if (k >= heapSize) return true;

    int left = 2 * k + 1;
    int right = 2 * k + 2;




    if (left < heapSize && !less(k, left)) return false;
    if (right < heapSize && !less(k, right)) return false;


    return isMinHeap(left) && isMinHeap(right);
  }


  private void mapAdd(T value, int index) {

    TreeSet<Integer> set = map.get(value);


    if (set == null) {

      set = new TreeSet<>();
      set.add(index);
      map.put(value, set);


    } else set.add(index);
  }


  private void mapRemove(T value, int index) {
    TreeSet<Integer> set = map.get(value);
    set.remove(index);
    if (set.size() == 0) map.remove(value);
  }




  private Integer mapGet(T value) {
    TreeSet<Integer> set = map.get(value);
    if (set != null) return set.last();
    return null;
  }


  private void mapSwap(T val1, T val2, int val1Index, int val2Index) {

    Set<Integer> set1 = map.get(val1);
    Set<Integer> set2 = map.get(val2);

    set1.remove(val1Index);
    set2.remove(val2Index);

    set1.add(val2Index);
    set2.add(val1Index);
  }

  @Override
  public String toString() {
    return heap.toString();
  }
}
