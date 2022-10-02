
package graphtheory.examples;

import static java.lang.Math.*;

import java.util.*;

public class EagerPrimsExample {

  

  public static void main(String[] args) {
    int n = 7;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 9);
    addUndirectedEdge(g, 0, 2, 0);
    addUndirectedEdge(g, 0, 3, 5);
    addUndirectedEdge(g, 0, 5, 7);
    addUndirectedEdge(g, 1, 3, -2);
    addUndirectedEdge(g, 1, 4, 3);
    addUndirectedEdge(g, 1, 6, 4);
    addUndirectedEdge(g, 2, 5, 6);
    addUndirectedEdge(g, 3, 5, 2);
    addUndirectedEdge(g, 3, 6, 3);
    addUndirectedEdge(g, 4, 6, 6);
    addUndirectedEdge(g, 5, 6, 1);

    MinimumSpanningTreeSolver solver = new MinimumSpanningTreeSolver(g);

    if (!solver.mstExists()) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + solver.getMstCost());
      System.out.println("MST edges:");
      for (Edge e : solver.getMst()) {
        System.out.println(String.format("  (%d, %d, %d)", e.from, e.to, e.cost));
      }
    }








  }

  


  private static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++) g.add(new ArrayList<>());
    return g;
  }

  private static void addDirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
    g.get(from).add(new Edge(from, to, cost));
  }

  private static void addUndirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
    addDirectedEdge(g, from, to, cost);
    addDirectedEdge(g, to, from, cost);
  }


  private static class Edge implements Comparable<Edge> {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }

    @Override
    public int compareTo(Edge other) {
      return cost - other.cost;
    }
  }


  private static class MinimumSpanningTreeSolver {


    private final int n;
    private final List<List<Edge>> graph;


    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Edge> ipq;


    private long minCostSum;
    private Edge[] mstEdges;

    public MinimumSpanningTreeSolver(List<List<Edge>> graph) {
      if (graph == null || graph.isEmpty()) throw new IllegalArgumentException();
      this.n = graph.size();
      this.graph = graph;
    }



    public Edge[] getMst() {
      solve();
      return mstExists ? mstEdges : null;
    }

    public Long getMstCost() {
      solve();
      return mstExists ? minCostSum : null;
    }

    public boolean mstExists() {
      solve();
      return mstExists;
    }


    private void solve() {
      if (solved) return;
      solved = true;

      int m = n - 1, edgeCount = 0;
      visited = new boolean[n];
      mstEdges = new Edge[m];




      int degree = (int) Math.ceil(Math.log(n) / Math.log(2));
      ipq = new MinIndexedDHeap<>(max(2, degree), n);


      relaxEdgesAtNode(0);

      while (!ipq.isEmpty() && edgeCount != m) {
        int destNodeIndex = ipq.peekMinKeyIndex();
        Edge edge = ipq.pollMinValue();

        mstEdges[edgeCount++] = edge;
        minCostSum += edge.cost;

        relaxEdgesAtNode(destNodeIndex);
      }


      mstExists = (edgeCount == m);
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
      visited[currentNodeIndex] = true;


      List<Edge> edges = graph.get(currentNodeIndex);

      for (Edge edge : edges) {
        int destNodeIndex = edge.to;


        if (visited[destNodeIndex]) continue;

        if (ipq.contains(destNodeIndex)) {

          ipq.decrease(destNodeIndex, edge);
        } else {

          ipq.insert(destNodeIndex, edge);
        }
      }
    }
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

    



    public boolean isMinHeap() {
      return isMinHeap(0);
    }

    private boolean isMinHeap(int i) {
      int from = child[i], to = min(sz, from + D);
      for (int j = from; j < to; j++) {
        if (!less(i, j)) return false;
        if (!isMinHeap(j)) return false;
      }
      return true;
    }
  }
}
