
package graphtheory.treealgorithms;

import java.util.*;

public class TreeIsomorphismWithBfs {

  public static List<List<Integer>> createEmptyTree(int n) {
    List<List<Integer>> tree = new ArrayList<>(n);
    for (int i = 0; i < n; i++) tree.add(new ArrayList<>());
    return tree;
  }

  public static void addUndirectedEdge(List<List<Integer>> tree, int from, int to) {
    tree.get(from).add(to);
    tree.get(to).add(from);
  }

  private static List<Integer> findTreeCenters(List<List<Integer>> tree) {
    final int n = tree.size();
    int[] degrees = new int[n];


    List<Integer> leaves = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      List<Integer> edges = tree.get(i);
      degrees[i] = edges.size();
      if (degrees[i] <= 1) leaves.add(i);
    }

    int processedLeafs = leaves.size();




    while (processedLeafs < n) {
      List<Integer> newLeaves = new ArrayList<>();
      for (int node : leaves)
        for (int neighbor : tree.get(node)) if (--degrees[neighbor] == 1) newLeaves.add(neighbor);
      processedLeafs += newLeaves.size();
      leaves = newLeaves;
    }

    return leaves;
  }




  public static String encodeTree(List<List<Integer>> tree) {
    if (tree == null || tree.size() == 0) return "";
    if (tree.size() == 1) return "()";
    final int n = tree.size();

    int root = findTreeCenters(tree).get(0);

    int[] degree = new int[n];
    int[] parent = new int[n];
    boolean[] visited = new boolean[n];
    List<Integer> leafs = new ArrayList<>();

    Queue<Integer> q = new ArrayDeque<>();
    visited[root] = true;
    parent[root] = -1;
    q.offer(root);


    while (!q.isEmpty()) {
      int at = q.poll();
      List<Integer> edges = tree.get(at);
      degree[at] = edges.size();
      for (int next : edges) {
        if (!visited[next]) {
          visited[next] = true;
          parent[next] = at;
          q.offer(next);
        }
      }
      if (degree[at] == 1) leafs.add(at);
    }

    List<Integer> newLeafs = new ArrayList<>();
    String[] map = new String[n];
    for (int i = 0; i < n; i++) {
      visited[i] = false;
      map[i] = "()";
    }

    int treeSize = n;
    while (treeSize > 2) {
      for (int leaf : leafs) {



        visited[leaf] = true;
        int p = parent[leaf];
        if (--degree[p] == 1) newLeafs.add(p);

        treeSize--;
      }


      for (int p : newLeafs) {

        List<String> labels = new ArrayList<>();
        for (int child : tree.get(p))


          if (visited[child]) labels.add(map[child]);

        String parentInnerParentheses = map[p].substring(1, map[p].length() - 1);
        labels.add(parentInnerParentheses);

        Collections.sort(labels);
        map[p] = "(".concat(String.join("", labels)).concat(")");
      }

      leafs.clear();
      leafs.addAll(newLeafs);
      newLeafs.clear();
    }


    String l1 = map[leafs.get(0)];
    if (treeSize == 1) return l1;


    String l2 = map[leafs.get(1)];
    return ((l1.compareTo(l2) < 0) ? (l1 + l2) : (l2 + l1));
  }

  public static boolean treesAreIsomorphic(List<List<Integer>> tree1, List<List<Integer>> tree2) {
    return encodeTree(tree1).equals(encodeTree(tree2));
  }

  

  public static void main(String[] args) {


    List<List<Integer>> tree1 = createEmptyTree(5);
    List<List<Integer>> tree2 = createEmptyTree(5);

    addUndirectedEdge(tree1, 2, 0);
    addUndirectedEdge(tree1, 3, 4);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 2, 3);

    addUndirectedEdge(tree2, 1, 0);
    addUndirectedEdge(tree2, 2, 4);
    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 1, 2);

    String encoding1 = encodeTree(tree1);
    String encoding2 = encodeTree(tree2);

    System.out.println("Tree1 encoding: " + encoding1);
    System.out.println("Tree2 encoding: " + encoding1);
    System.out.println("Trees are isomorphic: " + (encoding1.equals(encoding2)));





  }
}
