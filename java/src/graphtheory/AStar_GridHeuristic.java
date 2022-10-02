
package graphtheory;

import java.util.*;

public class AStar_GridHeuristic {



  static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      if (cost < 0) throw new IllegalArgumentException("No negative edge weights");
      this.from = from;
      this.to = to;
      this.cost = cost;
    }

    @Override
    public String toString() {
      return from + ":" + to;
    }
  }


  private static class Node implements Comparable<Node> {

    int id;
    double f, g, h;

    private static final double EPS = 1e-7;

    public Node(int nodeID, double gg, double hh) {
      id = nodeID;
      g = gg;
      h = hh;
      f = g + h;
    }


    @Override
    public int compareTo(Node other) {
      if (Math.abs(f - other.f) < EPS) {
        if (Math.abs(h - other.h) < EPS) return 0;
        return (h - other.h) > 0 ? +1 : -1;
      }
      return (f - other.f) > 0 ? +1 : -1;
    }
  }





  public static double astar(
      double[] X, double[] Y, Map<Integer, List<Edge>> graph, int start, int end, int n) {








    double[] G = new double[n];
    double[] F = new double[n];
    Arrays.fill(G, Double.POSITIVE_INFINITY);
    Arrays.fill(F, Double.POSITIVE_INFINITY);
    G[start] = 0;
    F[start] = heuristic(X, Y, start, end);

    Set<Integer> openSet = new HashSet<>();
    Set<Integer> closedSet = new HashSet<>();


    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(start, G[start], F[start]));
    openSet.add(start);

    while (!pq.isEmpty()) {

      Node node = pq.poll();
      openSet.remove(node.id);
      closedSet.add(node.id);

      if (node.id == end) return G[end];

      List<Edge> edges = graph.get(node.id);
      if (edges != null) {
        for (int i = 0; i < edges.size(); i++) {

          Edge edge = edges.get(i);
          if (closedSet.contains(edge.to)) continue;

          double g = node.g + edge.cost;
          double h = heuristic(X, Y, edge.to, end);

          if (g < G[edge.to] || !openSet.contains(edge.to)) {

            G[edge.to] = g;


            if (!openSet.contains(edge.to)) {
              pq.offer(new Node(edge.to, g, h));
              openSet.add(edge.to);
            }
          }
        }
      }
    }


    return Double.POSITIVE_INFINITY;
  }


  static double heuristic(double[] X, double[] Y, int at, int end) {




    return 0;
  }


  public static void main(String[] args) {

    Random RANDOM = new Random();

    int n = 20 * 20;
    Map<Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());

    double[] X = new double[n];
    double[] Y = new double[n];
    int N = (int) Math.sqrt(n);

    int connections = (n * (n - 1));
    int[] locations = new int[connections * 2];

    boolean[][] m = new boolean[n][n];

    for (int k = 0; k < connections; k++) {

      int i = RANDOM.nextInt(N);
      int j = RANDOM.nextInt(N);
      int ii = RANDOM.nextInt(N);
      int jj = RANDOM.nextInt(N);

      int node1 = i * N + j;
      int node2 = ii * N + jj;
      if (m[node1][node2]) continue;

      locations[2 * k] = node1;
      locations[2 * k + 1] = node2;

      X[node1] = i;
      Y[node1] = j;
      X[node2] = ii;
      Y[node2] = jj;

      addEdge(graph, node1, node2, i, j, ii, jj);
      m[node1][node2] = true;
    }

    System.out.println(graph);

    for (int i = 0; i < 10 * n; i++) {

      int s = locations[RANDOM.nextInt(2 * connections)];
      int e = locations[RANDOM.nextInt(2 * connections)];

      double d = dijkstra(graph, s, e, n);
      double a = astar(X, Y, graph, s, e, n);

      if (a != d) {
        System.out.println("ERROR: " + a + " " + d + " " + s + " " + e);

        break;
      }
      System.out.println();
    }
  }

  static void addEdge(
      Map<Integer, List<Edge>> graph, int f, int t, int fx, int fy, int tx, int ty) {
    double dx = Math.abs(fx - tx);
    double dy = Math.abs(fy - ty);
    graph.get(f).add(new Edge(f, t, dx + dy));
  }


  private static class DNode implements Comparable<DNode> {
    int id;
    double value;
    private static final double EPS = 1e-7;

    public DNode(int nodeID, double nodeValue) {
      id = nodeID;
      value = nodeValue;
    }

    @Override
    public int compareTo(DNode other) {
      if (Math.abs(value - other.value) < EPS) return 0;
      return (value - other.value) > 0 ? +1 : -1;
    }
  }





  public static double dijkstra(Map<Integer, List<Edge>> graph, int start, int end, int n) {


    double[] dists = new double[n];
    Arrays.fill(dists, Double.POSITIVE_INFINITY);
    dists[start] = 0;


    PriorityQueue<DNode> pq = new PriorityQueue<>();
    pq.offer(new DNode(start, 0.0));


    boolean[] visited = new boolean[n];








    while (!pq.isEmpty()) {

      DNode node = pq.poll();
      visited[node.id] = true;



      if (node.value > dists[node.id]) continue;

      List<Edge> edges = graph.get(node.id);
      if (edges != null) {
        for (int i = 0; i < edges.size(); i++) {
          Edge edge = edges.get(i);



          if (visited[edge.to]) continue;


          double newDist = dists[edge.from] + edge.cost;
          if (newDist < dists[edge.to]) {

            dists[edge.to] = newDist;
            pq.offer(new DNode(edge.to, dists[edge.to]));
          }
        }
      }




      if (node.id == end) {



        return dists[end];
      }
    }


    return Double.POSITIVE_INFINITY;
  }
}
