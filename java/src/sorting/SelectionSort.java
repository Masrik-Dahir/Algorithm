
package sorting;

public class SelectionSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    SelectionSort.selectionSort(values);
  }

  public static void selectionSort(int[] array) {
    if (array == null) return;
    final int N = array.length;

    for (int i = 0; i < N; i++) {

      int swapIndex = i;
      for (int j = i + 1; j < N; j++) {
        if (array[j] < array[swapIndex]) {
          swapIndex = j;
        }
      }
      swap(array, i, swapIndex);
    }
  }

  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {
    InplaceSort sorter = new SelectionSort();
    int[] array = {10, 4, 6, 8, 13, 2, 3};
    sorter.sort(array);


    System.out.println(java.util.Arrays.toString(array));
  }
}
