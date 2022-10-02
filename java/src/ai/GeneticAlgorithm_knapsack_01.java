package ai;

import java.util.*;

public class GeneticAlgorithm_knapsack_01 {

  static final Random RANDOM = new Random();
  static final int P = 500;
  static final int MAX_EPOCH = 10000;
  static final double MUTATION_RATE = 0.0125;
  static double power;
  static final double POWER_INC = 0.0001;
  static long run(int capacity, int[] weights, int[] values) {

    power = 1.0;
    final int N = weights.length;

    Individual[] generation = new Individual[P + 1];
    Individual[] nextGeneration = new Individual[P + 1];
    for (int i = 1; i <= P; i++) generation[i] = new Individual(N);

    double[] lo = new double[P + 1];
    double[] hi = new double[P + 1];

    long[] fitness = new long[P + 1];
    long bestFitness = 0;

    for (int epoch = 1; epoch <= MAX_EPOCH; epoch++, power += POWER_INC) {
      double fitnessSum = 0;

      for (int i = 1; i <= P; i++) {
        Individual in = generation[i];
        fitness[i] = fitness(in, weights, values, capacity, N);
        fitnessSum += fitness[i];
        lo[i] = hi[i] = 0;
      }

      long bestEpochFitness = 0;
      for (int i = 1; i <= P; i++) {

        double norm = fitness[i] / fitnessSum;

        lo[i] = hi[i - 1] = lo[i - 1] + norm;

        if (fitness[i] > bestEpochFitness) {
          bestEpochFitness = fitness[i];
          if (bestEpochFitness > bestFitness) bestFitness = bestEpochFitness;
        }
      }

      if (epoch % 50 == 0)
        System.out.printf("Epoch: %d, %d$, %d$\n", epoch, bestEpochFitness, bestFitness);

      for (int i = 1; i <= P; i++) {
        Individual parent1 = selectIndividual(generation, lo, hi);
        Individual parent2 = selectIndividual(generation, lo, hi);
        Individual child = crossover(parent1, parent2, N);
        for (int j = 0; j < N; j++) {
          if (Math.random() < MUTATION_RATE) mutate(child, j);
        }

        nextGeneration[i] = child;
      }

      generation = nextGeneration;
    }

    return bestFitness;
  }

  static long fitness(Individual in, int[] weights, int[] values, int capacity, int n) {
    long value = 0, weight = 0;
    for (int i = 0; i < n; i++) {
      if (in.bits.get(i)) {
        value += values[i];
        weight += weights[i];
      }
      if (weight > capacity) return 0;
    }
    return value;
  }

  static void mutate(Individual in, int i) {
    in.bits.flip(i);
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
    int splitPoint = RANDOM.nextInt(n);
    BitSet newBitSet = new BitSet(n);
    for (int i = 0; i < splitPoint; i++) if (p1.bits.get(i)) newBitSet.flip(i);
    for (int i = splitPoint; i < n; i++) if (p2.bits.get(i)) newBitSet.flip(i);
    return new Individual(newBitSet);
  }

  public static void main(String[] args) {

    int n = 50;
    int multiplier = 100000;

    int[] weights = new int[n];
    int[] values = new int[n];

    for (int i = 0; i < n; i++) {
      weights[i] = (int) (Math.random() * multiplier);
      values[i] = (int) (Math.random() * multiplier);
    }

    int cap = (n * multiplier) / 3;
    long gaAns = run(cap, weights, values);
    int answer = Knapsack_01.knapsack(cap, weights, values);

    System.out.println("\nGenetic algorithm approximation: " + gaAns + "$");
    System.out.println("Actual answer calculated with DP:  " + answer + "$\n");
    System.out.printf(
        "Genetic algorithm was %.5f%% off from true answer\n\n",
        (1.0 - ((double) gaAns) / answer) * 100);
  }

  static class Individual {

    BitSet bits;
    public Individual(int n) {
      bits = new BitSet(n);
      for (int i = 0; i < n; i++) {
        if (Math.random() < 0.5) {
          bits.flip(i);
        }
      }
    }

    public Individual(BitSet set) {
      this.bits = set;
    }

    @Override
    public String toString() {
      return bits.toString();
    }
  }

  static class Knapsack_01 {

    
    public static int knapsack(int maxWeight, int[] W, int[] V) {

      if (W == null || V == null || W.length != V.length || maxWeight < 0) {
        throw new IllegalArgumentException("Invalid input");
      }

      final int N = W.length;
      int[][] DP = new int[N + 1][maxWeight + 1];

      for (int i = 1; i <= N; i++) {
        int w = W[i - 1], v = V[i - 1];
        for (int sz = 1; sz <= maxWeight; sz++) {
          DP[i][sz] = DP[i - 1][sz];
          if (sz >= w && DP[i - 1][sz - w] + v > DP[i][sz]) {
            DP[i][sz] = DP[i - 1][sz - w] + v;
          }
        }
      }
      return DP[N][maxWeight];
    }
  }
}
