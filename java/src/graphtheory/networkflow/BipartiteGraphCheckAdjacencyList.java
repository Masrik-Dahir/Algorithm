
package graphtheory.networkflow;

import utils.graphutils.Utils;
import java.util.List;

public class BipartiteGraphCheckAdjacencyList {

  private int n;
  private int[] colors;
  private boolean solved;
  private boolean isBipartite;
  private List<List<Integer>> graph;

  public static final int RED = 0b10, BLACK = (RED ^ 1);

  public BipartiteGraphCheckAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    n = graph.size();
    this.graph = graph;
  }


  public boolean isBipartite() {
    if (!solved) solve();
    return isBipartite;
  }




  public int[] getTwoColoring() {
    return isBipartite() ? colors : null;
  }

  private void solve() {
    if (n <= 1) return;

    colors = new int[n];
    int nodesVisited = colorGraph(0, RED);



    isBipartite = (nodesVisited == n);
    solved = true;
  }




  private int colorGraph(int i, int color) {
    colors[i] = color;



    int nextColor = (color ^ 1);

    int visitCount = 1;
    List<Integer> edges = graph.get(i);

    for (int to : edges) {


      if (colors[to] == color) return -1;
      if (colors[to] == nextColor) continue;



      int count = colorGraph(to, nextColor);
      if (count == -1) return -1;
      visitCount += count;
    }

    return visitCount;
  }



  public static void main(String[] args) {


    int n = 1;
    List<List<Integer>> graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 0);
    displayGraph(graph);








    n = 2;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    displayGraph(graph);








    n = 3;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 0);
    displayGraph(graph);












    n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 2, 3);
    displayGraph(graph);










    n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 3);
    Utils.addUndirectedEdge(graph, 3, 0);
    displayGraph(graph);














    n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 3);
    Utils.addUndirectedEdge(graph, 3, 0);
    Utils.addUndirectedEdge(graph, 0, 2);
    displayGraph(graph);















  }

  private static void displayGraph(List<List<Integer>> graph) {
    final int n = graph.size();

    System.out.println("Graph has " + n + " node(s) and the following edges:");
    for (int f = 0; f < n; f++) for (int t : graph.get(f)) System.out.println(f + " -> " + t);

    BipartiteGraphCheckAdjacencyList solver;
    solver = new BipartiteGraphCheckAdjacencyList(graph);

    System.out.println("This graph is bipartite: " + (solver.isBipartite()));
    System.out.println();
  }
}
