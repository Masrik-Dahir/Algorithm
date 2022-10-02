

package graphtheory.analysis;

import static java.lang.Math.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PrimsGraphRepresentationAnaylsis {

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

  private static class PrimsAdjList {


    private final int n;
    private final List<List<Edge>> graph;


    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Edge> ipq;


    private long minCostSum;
    private Edge[] mstEdges;

    public PrimsAdjList(List<List<Edge>> graph) {
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

    


    static List<List<Edge>> createEmptyGraph(int n) {
      List<List<Edge>> g = new ArrayList<>();
      for (int i = 0; i < n; i++) g.add(new ArrayList<>());
      return g;
    }

    static void addDirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
      g.get(from).add(new Edge(from, to, cost));
    }

    static void addUndirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
      addDirectedEdge(g, from, to, cost);
      addDirectedEdge(g, to, from, cost);
    }
  }

  private static class PrimsAdjMatrix {


    private final int n;
    private final Integer[][] graph;


    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Integer> ipq;


    private long minCostSum;
    private Edge[] mstEdges;

    public PrimsAdjMatrix(Integer[][] graph) {
      if (graph == null || graph.length == 0 || graph[0].length != graph.length)
        throw new IllegalArgumentException();
      this.n = graph.length;
      this.graph = graph;
    }



    public Edge[] getMst() {

      return null;
    }

    public Long getMstCost() {
      solve();
      return mstExists ? minCostSum : null;
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
      visited[currentNodeIndex] = true;

      for (int to = 0; to < n; to++) {
        Integer cost = graph[currentNodeIndex][to];

        if (cost == null) continue;


        if (visited[to]) continue;

        if (ipq.contains(to)) {

          ipq.decrease(to, cost);
        } else {

          ipq.insert(to, cost);
        }
      }
    }


    private void solve() {
      if (solved) return;
      solved = true;

      int m = n - 1, edgeCount = 0;
      visited = new boolean[n];




      int degree = (int) Math.ceil(Math.log(n) / Math.log(2));
      ipq = new MinIndexedDHeap<>(max(2, degree), n);


      relaxEdgesAtNode(0);

      while (!ipq.isEmpty() && edgeCount != m) {
        int destNodeIndex = ipq.peekMinKeyIndex();
        int edgeCost = ipq.pollMinValue();

        minCostSum += edgeCost;
        edgeCount++;

        relaxEdgesAtNode(destNodeIndex);
      }


      mstExists = (edgeCount == m);
    }

    


    static Integer[][] createEmptyGraph(int n) {
      return new Integer[n][n];
    }

    static void addDirectedEdge(Integer[][] g, int from, int to, int cost) {
      g[from][to] = cost;
    }

    static void addUndirectedEdge(Integer[][] g, int from, int to, int cost) {
      addDirectedEdge(g, from, to, cost);
      addDirectedEdge(g, to, from, cost);
    }
  }

  

  public static void main(String[] args) throws InterruptedException {
    densityTest();
  }

  static Random random = new Random();

  private static void densityTest() throws InterruptedException {
    String rows = "", header = "edge density percentage, adj list, adj matrix\n";
    for (int percentage = 5; percentage <= 100; percentage += 5) {


      System.gc();
      TimeUnit.SECONDS.sleep(2);

      int n = 5000;
      List<List<Edge>> g1 = PrimsAdjList.createEmptyGraph(n);
      Integer[][] g2 = PrimsAdjMatrix.createEmptyGraph(n);

      int numEdgesIncluded = 0;
      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          int r = Math.abs(random.nextInt()) % 100;
          if (r >= percentage) continue;
          PrimsAdjList.addUndirectedEdge(g1, i, j, r);
          PrimsAdjMatrix.addUndirectedEdge(g2, i, j, r);
          numEdgesIncluded += 2;
        }
      }

      PrimsAdjList adjListSolver = new PrimsAdjList(g1);
      PrimsAdjMatrix matrixSolver = new PrimsAdjMatrix(g2);

      System.out.println(
          "\nPercentage full: ~" + percentage + "%, Edges included: " + numEdgesIncluded);

      Instant start = Instant.now();
      Long listCost = adjListSolver.getMstCost();
      Instant end = Instant.now();
      long listTimeMs = Duration.between(start, end).toMillis();
      System.out.println("List:   " + listTimeMs + " millis");

      start = Instant.now();
      Long matrixCost = matrixSolver.getMstCost();
      end = Instant.now();
      long matrixTimeMs = Duration.between(start, end).toMillis();
      System.out.println("Matrix: " + matrixTimeMs + " millis");

      if (listCost != null && listCost.longValue() != matrixCost.longValue()) {
        System.out.println("Oh dear. " + listCost + " != " + matrixCost);
      }

      rows += String.format("%d%%,%d,%d\n", percentage, listTimeMs, matrixTimeMs);
    }
    System.out.println("CSV printout:\n\n" + header + rows);
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
