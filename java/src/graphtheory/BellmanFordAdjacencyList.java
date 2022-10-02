
package graphtheory;

import java.util.*;

public class BellmanFordAdjacencyList {


  public static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      this.to = to;
      this.from = from;
      this.cost = cost;
    }
  }


  @SuppressWarnings("unchecked")
  public static List<Edge>[] createGraph(final int V) {
    List<Edge>[] graph = new List[V];
    for (int i = 0; i < V; i++) graph[i] = new ArrayList<>();
    return graph;
  }


  public static void addEdge(List<Edge>[] graph, int from, int to, double cost) {
    graph[from].add(new Edge(from, to, cost));
  }

  
  public static double[] bellmanFord(List<Edge>[] graph, int V, int start) {



    double[] dist = new double[V];
    java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;


    for (int i = 0; i < V - 1; i++)
      for (List<Edge> edges : graph)
        for (Edge edge : edges)
          if (dist[edge.from] + edge.cost < dist[edge.to])
            dist[edge.to] = dist[edge.from] + edge.cost;




    for (int i = 0; i < V - 1; i++)
      for (List<Edge> edges : graph)
        for (Edge edge : edges)
          if (dist[edge.from] + edge.cost < dist[edge.to]) dist[edge.to] = Double.NEGATIVE_INFINITY;


    return dist;
  }

  public static void main(String[] args) {

    int E = 10, V = 9, start = 0;
    List<Edge>[] graph = createGraph(V);
    addEdge(graph, 0, 1, 1);
    addEdge(graph, 1, 2, 1);
    addEdge(graph, 2, 4, 1);
    addEdge(graph, 4, 3, -3);
    addEdge(graph, 3, 2, 1);
    addEdge(graph, 1, 5, 4);
    addEdge(graph, 1, 6, 4);
    addEdge(graph, 5, 6, 5);
    addEdge(graph, 6, 7, 4);
    addEdge(graph, 5, 7, 3);
    double[] d = bellmanFord(graph, V, start);

    for (int i = 0; i < V; i++)
      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i]);












  }
}
