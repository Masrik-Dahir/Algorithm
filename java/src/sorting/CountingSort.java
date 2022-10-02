
package sorting;

public class CountingSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    int minValue = Integer.MAX_VALUE;
    int maxValue = Integer.MIN_VALUE;
    for (int i = 0; i < values.length; i++) {
      if (values[i] < minValue) minValue = values[i];
      if (values[i] > maxValue) maxValue = values[i];
    }
    CountingSort.countingSort(values, minValue, maxValue);
  }


  private static void countingSort(int[] ar, int minVal, int maxVal) {
    int sz = maxVal - minVal + 1;
    int[] b = new int[sz];
    for (int i = 0; i < ar.length; i++) b[ar[i] - minVal]++;
    for (int i = 0, k = 0; i < sz; i++) {
      while (b[i]-- > 0) ar[k++] = i + minVal;
    }
  }

  public static void main(String[] args) {
    CountingSort sorter = new CountingSort();
    int[] nums = {+4, -10, +0, +6, +1, -5, -5, +1, +1, -2, 0, +6, +8, -7, +10};
    sorter.sort(nums);



    System.out.println(java.util.Arrays.toString(nums));
  }
}
