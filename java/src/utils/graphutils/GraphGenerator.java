package utils.graphutils;

import java.util.*;

public class GraphGenerator {

  public static class DagGenerator {
    double edgeProbability;
    int minLevels, maxLevels, minNodesPerLevel, maxNodesPerLevel;



    public DagGenerator(
        int minLevels,
        int maxLevels,
        int minNodesPerLevel,
        int maxNodesPerLevel,
        double edgeProbability) {
      this.minLevels = minLevels;
      this.maxLevels = maxLevels;
      this.minNodesPerLevel = minNodesPerLevel;
      this.maxNodesPerLevel = maxNodesPerLevel;
      this.edgeProbability = edgeProbability;
    }

    
    private static int rand(int min, int max) {
      return min + (int) (Math.random() * ((max - min) + 1));
    }

    public List<List<Integer>> createDag() {
      int levels = rand(minLevels, maxLevels);
      int[] nodesPerLevel = new int[levels];

      int n = 0;
      for (int l = 0; l < levels; l++) {
        nodesPerLevel[l] = rand(minNodesPerLevel, maxNodesPerLevel);
        n += nodesPerLevel[l];
      }
      List<List<Integer>> g = Utils.createEmptyAdjacencyList(n);
      int levelIndex = 0;
      for (int l = 0; l < levels - 1; l++) {
        for (int i = 0; i < nodesPerLevel[l]; i++) {
          for (int j = 0; j < nodesPerLevel[l + 1]; j++) {
            if (Math.random() <= edgeProbability) {
              Utils.addDirectedEdge(g, levelIndex + i, levelIndex + nodesPerLevel[l] + j);
            }
          }
        }
        levelIndex += nodesPerLevel[l];
      }
      return g;
    }
  }

  public static void main(String[] args) {
    DagGenerator gen = new DagGenerator(10, 10, 5, 5, 0.9);
    gen.createDag();
  }
}
