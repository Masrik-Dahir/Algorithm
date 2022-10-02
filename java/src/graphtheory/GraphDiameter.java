
package graphtheory;

import java.util.*;

public class GraphDiameter {

  static class Edge {
    int from, to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }



  static final int DEPTH_TOKEN = -1;

  static Integer VISITED_TOKEN = 0;
  static Map<Integer, Integer> visited = new HashMap<>();
  static ArrayDeque<Integer> queue = new ArrayDeque<>();



  private static int eccentricity(int nodeID, Map<Integer, List<Edge>> graph) {

    VISITED_TOKEN++;

    queue.offer(nodeID);
    queue.offer(DEPTH_TOKEN);
    visited.put(nodeID, VISITED_TOKEN);

    int depth = 0;


    while (true) {

      Integer id = queue.poll();





      if (id == DEPTH_TOKEN) {


        if (queue.isEmpty()) break;


        queue.offer(DEPTH_TOKEN);


        depth++;

      } else {

        List<Edge> edges = graph.get(id);
        if (edges != null) {
          for (Edge edge : edges) {
            if (visited.get(edge.to) != VISITED_TOKEN) {
              visited.put(edge.to, VISITED_TOKEN);
              queue.offer(edge.to);
            }
          }
        }
      }
    }

    return depth;
  }



  public static int graphDiameter(Map<Integer, List<Edge>> graph) {

    if (graph == null) return 0;

    int diameter = 0;
    int radius = Integer.MAX_VALUE;



    for (Integer nodeID : graph.keySet()) {
      int eccentricity = eccentricity(nodeID, graph);
      diameter = Math.max(diameter, eccentricity);
      radius = Math.min(radius, eccentricity);
    }


    return diameter;
  }


  public static void main(String[] args) {

    Map<Integer, List<Edge>> graph = createGraph(5);
    addUndirectedEdge(graph, 4, 2);
    addUndirectedEdge(graph, 2, 0);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 1, 3);

    int diameter = graphDiameter(graph);
    if (diameter != 3) System.out.println("Wrong diameter!");


    graph = createGraph(5);
    diameter = graphDiameter(graph);
    if (diameter != 0) System.out.println("Wrong diameter!");

    graph = createGraph(8);
    addUndirectedEdge(graph, 0, 5);
    addUndirectedEdge(graph, 1, 5);
    addUndirectedEdge(graph, 2, 5);
    addUndirectedEdge(graph, 3, 5);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 6, 5);
    addUndirectedEdge(graph, 7, 5);
    diameter = graphDiameter(graph);
    if (diameter != 2) System.out.println("Wrong diameter!");

    graph = createGraph(9);
    addUndirectedEdge(graph, 0, 5);
    addUndirectedEdge(graph, 1, 5);
    addUndirectedEdge(graph, 2, 5);
    addUndirectedEdge(graph, 3, 5);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 6, 5);
    addUndirectedEdge(graph, 7, 5);
    addUndirectedEdge(graph, 3, 8);
    diameter = graphDiameter(graph);
    if (diameter != 3) System.out.println("Wrong diameter!");
  }

  private static Map<Integer, List<Edge>> createGraph(int numNodes) {
    Map<Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < numNodes; i++) graph.put(i, new ArrayList<Edge>());
    return graph;
  }

  private static void addUndirectedEdge(Map<Integer, List<Edge>> graph, int from, int to) {
    graph.get(from).add(new Edge(from, to));
    graph.get(to).add(new Edge(to, from));
  }
}
