
package sorting;

public class QuickSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    QuickSort.quicksort(values);
  }

  public static void quicksort(int[] ar) {
    if (ar == null) return;
    quicksort(ar, 0, ar.length - 1);
  }


  private static void quicksort(int[] ar, int lo, int hi) {
    if (lo < hi) {
      int splitPoint = partition(ar, lo, hi);
      quicksort(ar, lo, splitPoint);
      quicksort(ar, splitPoint + 1, hi);
    }
  }


  private static int partition(int[] ar, int lo, int hi) {
    int pivot = ar[lo];
    int i = lo - 1, j = hi + 1;
    while (true) {
      do {
        i++;
      } while (ar[i] < pivot);
      do {
        j--;
      } while (ar[j] > pivot);
      if (i < j) swap(ar, i, j);
      else return j;
    }
  }


  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {
    InplaceSort sorter = new QuickSort();
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    sorter.sort(array);


    System.out.println(java.util.Arrays.toString(array));
  }
}
