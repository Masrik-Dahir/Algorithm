
package graphtheory.treealgorithms;

import java.util.*;

public class TreeCenterLongestPathImpl {

  private static class DfsResult {

    int distance;


    int index;

    public DfsResult(int distance, int index) {
      this.distance = distance;
      this.index = index;
    }
  }

  private static DfsResult dfs(
      List<List<Integer>> graph, boolean[] visited, int[] prev, int at, int parent) {


    if (visited[at]) return new DfsResult(0, parent);


    visited[at] = true;


    prev[at] = parent;

    int bestDist = 0, index = -1;
    List<Integer> edges = graph.get(at);

    for (int to : edges) {
      DfsResult result = dfs(graph, visited, prev, to, at);
      int dist = result.distance + 1;
      if (dist > bestDist) {
        bestDist = dist;
        index = result.index;
      }
    }

    return new DfsResult(bestDist, index);
  }

  public static List<Integer> findTreeCenters(List<List<Integer>> graph) {
    List<Integer> centers = new ArrayList<>();
    if (graph == null) return centers;

    int n = graph.size();
    boolean[] visited = new boolean[n];
    int[] prev = new int[n];


    DfsResult result = dfs(graph, visited, prev, 0, -1);
    int furthestNode1 = result.index;


    if (furthestNode1 == -1) {
      centers.add(0);
      return centers;
    }


    Arrays.fill(visited, false);
    Arrays.fill(prev, 0);

    result = dfs(graph, visited, prev, furthestNode1, -1);
    int furthestNode2 = result.index;

    List<Integer> path = new LinkedList<>();
    for (int i = furthestNode2; i != -1; i = prev[i]) {
      path.add(i);
    }

    if (path.size() % 2 == 0) {
      centers.add(path.get(path.size() / 2 - 1));
    }
    centers.add(path.get(path.size() / 2));
    return centers;
  }

  


  public static List<List<Integer>> createEmptyTree(int n) {
    List<List<Integer>> tree = new ArrayList<>(n);
    for (int i = 0; i < n; i++) tree.add(new LinkedList<>());
    return tree;
  }

  public static void addUndirectedEdge(List<List<Integer>> tree, int from, int to) {
    tree.get(from).add(to);
    tree.get(to).add(from);
  }

  public static void main(String[] args) {

    List<List<Integer>> graph = createEmptyTree(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);


    System.out.println(findTreeCenters(graph));


    List<List<Integer>> graph2 = createEmptyTree(1);
    System.out.println(findTreeCenters(graph2));


    List<List<Integer>> graph3 = createEmptyTree(2);
    addUndirectedEdge(graph3, 0, 1);
    System.out.println(findTreeCenters(graph3));


    List<List<Integer>> graph4 = createEmptyTree(3);
    addUndirectedEdge(graph4, 0, 1);
    addUndirectedEdge(graph4, 1, 2);
    System.out.println(findTreeCenters(graph4));


    List<List<Integer>> graph5 = createEmptyTree(4);
    addUndirectedEdge(graph5, 0, 1);
    addUndirectedEdge(graph5, 1, 2);
    addUndirectedEdge(graph5, 2, 3);
    System.out.println(findTreeCenters(graph5));


    List<List<Integer>> graph6 = createEmptyTree(7);
    addUndirectedEdge(graph6, 0, 1);
    addUndirectedEdge(graph6, 1, 2);
    addUndirectedEdge(graph6, 2, 3);
    addUndirectedEdge(graph6, 3, 4);
    addUndirectedEdge(graph6, 4, 5);
    addUndirectedEdge(graph6, 4, 6);
    System.out.println(findTreeCenters(graph6));
  }
}
