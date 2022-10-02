
package utils.graphutils;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

  
  public static List<List<Integer>> createEmptyAdjacencyList(int n) {
    if (n < 0) throw new IllegalArgumentException("n cannot be negative; received: " + n);
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  
  public static void addDirectedEdge(List<List<Integer>> graph, int from, int to) {
    if (graph == null) throw new IllegalArgumentException("graph cannot be null");
    int n = graph.size();
    if (from < 0 || from >= n)
      throw new IllegalArgumentException("'from' node index out of bounds; received: " + from);
    if (to < 0 || to >= n)
      throw new IllegalArgumentException("'to' node index out of bounds; received: " + to);
    graph.get(from).add(to);
  }

  
  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    addDirectedEdge(graph, from, to);
    addDirectedEdge(graph, to, from);
  }
}
