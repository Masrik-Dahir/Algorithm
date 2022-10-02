
package graphtheory;

import java.util.*;

public class ConnectedComponentsAdjacencyList {

  static class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  static int countConnectedComponents(Map<Integer, List<Edge>> graph, int n) {

    UnionFind uf = new UnionFind(n);

    for (int i = 0; i < n; i++) {
      List<Edge> edges = graph.get(i);
      if (edges != null) {
        for (Edge edge : edges) {
          uf.unify(edge.from, edge.to);
        }
      }
    }

    return uf.components();
  }


  public static void main(String[] args) {

    final int numNodes = 7;
    Map<Integer, List<Edge>> graph = new HashMap<>();



    addUndirectedEdge(graph, 0, 1, 1);
    addUndirectedEdge(graph, 1, 2, 1);
    addUndirectedEdge(graph, 2, 0, 1);
    addUndirectedEdge(graph, 3, 4, 1);
    addUndirectedEdge(graph, 5, 5, 1);

    int components = countConnectedComponents(graph, numNodes);
    System.out.printf("Number of components: %d\n", components);
  }


  private static void addUndirectedEdge(
      Map<Integer, List<Edge>> graph, int from, int to, int cost) {
    List<Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList<Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to, cost));
    list.add(new Edge(to, from, cost));
  }
}


class UnionFind {


  private int size;


  private int[] sz;


  private int[] id;


  private int numComponents;

  public UnionFind(int size) {

    if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

    this.size = numComponents = size;
    sz = new int[size];
    id = new int[size];

    for (int i = 0; i < size; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }


  public int find(int p) {


    int root = p;
    while (root != id[root]) root = id[root];




    while (p != root) {
      int next = id[p];
      id[p] = root;
      p = next;
    }

    return root;
  }



  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }


  public int componentSize(int p) {
    return sz[find(p)];
  }


  public int size() {
    return size;
  }


  public int components() {
    return numComponents;
  }


  public void unify(int p, int q) {

    int root1 = find(p);
    int root2 = find(q);


    if (root1 == root2) return;


    if (sz[root1] < sz[root2]) {
      sz[root2] += sz[root1];
      id[root1] = root2;
    } else {
      sz[root1] += sz[root2];
      id[root2] = root1;
    }



    numComponents--;
  }
}
