
package datastructures.queue;

public class IntQueue implements Queue<Integer> {

  private int[] data;
  private int front, end;
  private int size;



  public IntQueue(int maxSize) {
    front = end = size = 0;
    data = new int[maxSize];
  }


  public boolean isEmpty() {
    return size == 0;
  }


  public int size() {
    return size;
  }

  @Override
  public Integer peek() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }
    front = front % data.length;
    return data[front];
  }

  public boolean isFull() {
    return size == data.length;
  }


  @Override
  public void offer(Integer value) {
    if (isFull()) {
      throw new RuntimeException("Queue too small!");
    }
    data[end++] = value;
    size++;
    end = end % data.length;
  }


  @Override
  public Integer poll() {
    if (size == 0) {
      throw new RuntimeException("Queue is empty");
    }
    size--;
    front = front % data.length;
    return data[front++];
  }


  public static void main(String[] args) {

    IntQueue q = new IntQueue(5);

    q.offer(1);
    q.offer(2);
    q.offer(3);
    q.offer(4);
    q.offer(5);

    System.out.println(q.poll());
    System.out.println(q.poll());
    System.out.println(q.poll());
    System.out.println(q.poll());

    System.out.println(q.isEmpty());

    q.offer(1);
    q.offer(2);
    q.offer(3);

    System.out.println(q.poll());
    System.out.println(q.poll());
    System.out.println(q.poll());
    System.out.println(q.poll());

    System.out.println(q.isEmpty());


  }


  private static void benchMarkTest() {

    int n = 10000000;
    IntQueue intQ = new IntQueue(n);


    long start = System.nanoTime();
    for (int i = 0; i < n; i++) intQ.offer(i);
    for (int i = 0; i < n; i++) intQ.poll();
    long end = System.nanoTime();
    System.out.println("IntQueue Time: " + (end - start) / 1e9);


    java.util.ArrayDeque<Integer> arrayDeque = new java.util.ArrayDeque<>();


    start = System.nanoTime();
    for (int i = 0; i < n; i++) arrayDeque.offer(i);
    for (int i = 0; i < n; i++) arrayDeque.poll();
    end = System.nanoTime();
    System.out.println("ArrayDeque Time: " + (end - start) / 1e9);
  }
}
