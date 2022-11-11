
package graphtheory;

import java.util.*;
import java.util.stream.Collectors;

public class BellmanFordAdjacencyMatrix {

  private int n, start;
  private boolean solved;
  private double[] dist;
  private Integer[] prev;
  private double[][] matrix;

  
  public BellmanFordAdjacencyMatrix(int start, double[][] matrix) {
    this.n = matrix.length;
    this.start = start;
    this.matrix = new double[n][n];


    for (int i = 0; i < n; i++) this.matrix[i] = matrix[i].clone();
  }

  public double[] getShortestPaths() {
    if (!solved) solve();
    return dist;
  }

  public List<Integer> reconstructShortestPath(int end) {
    if (!solved) solve();
    LinkedList<Integer> path = new LinkedList<>();
    if (dist[end] == Double.POSITIVE_INFINITY) return path;
    for (int at = end; prev[at] != null; at = prev[at]) {

      if (prev[at] == -1) return null;
      path.addFirst(at);
    }
    path.addFirst(start);
    return path;
  }

  public void solve() {
    if (solved) return;



    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;



    prev = new Integer[n];


    for (int k = 0; k < n - 1; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dist[i] + matrix[i][j] < dist[j]) {
            dist[j] = dist[i] + matrix[i][j];
            prev[j] = i;
          }




    for (int k = 0; k < n - 1; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dist[i] + matrix[i][j] < dist[j]) {
            dist[j] = Double.NEGATIVE_INFINITY;
            prev[j] = -1;
          }

    solved = true;
  }

  public static void main(String[] args) {

    int n = 9;
    double[][] graph = new double[n][n];



    for (int i = 0; i < n; i++) {
      Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
      graph[i][i] = 0;
    }

    graph[0][1] = 1;
    graph[1][2] = 1;
    graph[2][4] = 1;
    graph[4][3] = -3;
    graph[3][2] = 1;
    graph[1][5] = 4;
    graph[1][6] = 4;
    graph[5][6] = 5;
    graph[6][7] = 4;
    graph[5][7] = 3;

    int start = 0;
    BellmanFordAdjacencyMatrix solver;
    solver = new BellmanFordAdjacencyMatrix(start, graph);
    double[] d = solver.getShortestPaths();

    for (int i = 0; i < n; i++)
      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i]);











    System.out.println();

    for (int i = 0; i < n; i++) {
      String strPath;
      List<Integer> path = solver.reconstructShortestPath(i);
      if (path == null) {
        strPath = "Infinite number of shortest paths.";
      } else {
        List<String> nodes = path.stream().map(Object::toString).collect(Collectors.toList());
        strPath = String.join(" -> ", nodes);
      }
      System.out.printf("The shortest path from %d to %d is: [%s]\n", start, i, strPath);
    }










  }
}
