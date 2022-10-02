
package graphtheory;

import java.util.*;






class IntStack {

  private int[] ar;
  private int pos = 0, sz;



  public IntStack(int max_sz) {
    ar = new int[(sz = max_sz)];
  }

  public boolean isEmpty() {
    return pos == 0;
  }


  public int peek() {
    return ar[pos - 1];
  }


  public void push(int value) {
    ar[pos++] = value;
  }


  public int pop() {
    return ar[--pos];
  }
}

public class DepthFirstSearchAdjacencyListIterativeFastStack {

  static class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }




  static int dfs(Map<Integer, List<Edge>> graph, int start, int n) {

    int count = 0;
    boolean[] visited = new boolean[n];
    IntStack stack = new IntStack(n);


    stack.push(start);

    while (!stack.isEmpty()) {
      int node = stack.pop();
      if (!visited[node]) {

        count++;
        visited[node] = true;
        List<Edge> edges = graph.get(node);

        if (edges != null) {
          for (Edge edge : edges) {
            if (!visited[edge.to]) {
              stack.push(edge.to);
            }
          }
        }
      }
    }

    return count;
  }


  public static void main(String[] args) {













    int numNodes = 5;
    Map<Integer, List<Edge>> graph = new HashMap<>();
    addDirectedEdge(graph, 0, 1, 4);
    addDirectedEdge(graph, 0, 2, 5);
    addDirectedEdge(graph, 1, 2, -2);
    addDirectedEdge(graph, 1, 3, 6);
    addDirectedEdge(graph, 2, 3, 1);
    addDirectedEdge(graph, 2, 2, 10);

    long nodeCount = dfs(graph, 0, numNodes);
    System.out.println("DFS node count starting at node 0: " + nodeCount);
    if (nodeCount != 4) System.err.println("Error with DFS");

    nodeCount = dfs(graph, 4, numNodes);
    System.out.println("DFS node count starting at node 4: " + nodeCount);
    if (nodeCount != 1) System.err.println("Error with DFS");
  }


  private static void addDirectedEdge(Map<Integer, List<Edge>> graph, int from, int to, int cost) {
    List<Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList<Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to, cost));
  }
}
