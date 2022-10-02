
package graphtheory.networkflow.examples;

import static java.lang.Math.min;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EdmondsKarpExample {

  private static class Edge {
    public int from, to;
    public Edge residual;
    public long flow;
    public final long capacity;

    public Edge(int from, int to, long capacity) {
      this.from = from;
      this.to = to;
      this.capacity = capacity;
    }

    public boolean isResidual() {
      return capacity == 0;
    }

    public long remainingCapacity() {
      return capacity - flow;
    }

    public void augment(long bottleNeck) {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
    }

    public String toString(int s, int t) {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
      return String.format(
          "Edge %s -> %s | flow = %3d | capacity = %3d | is residual: %s",
          u, v, flow, capacity, isResidual());
    }
  }

  private abstract static class NetworkFlowSolverBase {


    static final long INF = Long.MAX_VALUE / 2;


    final int n, s, t;





    private int visitedToken = 1;
    private int[] visited;



    protected boolean solved;


    protected long maxFlow;


    protected List<Edge>[] graph;

    
    public NetworkFlowSolverBase(int n, int s, int t) {
      this.n = n;
      this.s = s;
      this.t = t;
      initializeEmptyFlowGraph();
      visited = new int[n];
    }


    @SuppressWarnings("unchecked")
    private void initializeEmptyFlowGraph() {
      graph = new List[n];
      for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge>();
    }

    
    public void addEdge(int from, int to, long capacity) {
      if (capacity <= 0) throw new IllegalArgumentException("Forward edge capacity <= 0");
      Edge e1 = new Edge(from, to, capacity);
      Edge e2 = new Edge(to, from, 0);
      e1.residual = e2;
      e2.residual = e1;
      graph[from].add(e1);
      graph[to].add(e2);
    }

    
    public List<Edge>[] getGraph() {
      execute();
      return graph;
    }


    public long getMaxFlow() {
      execute();
      return maxFlow;
    }


    public void visit(int i) {
      visited[i] = visitedToken;
    }


    public boolean visited(int i) {
      return visited[i] == visitedToken;
    }



    public void markAllNodesAsUnvisited() {
      visitedToken++;
    }


    private void execute() {
      if (solved) return;
      solved = true;
      solve();
    }


    public abstract void solve();
  }

  private static class EdmondsKarpSolver extends NetworkFlowSolverBase {

    
    public EdmondsKarpSolver(int n, int s, int t) {
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
    }

    private long bfs() {

      Queue<Integer> q = new ArrayDeque<>(n);
      visit(s);
      q.offer(s);


      Edge[] prev = new Edge[n];
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
  }

  

  public static void main(String[] args) {

    int n = 11;

    int s = n - 2;
    int t = n - 1;

    NetworkFlowSolverBase solver = new EdmondsKarpSolver(n, s, t);


    solver.addEdge(s, 0, 5);
    solver.addEdge(s, 1, 10);
    solver.addEdge(s, 2, 5);


    solver.addEdge(0, 3, 10);
    solver.addEdge(1, 0, 15);
    solver.addEdge(1, 4, 20);
    solver.addEdge(2, 5, 10);
    solver.addEdge(3, 4, 25);
    solver.addEdge(3, 6, 10);
    solver.addEdge(4, 2, 5);
    solver.addEdge(4, 7, 30);
    solver.addEdge(5, 7, 5);
    solver.addEdge(5, 8, 10);
    solver.addEdge(7, 3, 15);
    solver.addEdge(7, 8, 5);


    solver.addEdge(6, t, 5);
    solver.addEdge(7, t, 15);
    solver.addEdge(8, t, 10);



    System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

    List<Edge>[] resultGraph = solver.getGraph();


    for (List<Edge> edges : resultGraph) for (Edge e : edges) System.out.println(e.toString(s, t));
  }
}
