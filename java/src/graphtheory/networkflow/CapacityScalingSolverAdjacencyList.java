
package graphtheory.networkflow;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;

public class CapacityScalingSolverAdjacencyList extends NetworkFlowSolverBase {

  private long delta;

  
  public CapacityScalingSolverAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }

  
  @Override
  public void addEdge(int from, int to, long capacity) {
    super.addEdge(from, to, capacity);
    delta = max(delta, capacity);
  }



  @Override
  public void solve() {


    delta = Long.highestOneBit(delta);




    for (long f = 0; delta > 0; delta /= 2) {
      do {
        markAllNodesAsUnvisited();
        f = dfs(s, INF);
        maxFlow += f;
      } while (f != 0);
    }


    for (int i = 0; i < n; i++) if (visited(i)) minCut[i] = true;
  }

  private long dfs(int node, long flow) {

    if (node == t) return flow;

    List<Edge> edges = graph[node];
    visit(node);

    for (Edge edge : edges) {
      long cap = edge.remainingCapacity();
      if (cap >= delta && !visited(edge.to)) {

        long bottleNeck = dfs(edge.to, min(flow, cap));


        if (bottleNeck > 0) {
          edge.augment(bottleNeck);
          return bottleNeck;
        }
      }
    }
    return 0;
  }

  

  public static void main(String[] args) {
    testSmallFlowGraph();
    testExampleFromMySlides();
  }



  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    CapacityScalingSolverAdjacencyList solver;
    solver = new CapacityScalingSolverAdjacencyList(n, s, t);


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

  private static void testExampleFromMySlides() {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    CapacityScalingSolverAdjacencyList solver;
    solver = new CapacityScalingSolverAdjacencyList(n, s, t);


    solver.addEdge(s, 0, 6);
    solver.addEdge(s, 1, 14);


    solver.addEdge(2, t, 11);
    solver.addEdge(3, t, 12);


    solver.addEdge(0, 1, 1);
    solver.addEdge(2, 3, 1);
    solver.addEdge(0, 2, 5);
    solver.addEdge(1, 2, 7);
    solver.addEdge(1, 3, 10);

    System.out.println(solver.getMaxFlow());
  }
}
