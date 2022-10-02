
package search;

public class InterpolationSearch {

  
  public static int interpolationSearch(int[] nums, int val) {
    int lo = 0, mid = 0, hi = nums.length - 1;
    while (nums[lo] <= val && nums[hi] >= val) {
      mid = lo + ((val - nums[lo]) * (hi - lo)) / (nums[hi] - nums[lo]);
      if (nums[mid] < val) {
        lo = mid + 1;
      } else if (nums[mid] > val) {
        hi = mid - 1;
      } else return mid;
    }
    if (nums[lo] == val) return lo;
    return -1;
  }

  public static void main(String[] args) {

    int[] values = {10, 20, 25, 35, 50, 70, 85, 100, 110, 120, 125};



    System.out.println(interpolationSearch(values, 25));


    System.out.println(interpolationSearch(values, 111));
  }
}
