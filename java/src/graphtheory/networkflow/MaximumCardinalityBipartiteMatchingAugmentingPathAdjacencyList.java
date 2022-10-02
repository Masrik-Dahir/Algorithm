
package graphtheory.networkflow;

import java.util.*;

public class MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList {

  static final int FREE = -1;
  static int visitToken = 1;


  public static int mcbm(List<List<Integer>> graph, int n, int m) {

    int N = n + m, matches = 0;

    int[] visited = new int[n];
    int[] next = new int[N];
    Arrays.fill(next, FREE);

    for (int i = 0; i < n; i++) {
      matches += augment(graph, visited, next, i);
      visitToken++;
    }

    return matches;
  }

  private static int augment(List<List<Integer>> graph, int[] visited, int[] next, int at) {


    if (visited[at] == visitToken) return 0;
    visited[at] = visitToken;

    for (int node : graph.get(at)) {



      int oppositeNode = next[node];

      if (oppositeNode == FREE) {


        next[node] = at;
        return 1;
      }


      if (augment(graph, visited, next, oppositeNode) != 0) {



        next[node] = at;
        return 1;
      }
    }


    return 0;
  }

  private static List<List<Integer>> createEmptyGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  private static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  public static void main(String[] args) {
    int n = 8;
    List<List<Integer>> graph = createEmptyGraph(n);


    addEdge(graph, 0, 4);
    addEdge(graph, 1, 5);
    addEdge(graph, 2, 7);
    addEdge(graph, 3, 6);
    addEdge(graph, 4, 1);
    addEdge(graph, 5, 1);
    addEdge(graph, 6, 1);


    System.out.println(mcbm(graph, 4, 4));
  }
}
