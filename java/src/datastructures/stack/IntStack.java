
package datastructures.stack;

public class IntStack implements Stack<Integer> {

  private int[] ar;
  private int pos = 0;



  public IntStack(int maxSize) {
    ar = new int[maxSize];
  }


  public int size() {
    return pos;
  }


  public boolean isEmpty() {
    return pos == 0;
  }


  @Override
  public Integer peek() {
    return ar[pos - 1];
  }


  @Override
  public void push(Integer value) {
    ar[pos++] = value;
  }


  @Override
  public Integer pop() {
    return ar[--pos];
  }


  public static void main(String[] args) {

    IntStack s = new IntStack(5);

    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    s.push(5);

    System.out.println(s.pop());
    System.out.println(s.pop());
    System.out.println(s.pop());

    s.push(3);
    s.push(4);
    s.push(5);

    while (!s.isEmpty()) System.out.println(s.pop());

    benchMarkTest();
  }


  private static void benchMarkTest() {

    int n = 10000000;
    IntStack intStack = new IntStack(n);


    long start = System.nanoTime();
    for (int i = 0; i < n; i++) intStack.push(i);
    for (int i = 0; i < n; i++) intStack.pop();
    long end = System.nanoTime();
    System.out.println("IntStack Time: " + (end - start) / 1e9);




    java.util.ArrayDeque<Integer> arrayDeque = new java.util.ArrayDeque<>(n);

    start = System.nanoTime();
    for (int i = 0; i < n; i++) arrayDeque.push(i);
    for (int i = 0; i < n; i++) arrayDeque.pop();
    end = System.nanoTime();
    System.out.println("ArrayDeque Time: " + (end - start) / 1e9);

    Stack<Integer> listStack = new ListStack<>();

    start = System.nanoTime();
    for (int i = 0; i < n; i++) listStack.push(i);
    for (int i = 0; i < n; i++) listStack.pop();
    end = System.nanoTime();
    System.out.println("ListStack Time: " + (end - start) / 1e9);

    Stack<Integer> arrayStack = new ArrayStack<>();

    start = System.nanoTime();
    for (int i = 0; i < n; i++) arrayStack.push(i);
    for (int i = 0; i < n; i++) arrayStack.pop();
    end = System.nanoTime();
    System.out.println("ArrayStack Time: " + (end - start) / 1e9);
  }
}
