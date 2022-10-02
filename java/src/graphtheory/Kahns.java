
package graphtheory;

import static utils.graphutils.Utils.addDirectedEdge;
import static utils.graphutils.Utils.createEmptyAdjacencyList;

import java.util.*;

public class Kahns {



  public int[] kahns(List<List<Integer>> g) {
    int n = g.size();


    int[] inDegree = new int[n];
    for (List<Integer> edges : g) {
      for (int to : edges) {
        inDegree[to]++;
      }
    }


    Queue<Integer> q = new ArrayDeque<>();


    for (int i = 0; i < n; i++) {
      if (inDegree[i] == 0) {
        q.offer(i);
      }
    }

    int index = 0;
    int[] order = new int[n];
    while (!q.isEmpty()) {
      int at = q.poll();
      order[index++] = at;
      for (int to : g.get(at)) {
        inDegree[to]--;
        if (inDegree[to] == 0) {
          q.offer(to);
        }
      }
    }
    if (index != n) {
      throw new IllegalArgumentException("Graph is not acyclic! Detected a cycle.");
    }
    return order;
  }


  public static void main(String[] args) {
    exampleFromSlides();



  }

  private static void exampleFromSlides() {
    List<List<Integer>> g = createEmptyAdjacencyList(14);
    addDirectedEdge(g, 0, 2);
    addDirectedEdge(g, 0, 3);
    addDirectedEdge(g, 0, 6);
    addDirectedEdge(g, 1, 4);
    addDirectedEdge(g, 2, 6);
    addDirectedEdge(g, 3, 1);
    addDirectedEdge(g, 3, 4);
    addDirectedEdge(g, 4, 5);
    addDirectedEdge(g, 4, 8);
    addDirectedEdge(g, 6, 7);
    addDirectedEdge(g, 6, 11);
    addDirectedEdge(g, 7, 4);
    addDirectedEdge(g, 7, 12);
    addDirectedEdge(g, 9, 2);
    addDirectedEdge(g, 9, 10);
    addDirectedEdge(g, 10, 6);
    addDirectedEdge(g, 11, 12);
    addDirectedEdge(g, 12, 8);

    Kahns solver = new Kahns();
    int[] ordering = solver.kahns(g);


    System.out.println(java.util.Arrays.toString(ordering));
  }

  private static void test1() {
    List<List<Integer>> g = createEmptyAdjacencyList(6);
    addDirectedEdge(g, 0, 1);
    addDirectedEdge(g, 0, 2);
    addDirectedEdge(g, 1, 2);
    addDirectedEdge(g, 3, 1);
    addDirectedEdge(g, 3, 2);
    addDirectedEdge(g, 2, 4);
    addDirectedEdge(g, 4, 5);
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));
  }

  private static void test2() {
    List<List<Integer>> g = createEmptyAdjacencyList(6);
    addDirectedEdge(g, 0, 1);
    addDirectedEdge(g, 0, 2);
    addDirectedEdge(g, 0, 5);
    addDirectedEdge(g, 1, 2);
    addDirectedEdge(g, 1, 3);
    addDirectedEdge(g, 2, 3);
    addDirectedEdge(g, 2, 4);
    addDirectedEdge(g, 3, 4);
    addDirectedEdge(g, 5, 4);
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));
  }

  private static void cycleTest() {
    List<List<Integer>> g = createEmptyAdjacencyList(4);
    addDirectedEdge(g, 0, 1);
    addDirectedEdge(g, 1, 2);
    addDirectedEdge(g, 2, 3);
    addDirectedEdge(g, 3, 0);
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));
  }
}
