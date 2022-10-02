
package sorting;

public class InsertionSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    InsertionSort.insertionSort(values);
  }





  private static void insertionSort(int[] ar) {
    if (ar == null) {
      return;
    }

    for (int i = 1; i < ar.length; i++) {
      for (int j = i; j > 0 && ar[j] < ar[j - 1]; j--) {
        swap(ar, j - 1, j);
      }
    }
  }

  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {
    InplaceSort sorter = new InsertionSort();
    int[] array = {10, 4, 6, 8, 13, 2, 3};
    sorter.sort(array);


    System.out.println(java.util.Arrays.toString(array));
  }
}
