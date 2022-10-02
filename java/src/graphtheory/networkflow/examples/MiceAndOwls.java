
package graphtheory.networkflow.examples;

import static java.lang.Math.min;

import java.awt.geom.*;
import java.util.*;

public class MiceAndOwls {

  static class Mouse {
    Point2D point;

    public Mouse(int x, int y) {
      point = new Point2D.Double(x, y);
    }
  }

  static class Hole {
    int capacity;
    Point2D point;

    public Hole(int x, int y, int cap) {
      point = new Point2D.Double(x, y);
      capacity = cap;
    }
  }

  public static void main(String[] args) {
    Mouse[] mice = {
      new Mouse(1, 0),
      new Mouse(0, 1),
      new Mouse(8, 1),
      new Mouse(12, 0),
      new Mouse(12, 4),
      new Mouse(15, 5)
    };
    Hole[] holes = {new Hole(1, 1, 1), new Hole(10, 2, 2), new Hole(14, 5, 1)};
    solve(mice, holes,  3);
  }

  static void solve(Mouse[] mice, Hole[] holes, int radius) {
    final int M = mice.length;
    final int H = holes.length;


    final int N = M + H + 2;
    final int S = N - 1;
    final int T = N - 2;

    NetworkFlowSolverBase solver;
    solver = new FordFulkersonDfsSolver(N, S, T);


    for (int i = 0; i < M; i++) {
      solver.addEdge(S, i, 1);
    }


    for (int i = 0; i < M; i++) {
      Point2D mouse = mice[i].point;
      for (int j = 0; j < H; j++) {
        Point2D hole = holes[j].point;
        if (mouse.distance(hole) <= radius) {
          solver.addEdge(i, M + j, 1);
        }
      }
    }


    for (int i = 0; i < H; i++) {
      solver.addEdge(M + i, T, holes[i].capacity);
    }


    System.out.println("Number of safe mice: " + solver.getMaxFlow());
  }



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
}
