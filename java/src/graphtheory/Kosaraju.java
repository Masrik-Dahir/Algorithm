
package graphtheory;

import java.util.*;

public class Kosaraju {

  private int n;
  private int sccCount;
  private boolean solved;
  private int[] sccs;
  private boolean[] visited;


  private List<Integer> postOrderTraversal;

  private List<List<Integer>> graph;
  private List<List<Integer>> transposeGraph;

  public Kosaraju(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    this.graph = graph;
    n = graph.size();
  }


  public int sccCount() {
    if (!solved) solve();
    return sccCount;
  }



  public int[] getSccs() {
    if (!solved) solve();
    return sccs;
  }

  private void solve() {
    sccCount = 0;
    sccs = new int[n];
    visited = new boolean[n];
    postOrderTraversal = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      dfs1(i);
    }

    Arrays.fill(visited, false);
    createTransposeGraph();



    Collections.reverse(postOrderTraversal);

    for (int node : postOrderTraversal) {
      if (!visited[node]) {
        dfs2(node);
        sccCount++;
      }
    }

    solved = true;
  }


  private void dfs1(int from) {
    if (visited[from]) {
      return;
    }
    visited[from] = true;
    for (int to : graph.get(from)) {
      dfs1(to);
    }
    postOrderTraversal.add(from);
  }


  private void dfs2(int from) {
    if (visited[from]) {
      return;
    }
    visited[from] = true;
    for (int to : transposeGraph.get(from)) {
      dfs2(to);
    }
    sccs[from] = sccCount;
  }

  private void createTransposeGraph() {
    transposeGraph = createGraph(n);
    for (int u = 0; u < n; u++) {
      for (int v : graph.get(u)) {
        addEdge(transposeGraph, v, u);
      }
    }
  }


  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }


  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  public static void main(String[] args) {
    example1();




  }

  private static void exampleFromCp4() {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 1);
    addEdge(graph, 3, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 7);
    addEdge(graph, 6, 4);
    addEdge(graph, 7, 6);

    runKosaraju(graph);
  }

  private static void example4() {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);


    addEdge(graph, 0, 2);
    addEdge(graph, 0, 3);
    addEdge(graph, 0, 5);
    addEdge(graph, 1, 4);
    addEdge(graph, 1, 7);
    addEdge(graph, 2, 1);
    addEdge(graph, 3, 0);
    addEdge(graph, 3, 4);
    addEdge(graph, 4, 2);
    addEdge(graph, 5, 7);
    addEdge(graph, 6, 5);
    addEdge(graph, 7, 6);

    runKosaraju(graph);
  }

  private static void example3() {
    int n = 6;
    List<List<Integer>> graph = createGraph(n);


    addEdge(graph, 0, 2);
    addEdge(graph, 0, 5);
    addEdge(graph, 1, 0);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 4);
    addEdge(graph, 3, 1);
    addEdge(graph, 3, 5);
    addEdge(graph, 4, 0);

    runKosaraju(graph);
  }

  private static void example2() {


    int n = 10;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);
    addEdge(graph, 1, 6);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 4);
    addEdge(graph, 3, 7);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 9);
    addEdge(graph, 6, 1);
    addEdge(graph, 7, 2);
    addEdge(graph, 8, 4);
    addEdge(graph, 9, 8);

    runKosaraju(graph);
  }

  private static void example1() {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 6, 0);
    addEdge(graph, 6, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 6, 4);
    addEdge(graph, 2, 0);
    addEdge(graph, 0, 1);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 5);
    addEdge(graph, 1, 2);
    addEdge(graph, 7, 3);
    addEdge(graph, 5, 0);






    runKosaraju(graph);
  }

  private static void runKosaraju(List<List<Integer>> graph) {
    int n = graph.size();
    Kosaraju solver = new Kosaraju(graph);
    int[] sccs = solver.getSccs();
    Map<Integer, List<Integer>> multimap = new HashMap<>();
    for (int i = 0; i < n; i++) {
      if (!multimap.containsKey(sccs[i])) multimap.put(sccs[i], new ArrayList<>());
      multimap.get(sccs[i]).add(i);
    }

    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
    for (List<Integer> scc : multimap.values()) {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
    }
  }
}
