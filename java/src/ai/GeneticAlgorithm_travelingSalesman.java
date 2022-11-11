package ai;

import java.util.*;

public class GeneticAlgorithm_travelingSalesman {
  static final Random RANDOM = new Random();
  static final int P = 250;
  static final int MAX_EPOCH = 100000;
  static final double MUTATION_RATE = 0.015;
  static double power;
  static final double POWER_INC = 0.0001;

  static double tsp(double[][] adjacencyMatrix) {

    power = 1.0;
    final int N = adjacencyMatrix.length;

    double max = Double.NEGATIVE_INFINITY;
    for (double[] row : adjacencyMatrix) {
      for (double elem : row) {
        max = Math.max(max, elem);
      }
    }

    Individual[] generation = new Individual[P + 1];
    Individual[] nextGeneration = new Individual[P + 1];
    for (int i = 1; i <= P; i++) generation[i] = new Individual(N);

    double[] lo = new double[P + 1];
    double[] hi = new double[P + 1];
    double[] fitness = new double[P + 1];

    int[] tour = null;
    Individual fittestIndv = null;
    double fittestIndvFitness = Double.NEGATIVE_INFINITY;

    for (int epoch = 1; epoch <= MAX_EPOCH; epoch++, power += POWER_INC) {
      double fitnessSum = 0;

      for (int i = 1; i <= P; i++) {
        Individual in = generation[i];
        fitness[i] = fitness(in, adjacencyMatrix, max, N);
        fitnessSum += fitness[i];
        lo[i] = hi[i] = 0;
      }

      Individual bestEpochIndv = null;
      double bestEpochFitness = Double.NEGATIVE_INFINITY;
      for (int i = 1; i <= P; i++) {

        Individual in = generation[i];
        double norm = fitness[i] / fitnessSum;

        lo[i] = hi[i - 1] = lo[i - 1] + norm;

        if (fitness[i] > bestEpochFitness) {

          bestEpochIndv = in;
          bestEpochFitness = fitness[i];
          if (fittestIndv == null) fittestIndv = in;

          double bestEpochTravelCost = trueTravelCost(bestEpochIndv, adjacencyMatrix, N);
          double bestTravelCost = trueTravelCost(fittestIndv, adjacencyMatrix, N);

          if (bestEpochTravelCost <= bestTravelCost) {
            tour = in.cities.clone();
            fittestIndv = in;
            fittestIndvFitness = bestEpochTravelCost;
          }
        }
      }

      double bestEpochTravelCost = trueTravelCost(bestEpochIndv, adjacencyMatrix, N);
      double bestTravelCost = trueTravelCost(fittestIndv, adjacencyMatrix, N);

      if (epoch % 100 == 0)
        System.out.printf("Epoch: %d, %.0f, %.0f\n", epoch, bestEpochTravelCost, bestTravelCost);

      for (int i = 1; i <= P; i++) {

        Individual parent1 = selectIndividual(generation, lo, hi);
        Individual parent2 = selectIndividual(generation, lo, hi);
        Individual child = crossover(parent1, parent2, N);
        for (int j = 0; j < N; j++) {
          if (Math.random() < MUTATION_RATE) {
            mutate(child);
          }
        }

        nextGeneration[i] = child;
      }

      generation = nextGeneration;
    }

    return trueTravelCost(fittestIndv, adjacencyMatrix, N);

  }

  static double fitness(Individual in, double[][] adjacencyMatrix, double max, int n) {

    double fitness = 0;
    for (int i = 1; i < n; i++) {
      int from = in.cities[i - 1];
      int to = in.cities[i];
      fitness += max - adjacencyMatrix[from][to];
    }

    int last = in.cities[n - 1];
    int first = in.cities[0];
    fitness += max - adjacencyMatrix[last][first];

    return Math.pow(fitness, power);
  }

  static double trueTravelCost(Individual in, double[][] adjacencyMatrix, int n) {

    double fitness = 0;
    for (int i = 1; i < n; i++) {
      int from = in.cities[i - 1];
      int to = in.cities[i];
      fitness += adjacencyMatrix[from][to];
    }

    int last = in.cities[n - 1];
    int first = in.cities[0];
    fitness += adjacencyMatrix[last][first];

    return fitness;
  }

  static void mutate(Individual in) {
    in.mutate();
  }

  static Individual selectIndividual(Individual[] generation, double[] lo, double[] hi) {
    double r = Math.random();

    int mid, l = 0, h = P - 1;
    while (true) {
      mid = (l + h) >>> 1;
      if (lo[mid] <= r && r < hi[mid]) return generation[mid + 1];
      if (r < lo[mid]) h = mid - 1;
      else l = mid + 1;
    }
  }

  static Individual crossover(Individual p1, Individual p2, int n) {

    int[] newPath = new int[n];
    int start = RANDOM.nextInt(n);
    int end = RANDOM.nextInt(n);
    int minimum = Math.min(start, end);
    int maximum = Math.max(start, end);

    int[] missing = new int[n - ((maximum - minimum) + 1)];

    int j = 0;
    for (int i = 0; i < n; i++) {
      if (i >= minimum && i <= maximum) {
        newPath[i] = p1.cities[i];
      } else {
        missing[j++] = p1.cities[i];
      }
    }

    Individual.shuffleArray(missing);

    j = 0;
    for (int i = 0; i < n; i++) {
      if (i < minimum || i > maximum) {
        newPath[i] = missing[j++];
      }
    }
    return new Individual(newPath);
  }

  public static void main(String[] args) {

    int n = 64;

    double[][] m = new double[n][n];
    for (double[] row : m) {
      Arrays.fill(row, 10.0);
    }

    List<Integer> path = new ArrayList<>(n);
    for (int i = 0; i < n; i++) path.add(i);
    for (int i = 1; i < n; i++) {
      int from = path.get(i - 1);
      int to = path.get(i);
      m[from][to] = 1.0;
    }
    int last = path.get(n - 1);
    int first = path.get(0);
    m[last][first] = 1.0;

    System.out.println(tsp(m));
  }

  static class Individual {

    int[] cities;
    static Random RANDOM = new Random();

    public Individual(int n) {
      cities = new int[n];
      for (int i = 0; i < n; i++) cities[i] = i;
      shuffleArray(cities);
    }

    public Individual(int[] cities) {
      this.cities = cities;
    }

    public void mutate() {
      int i = RANDOM.nextInt(cities.length);
      int j = RANDOM.nextInt(cities.length);
      int tmp = cities[i];
      cities[i] = cities[j];
      cities[j] = tmp;
    }

    public static void shuffleArray(int[] array) {
      int index;
      for (int i = array.length - 1; i > 0; i--) {
        index = RANDOM.nextInt(i + 1);
        if (index != i) {
          array[index] ^= array[i];
          array[i] ^= array[index];
          array[index] ^= array[i];
        }
      }
    }

    @Override
    public String toString() {
      return Arrays.toString(cities);
    }
  }
}
