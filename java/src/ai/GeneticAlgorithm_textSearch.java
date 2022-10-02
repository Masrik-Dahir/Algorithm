package ai;

import java.util.*;

public class GeneticAlgorithm_textSearch {

  static final String TARGET = "to be or not to be that is the question";
  static final char[] ALPHA = " abcdefghijklmnopqrstuvwxyz".toCharArray();;
  static final int TL = TARGET.length();

  static final Random RANDOM = new Random();
  static final int MAX_EPOCH = 100000;
  static final int POPULATION_SZ = 250;
  static final double MUTATION_RATE = 0.0125;
  static double power;
  static final double POWER_INC = 0.0001;
  static void run() {

    power = 1.0;
    Individual[] generation = new Individual[POPULATION_SZ + 1];
    Individual[] nextGeneration = new Individual[POPULATION_SZ + 1];
    for (int i = 1; i <= POPULATION_SZ; i++) {
      generation[i] = new Individual();
    }
    double[] lo = new double[POPULATION_SZ + 1];
    double[] hi = new double[POPULATION_SZ + 1];

    for (int epoch = 1; epoch <= MAX_EPOCH; epoch++, power += POWER_INC) {
      double fitnessSum = 0;

      for (int i = 1; i <= POPULATION_SZ; i++) {
        Individual in = generation[i];
        fitnessSum += in.fitness;
        lo[i] = hi[i] = 0;
      }

      Individual fittest = null;
      double maxFitness = 0;

      for (int i = 1; i <= POPULATION_SZ; i++) {

        Individual in = generation[i];
        double norm = in.fitness / fitnessSum;

        lo[i] = hi[i - 1] = lo[i - 1] + norm;

        if (in.fitness > maxFitness) {
          maxFitness = in.fitness;
          fittest = in;
        }
      }

      if (epoch % 50 == 0) System.out.printf("Epoch: %d,       %s\n", epoch, fittest);

      for (int i = 1; i <= POPULATION_SZ; i++) {
        Individual parent1 = selectIndividual(generation, lo, hi);
        Individual parent2 = selectIndividual(generation, lo, hi);
        Individual child = crossover(parent1, parent2);
        for (int j = 0; j < TL; j++) {
          if (Math.random() < MUTATION_RATE) {
            child = mutate(child, j);
          }
        }

        nextGeneration[i] = child;
        if (child.str.equals(TARGET)) {
          System.out.println("\nFOUND ANSWER: " + child + "\n");
          return;
        }
      }

      generation = nextGeneration;
    }
  }

  static class Individual {

    char[] dna;
    String str;
    double fitness;

    public Individual() {
      dna = new char[TL];
      for (int i = 0; i < TL; i++) {
        dna[i] = ALPHA[RANDOM.nextInt(ALPHA.length)];
      }
      this.str = new String(dna);
      this.fitness = fitness();
    }

    public Individual(String dna) {
      this.dna = dna.toCharArray();
      this.str = dna;
      this.fitness = fitness();
    }

    public double fitness() {
      double score = 0.0;
      for (int i = 0; i < TL; i++) {
        if (TARGET.charAt(i) == dna[i]) score++;
      }
      return Math.pow(score, power);
    }

    @Override
    public String toString() {
      return str;
    }
  }

  static Individual mutate(Individual in, int i) {
    in.dna[i] = ALPHA[RANDOM.nextInt(ALPHA.length)];
    in.str = new String(in.dna);
    return in;
  }

  static Individual selectIndividual(Individual[] generation, double[] lo, double[] hi) {

    double r = Math.random();
    int mid, l = 0, h = POPULATION_SZ - 1;
    while (true) {
      mid = (l + h) >>> 1;
      if (lo[mid] <= r && r < hi[mid]) return generation[mid + 1];
      if (r < lo[mid]) h = mid - 1;
      else l = mid + 1;
    }
  }

  static Individual crossover(Individual p1, Individual p2) {
    int splitPoint = RANDOM.nextInt(TL);
    String newDNA = p1.str.substring(0, splitPoint) + p2.str.substring(splitPoint, TL);
    return new Individual(newDNA);
  }

  public static void main(String[] args) {
    run();
  }
}
