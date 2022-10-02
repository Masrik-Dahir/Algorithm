
package graphtheory;

public class TopologicalSortAdjacencyMatrix {



  public static int[] topologicalSort(Double[][] adj) {


    int n = adj.length;
    boolean[] visited = new boolean[n];
    int[] order = new int[n];
    int index = n - 1;


    for (int u = 0; u < n; u++) if (!visited[u]) index = visit(adj, visited, order, index, u);


    return order;
  }

  private static int visit(Double[][] adj, boolean[] visited, int[] order, int index, int u) {

    if (visited[u]) return index;
    visited[u] = true;


    for (int v = 0; v < adj.length; v++)
      if (adj[u][v] != null && !visited[v]) index = visit(adj, visited, order, index, v);


    order[index--] = u;

    return index;
  }





  public static double[] dagShortestPath(Double[][] adj, int start) {


    int n = adj.length;
    double[] dist = new double[n];
    java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0.0;


    for (int u : topologicalSort(adj))
      for (int v = 0; v < n; v++) {
        if (adj[u][v] != null) {
          double newDist = dist[u] + adj[u][v];
          if (newDist < dist[v]) dist[v] = newDist;
        }
      }


    return dist;
  }


  public static void main(String[] args) {

    final int N = 7;
    Double[][] adjMatrix = new Double[N][N];

    adjMatrix[0][1] = 3.0;
    adjMatrix[0][2] = 2.0;
    adjMatrix[0][5] = 3.0;

    adjMatrix[1][3] = 1.0;
    adjMatrix[1][2] = 6.0;

    adjMatrix[2][3] = 1.0;
    adjMatrix[2][4] = 10.0;

    adjMatrix[3][4] = 5.0;

    adjMatrix[5][4] = 7.0;

    int[] ordering = topologicalSort(adjMatrix);


    System.out.println(java.util.Arrays.toString(ordering));


    double[] dists = dagShortestPath(adjMatrix, 0);


    System.out.println(dists[4]);



    System.out.println(dists[6]);
  }
}
