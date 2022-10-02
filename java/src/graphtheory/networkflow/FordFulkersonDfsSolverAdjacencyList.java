
package graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.List;

public class FordFulkersonDfsSolverAdjacencyList extends NetworkFlowSolverBase {

  
  public FordFulkersonDfsSolverAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }



  @Override
  public void solve() {


    for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
      markAllNodesAsUnvisited();
      maxFlow += f;
    }


    for (int i = 0; i < n; i++) if (visited(i)) minCut[i] = true;
  }

  private long dfs(int node, long flow) {

    if (node == t) return flow;

    List<Edge> edges = graph[node];
    visit(node);

    for (Edge edge : edges) {
      long rcap = edge.remainingCapacity();
      if (rcap > 0 && !visited(edge.to)) {
        long bottleNeck = dfs(edge.to, min(flow, rcap));


        if (bottleNeck > 0) {
          edge.augment(bottleNeck);
          return bottleNeck;
        }
      }
    }
    return 0;
  }

  

  public static void main(String[] args) {


    exampleFromSlides2();
  }

  private static void exampleFromSlides2() {
    int n = 12;
    int s = n - 2;
    int t = n - 1;

    FordFulkersonDfsSolverAdjacencyList solver;
    solver = new FordFulkersonDfsSolverAdjacencyList(n, s, t);

    solver.addEdge(s, 1, 2);
    solver.addEdge(s, 2, 1);
    solver.addEdge(s, 0, 7);

    solver.addEdge(0, 3, 2);
    solver.addEdge(0, 4, 4);

    solver.addEdge(1, 4, 5);
    solver.addEdge(1, 5, 6);

    solver.addEdge(2, 3, 4);
    solver.addEdge(2, 7, 8);

    solver.addEdge(3, 6, 7);
    solver.addEdge(3, 7, 1);

    solver.addEdge(4, 5, 8);
    solver.addEdge(4, 8, 3);

    solver.addEdge(5, 8, 3);

    solver.addEdge(6, t, 1);
    solver.addEdge(7, t, 3);
    solver.addEdge(8, t, 4);

    System.out.println(solver.getMaxFlow());

    List<Edge>[] g = solver.getGraph();
    for (List<Edge> edges : g) {
      for (Edge e : edges) {
        if (e.to == s || e.from == t) continue;
        if (e.from == s || e.to == t || e.from < e.to) System.out.println(e.toString(s, t));

      }
    }
  }

  private static void exampleFromSlides() {
    int n = 6;
    int s = n - 2;
    int t = n - 1;

    FordFulkersonDfsSolverAdjacencyList solver;
    solver = new FordFulkersonDfsSolverAdjacencyList(n, s, t);

    solver.addEdge(s, 1, 10);
    solver.addEdge(1, 3, 15);
    solver.addEdge(3, 0, 6);
    solver.addEdge(0, 2, 25);
    solver.addEdge(2, t, 10);

    solver.addEdge(s, 0, 10);
    solver.addEdge(3, t, 10);

    System.out.println(solver.getMaxFlow());

    List<Edge>[] g = solver.getGraph();
    for (List<Edge> edges : g) {
      for (Edge e : edges) {
        System.out.println(e.toString(s, t));

      }
    }
  }



  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    FordFulkersonDfsSolverAdjacencyList solver;
    solver = new FordFulkersonDfsSolverAdjacencyList(n, s, t);


    solver.addEdge(s, 0, 10);
    solver.addEdge(s, 1, 10);


    solver.addEdge(2, t, 10);
    solver.addEdge(3, t, 10);


    solver.addEdge(0, 1, 2);
    solver.addEdge(0, 2, 4);
    solver.addEdge(0, 3, 8);
    solver.addEdge(1, 3, 9);
    solver.addEdge(3, 2, 6);

    System.out.println(solver.getMaxFlow());
  }
}
