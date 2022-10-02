
package graphtheory;

import java.util.*;

class TarjanAdjacencyMatrix {

  public static void main(String[] args) {



    final int NUM_NODES = 10;

    boolean[][] adjMatrix = new boolean[NUM_NODES][NUM_NODES];


    adjMatrix[0][1] = true;
    adjMatrix[1][2] = true;
    adjMatrix[2][0] = true;


    adjMatrix[5][4] = true;
    adjMatrix[5][6] = true;
    adjMatrix[3][5] = true;
    adjMatrix[4][3] = true;
    adjMatrix[4][5] = true;
    adjMatrix[6][4] = true;


    adjMatrix[7][8] = true;
    adjMatrix[8][7] = true;



    adjMatrix[1][5] = true;
    adjMatrix[1][7] = true;
    adjMatrix[2][7] = true;
    adjMatrix[6][8] = true;
    adjMatrix[9][8] = true;
    adjMatrix[9][4] = true;

    Tarjan sccs = new Tarjan(adjMatrix);

    System.out.println(
        "Strong connected component count: " + sccs.countStronglyConnectedComponents());
    System.out.println(
        "Strong connected components:\n" + Arrays.toString(sccs.getStronglyConnectedComponents()));






  }



  static class Tarjan {

    private int n, pre, count = 0;
    private int[] id, low;
    private boolean[] marked;
    private boolean[][] adj;
    private Stack<Integer> stack = new Stack<>();


    public Tarjan(boolean[][] adj) {
      n = adj.length;
      this.adj = adj;
      marked = new boolean[n];
      id = new int[n];
      low = new int[n];
      for (int u = 0; u < n; u++) if (!marked[u]) dfs(u);
    }

    private void dfs(int u) {
      marked[u] = true;
      low[u] = pre++;
      int min = low[u];
      stack.push(u);
      for (int v = 0; v < n; v++) {
        if (adj[u][v]) {
          if (!marked[v]) dfs(v);
          if (low[v] < min) min = low[v];
        }
      }
      if (min < low[u]) {
        low[u] = min;
        return;
      }
      int v;
      do {
        v = stack.pop();
        id[v] = count;
        low[v] = n;
      } while (v != u);
      count++;
    }



    public int[] getStronglyConnectedComponents() {
      return id.clone();
    }


    public int countStronglyConnectedComponents() {
      return count;
    }
  }
}
