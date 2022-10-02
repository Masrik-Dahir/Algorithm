
package dp;

public class MaximumSubarray {

  public static void main(String[] args) {
    System.out.println(maximumSubarrayValue(new int[] {-5}));
    System.out.println(maximumSubarrayValue(new int[] {-5, -4, -10, -3, -1, -12, -6}));
    System.out.println(maximumSubarrayValue(new int[] {1, 2, 1, -7, 2, -1, 40, -89}));
  }


  public static long maximumSubarrayValue(int[] ar) {

    if (ar == null || ar.length == 0) return 0L;
    int n = ar.length, maxValue, sum;

    maxValue = sum = ar[0];

    for (int i = 1; i < n; i++) {




      if (ar[i] > sum + ar[i]) sum = ar[i];
      else sum = sum + ar[i];

      if (sum > maxValue) maxValue = sum;
    }

    return maxValue;
  }
}
