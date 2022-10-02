
package graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.*;

public class EdmondsKarpAdjacencyList extends NetworkFlowSolverBase {

  
  public EdmondsKarpAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }


  @Override
  public void solve() {
    long flow;
    do {
      markAllNodesAsUnvisited();
      flow = bfs();
      maxFlow += flow;
    } while (flow != 0);

    for (int i = 0; i < n; i++) if (visited(i)) minCut[i] = true;
  }

  private long bfs() {
    Edge[] prev = new Edge[n];


    Queue<Integer> q = new ArrayDeque<>(n);
    visit(s);
    q.offer(s);


    while (!q.isEmpty()) {
      int node = q.poll();
      if (node == t) break;

      for (Edge edge : graph[node]) {
        long cap = edge.remainingCapacity();
        if (cap > 0 && !visited(edge.to)) {
          visit(edge.to);
          prev[edge.to] = edge;
          q.offer(edge.to);
        }
      }
    }


    if (prev[t] == null) return 0;

    long bottleNeck = Long.MAX_VALUE;


    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
      bottleNeck = min(bottleNeck, edge.remainingCapacity());


    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) edge.augment(bottleNeck);


    return bottleNeck;
  }

  

  public static void main(String[] args) {
    testSmallFlowGraph();
  }



  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    EdmondsKarpAdjacencyList solver;
    solver = new EdmondsKarpAdjacencyList(n, s, t);


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
