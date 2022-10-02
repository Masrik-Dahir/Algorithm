
package other;

public class LazyRangeAdder {


  private int n;


  private int[] array;


  private int[] differenceArray;


  public LazyRangeAdder(int[] array) {
    this.array = array;
    this.n = array.length;

    differenceArray = new int[n + 1];
    differenceArray[0] = array[0];
    for (int i = 1; i < n; i++) {
      differenceArray[i] = array[i] - array[i - 1];
    }
  }


  public void add(int l, int r, int x) {
    differenceArray[l] += x;
    differenceArray[r + 1] -= x;
  }



  public void done() {
    for (int i = 0; i < n; i++) {
      if (i == 0) {
        array[i] = differenceArray[i];
      } else {
        array[i] = differenceArray[i] + array[i - 1];
      }
    }
  }

  public static void main(String[] args) {

    int[] array = {10, 4, 6, 13, 8, 15, 17, 22};
    LazyRangeAdder lazyRangeAdder = new LazyRangeAdder(array);



    lazyRangeAdder.add(1, 4, 10);
    lazyRangeAdder.done();
    System.out.println(java.util.Arrays.toString(array));



    lazyRangeAdder.add(3, 6, -5);
    lazyRangeAdder.add(0, 7, 12);
    lazyRangeAdder.done();
    System.out.println(java.util.Arrays.toString(array));
  }
}
