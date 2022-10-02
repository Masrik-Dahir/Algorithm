
package graphtheory.treealgorithms;

import java.util.*;

public class TreeDiameter {




  static int MAX_NUM_NODES = 10;

  static Integer VISITED_TOKEN = 0;
  static int[] visited = new int[MAX_NUM_NODES];


  private static int[] dfs(List<List<Integer>> graph, int at, int parent) {


    if (visited[at] == VISITED_TOKEN) return new int[] {0, parent};


    visited[at] = VISITED_TOKEN;

    int bestDist = 0, index = -1;
    List<Integer> edges = graph.get(at);

    if (edges != null) {
      for (Integer to : edges) {
        int[] tuple = dfs(graph, to, at);
        int dist = tuple[0] + 1;
        if (dist > bestDist) {
          bestDist = dist;
          index = tuple[1];
        }
      }
    }

    return new int[] {bestDist, index};
  }



  public static int treeDiameter(List<List<Integer>> graph, int start) {

    if (graph == null) return 0;


    VISITED_TOKEN++;
    int furthestIndex = dfs(graph, start, -1)[1];


    if (furthestIndex == -1) return 0;



    VISITED_TOKEN++;
    int diameter = dfs(graph, furthestIndex, -1)[0] - 1;

    return diameter;
  }

  public static void main(String[] args) {

    List<List<Integer>> graph = createGraph(MAX_NUM_NODES);

    int numNodes = 8;
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 2, 4);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 4, 6);
    addUndirectedEdge(graph, 6, 7);

    int diameter = treeDiameter(graph, 0);
    System.out.println("Tree diameter: " + diameter);
    if (diameter != 5) System.out.println("ERROR");
    resetGraph(graph, numNodes);

    numNodes = 10;
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 2, 4);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 4, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 9);
    addUndirectedEdge(graph, 7, 8);

    diameter = treeDiameter(graph, 5);
    System.out.println("Tree diameter: " + diameter);
    if (diameter != 6) System.out.println("ERROR");
    resetGraph(graph, numNodes);

    diameter = treeDiameter(graph, 3);
    System.out.println("Tree diameter: " + diameter);
    if (diameter != 0) System.out.println("ERROR");
  }

  private static List<List<Integer>> createGraph(int size) {
    List<List<Integer>> graph = new ArrayList<>(size);
    for (int i = 0; i < size; i++) graph.add(new ArrayList<>());
    return graph;
  }

  private static void resetGraph(List<List<Integer>> graph, int size) {
    for (int i = 0; i < size; i++) graph.get(i).clear();
  }

  private static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }
}
