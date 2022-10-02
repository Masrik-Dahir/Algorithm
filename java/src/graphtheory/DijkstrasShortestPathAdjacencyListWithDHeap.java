
package graphtheory;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class DijkstrasShortestPathAdjacencyListWithDHeap {



  public static class Edge {
    int to;
    double cost;

    public Edge(int to, double cost) {
      this.to = to;
      this.cost = cost;
    }
  }

  private final int n;

  private int edgeCount;
  private double[] dist;
  private Integer[] prev;
  private List<List<Edge>> graph;

  
  public DijkstrasShortestPathAdjacencyListWithDHeap(int n) {
    this.n = n;
    createEmptyGraph();
  }


  private void createEmptyGraph() {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }

  
  public void addEdge(int from, int to, int cost) {
    edgeCount++;
    graph.get(from).add(new Edge(to, cost));
  }

  
  public List<List<Edge>> getGraph() {
    return graph;
  }





  public double dijkstra(int start, int end) {



    int degree = edgeCount / n;
    MinIndexedDHeap<Double> ipq = new MinIndexedDHeap<>(degree, n);
    ipq.insert(start, 0.0);


    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0.0;

    boolean[] visited = new boolean[n];
    prev = new Integer[n];

    while (!ipq.isEmpty()) {
      int nodeId = ipq.peekMinKeyIndex();

      visited[nodeId] = true;
      double minValue = ipq.pollMinValue();



      if (minValue > dist[nodeId]) continue;

      for (Edge edge : graph.get(nodeId)) {



        if (visited[edge.to]) continue;


        double newDist = dist[nodeId] + edge.cost;
        if (newDist < dist[edge.to]) {
          prev[edge.to] = nodeId;
          dist[edge.to] = newDist;


          if (!ipq.contains(edge.to)) ipq.insert(edge.to, newDist);
          else ipq.decrease(edge.to, newDist);
        }
      }




      if (nodeId == end) return dist[end];
    }

    return Double.POSITIVE_INFINITY;
  }

  
  public List<Integer> reconstructPath(int start, int end) {
    if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
    if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");
    List<Integer> path = new ArrayList<>();
    double dist = dijkstra(start, end);
    if (dist == Double.POSITIVE_INFINITY) return path;
    for (Integer at = end; at != null; at = prev[at]) path.add(at);
    Collections.reverse(path);
    return path;
  }

  private static class MinIndexedDHeap<T extends Comparable<T>> {


    private int sz;


    private final int N;


    private final int D;


    private final int[] child, parent;



    public final int[] pm;




    public final int[] im;



    public final Object[] values;


    public MinIndexedDHeap(int degree, int maxSize) {
      if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

      D = max(2, degree);
      N = max(D + 1, maxSize);

      im = new int[N];
      pm = new int[N];
      child = new int[N];
      parent = new int[N];
      values = new Object[N];

      for (int i = 0; i < N; i++) {
        parent[i] = (i - 1) / D;
        child[i] = i * D + 1;
        pm[i] = im[i] = -1;
      }
    }

    public int size() {
      return sz;
    }

    public boolean isEmpty() {
      return sz == 0;
    }

    public boolean contains(int ki) {
      keyInBoundsOrThrow(ki);
      return pm[ki] != -1;
    }

    public int peekMinKeyIndex() {
      isNotEmptyOrThrow();
      return im[0];
    }

    public int pollMinKeyIndex() {
      int minki = peekMinKeyIndex();
      delete(minki);
      return minki;
    }

    @SuppressWarnings("unchecked")
    public T peekMinValue() {
      isNotEmptyOrThrow();
      return (T) values[im[0]];
    }

    public T pollMinValue() {
      T minValue = peekMinValue();
      delete(peekMinKeyIndex());
      return minValue;
    }

    public void insert(int ki, T value) {
      if (contains(ki)) throw new IllegalArgumentException("index already exists; received: " + ki);
      valueNotNullOrThrow(value);
      pm[ki] = sz;
      im[sz] = ki;
      values[ki] = value;
      swim(sz++);
    }

    @SuppressWarnings("unchecked")
    public T valueOf(int ki) {
      keyExistsOrThrow(ki);
      return (T) values[ki];
    }

    @SuppressWarnings("unchecked")
    public T delete(int ki) {
      keyExistsOrThrow(ki);
      final int i = pm[ki];
      swap(i, --sz);
      sink(i);
      swim(i);
      T value = (T) values[ki];
      values[ki] = null;
      pm[ki] = -1;
      im[sz] = -1;
      return value;
    }

    @SuppressWarnings("unchecked")
    public T update(int ki, T value) {
      keyExistsAndValueNotNullOrThrow(ki, value);
      final int i = pm[ki];
      T oldValue = (T) values[ki];
      values[ki] = value;
      sink(i);
      swim(i);
      return oldValue;
    }


    public void decrease(int ki, T value) {
      keyExistsAndValueNotNullOrThrow(ki, value);
      if (less(value, values[ki])) {
        values[ki] = value;
        swim(pm[ki]);
      }
    }


    public void increase(int ki, T value) {
      keyExistsAndValueNotNullOrThrow(ki, value);
      if (less(values[ki], value)) {
        values[ki] = value;
        sink(pm[ki]);
      }
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
      int index = -1, from = child[i], to = min(sz, from + D);
      for (int j = from; j < to; j++) if (less(j, i)) index = i = j;
      return index;
    }

    private void swap(int i, int j) {
      pm[im[j]] = i;
      pm[im[i]] = j;
      int tmp = im[i];
      im[i] = im[j];
      im[j] = tmp;
    }


    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
      return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
    }

    @SuppressWarnings("unchecked")
    private boolean less(Object obj1, Object obj2) {
      return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
    }

    @Override
    public String toString() {
      List<Integer> lst = new ArrayList<>(sz);
      for (int i = 0; i < sz; i++) lst.add(im[i]);
      return lst.toString();
    }

    

    private void isNotEmptyOrThrow() {
      if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    }

    private void keyExistsAndValueNotNullOrThrow(int ki, Object value) {
      keyExistsOrThrow(ki);
      valueNotNullOrThrow(value);
    }

    private void keyExistsOrThrow(int ki) {
      if (!contains(ki)) throw new NoSuchElementException("Index does not exist; received: " + ki);
    }

    private void valueNotNullOrThrow(Object value) {
      if (value == null) throw new IllegalArgumentException("value cannot be null");
    }

    private void keyInBoundsOrThrow(int ki) {
      if (ki < 0 || ki >= N)
        throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
    }
  }
}
