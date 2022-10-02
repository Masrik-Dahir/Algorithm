
package graphtheory;

import java.util.*;

public class TwoSatSolverAdjacencyList {

  private int n;
  private List<List<Integer>> graph;

  private boolean solved;
  private boolean isSatisfiable;
  private TarjanSccSolverAdjacencyList sccSolver;

  public TwoSatSolverAdjacencyList(List<List<Integer>> graph) {
    n = graph.size() / 2;
    sccSolver = new TarjanSccSolverAdjacencyList(graph);
  }


  public boolean isSatisfiable() {
    if (!solved) solve();
    return isSatisfiable;
  }

  public void solve() {
    if (solved) return;

    int[] sccs = sccSolver.getSccs();




    isSatisfiable = true;
    for (int i = 0; i < sccs.length; i += 2) {
      if (sccs[i] == sccs[i ^ 1]) {
        isSatisfiable = false;
        break;
      }
    }


    if (isSatisfiable) {

    }

    solved = true;
  }







  public static List<List<Integer>> createImplicationGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(2 * n);
    for (int i = 0; i < 2 * n; i++) graph.add(new ArrayList<>());
    return graph;
  }


  public static void addXorClause(List<List<Integer>> graph, int p, int q) {

    addOrClause(graph, p, q);
    addOrClause(graph, p ^ 1, q ^ 1);
  }



  public static void addOrClause(List<List<Integer>> graph, int p, int q) {




    graph.get(p ^ 1).add(q);
    graph.get(q ^ 1).add(p);
  }

  public static void main(String[] args) {

    int n = 2;
    List<List<Integer>> graph = createImplicationGraph(n);

















  }
}
