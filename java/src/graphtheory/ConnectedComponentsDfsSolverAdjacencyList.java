
package graphtheory;

import java.util.*;

public class ConnectedComponentsDfsSolverAdjacencyList {

  private final int n;

  private int componentCount;
  private int[] components;
  private boolean solved;
  private boolean[] visited;
  private List<List<Integer>> graph;


  public ConnectedComponentsDfsSolverAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new NullPointerException();
    this.n = graph.size();
    this.graph = graph;
  }

  public int[] getComponents() {
    solve();
    return components;
  }

  public int countComponents() {
    solve();
    return componentCount;
  }

  public void solve() {
    if (solved) return;

    visited = new boolean[n];
    components = new int[n];
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        componentCount++;
        dfs(i);
      }
    }

    solved = true;
  }

  private void dfs(int at) {
    visited[at] = true;
    components[at] = componentCount;
    for (int to : graph.get(at)) if (!visited[to]) dfs(to);
  }



  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  public static void main(String[] args) {

    final int n = 11;
    List<List<Integer>> graph = createGraph(n);



    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 7);
    addUndirectedEdge(graph, 7, 0);
    addUndirectedEdge(graph, 2, 5);
    addUndirectedEdge(graph, 4, 8);
    addUndirectedEdge(graph, 3, 6);
    addUndirectedEdge(graph, 6, 9);

    ConnectedComponentsDfsSolverAdjacencyList solver;
    solver = new ConnectedComponentsDfsSolverAdjacencyList(graph);
    int count = solver.countComponents();
    System.out.printf("Number of components: %d\n", count);

    int[] components = solver.getComponents();
    for (int i = 0; i < n; i++)
      System.out.printf("Node %d is part of component %d\n", i, components[i]);














  }
}
