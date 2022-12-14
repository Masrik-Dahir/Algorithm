
package sorting;

import java.util.*;

public class BucketSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    int minValue = Integer.MAX_VALUE;
    int maxValue = Integer.MIN_VALUE;
    for (int i = 0; i < values.length; i++) {
      if (values[i] < minValue) minValue = values[i];
      if (values[i] > maxValue) maxValue = values[i];
    }
    BucketSort.bucketSort(values, minValue, maxValue);
  }




  private static void bucketSort(int[] ar, int minValue, int maxValue) {
    if (ar == null || ar.length == 0 || minValue == maxValue) return;


    final int N = ar.length, M = maxValue - minValue + 1, numBuckets = M / N + 1;
    List<List<Integer>> buckets = new ArrayList<>(numBuckets);
    for (int i = 0; i < numBuckets; i++) buckets.add(new ArrayList<>());


    for (int i = 0; i < N; i++) {
      int bi = (ar[i] - minValue) / M;
      List<Integer> bucket = buckets.get(bi);
      bucket.add(ar[i]);
    }


    for (int bi = 0, j = 0; bi < numBuckets; bi++) {
      List<Integer> bucket = buckets.get(bi);
      if (bucket != null) {
        Collections.sort(bucket);
        for (int k = 0; k < bucket.size(); k++) {
          ar[j++] = bucket.get(k);
        }
      }
    }
  }

  public static void main(String[] args) {
    BucketSort sorter = new BucketSort();

    int[] array = {10, 4, 6, 8, 13, 2, 3};
    sorter.sort(array);


    System.out.println(Arrays.toString(array));

    array = new int[] {10, 10, 10, 10, 10};
    sorter.sort(array);


    System.out.println(Arrays.toString(array));
  }
}
