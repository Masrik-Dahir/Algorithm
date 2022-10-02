
package graphtheory.networkflow.examples;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class FordFulkersonExample {

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





    protected int visitedToken = 1;
    protected int[] visited;



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


    private void execute() {
      if (solved) return;
      solved = true;
      solve();
    }


    public abstract void solve();
  }

  private static class FordFulkersonDfsSolver extends NetworkFlowSolverBase {

    
    public FordFulkersonDfsSolver(int n, int s, int t) {
      super(n, s, t);
    }



    @Override
    public void solve() {

      for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
        visitedToken++;
        maxFlow += f;
      }
    }

    private long dfs(int node, long flow) {

      if (node == t) return flow;


      visited[node] = visitedToken;

      List<Edge> edges = graph[node];
      for (Edge edge : edges) {
        if (edge.remainingCapacity() > 0 && visited[edge.to] != visitedToken) {
          long bottleNeck = dfs(edge.to, min(flow, edge.remainingCapacity()));



          if (bottleNeck > 0) {
            edge.augment(bottleNeck);
            return bottleNeck;
          }
        }
      }
      return 0;
    }
  }

  

  public static void main(String[] args) {

    int n = 12;

    int s = n - 2;
    int t = n - 1;

    NetworkFlowSolverBase solver = new FordFulkersonDfsSolver(n, s, t);


    solver.addEdge(s, 0, 10);
    solver.addEdge(s, 1, 5);
    solver.addEdge(s, 2, 10);


    solver.addEdge(0, 3, 10);
    solver.addEdge(1, 2, 10);
    solver.addEdge(2, 5, 15);
    solver.addEdge(3, 1, 2);
    solver.addEdge(3, 6, 15);
    solver.addEdge(4, 1, 15);
    solver.addEdge(4, 3, 3);
    solver.addEdge(5, 4, 4);
    solver.addEdge(5, 8, 10);
    solver.addEdge(6, 7, 10);
    solver.addEdge(7, 4, 10);
    solver.addEdge(7, 5, 7);


    solver.addEdge(6, t, 15);
    solver.addEdge(8, t, 10);



    System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

    List<Edge>[] resultGraph = solver.getGraph();


    for (List<Edge> edges : resultGraph) for (Edge e : edges) System.out.println(e.toString(s, t));
  }
}
