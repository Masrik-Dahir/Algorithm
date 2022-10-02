package datastructures.fibonacciheap;



final class FibonacciHeapNode<E> {

  private final E element;

  private FibonacciHeapNode<E> parent;

  private FibonacciHeapNode<E> left = this;

  private FibonacciHeapNode<E> right = this;

  private FibonacciHeapNode<E> child;

  private int degree;

  private boolean marked;

  public FibonacciHeapNode(E element) {

    degree = 0;

    setParent(null);

    setChild(null);

    setLeft(this);

    setRight(this);

    setMarked(false);


    this.element = element;
  }

  public FibonacciHeapNode<E> getParent() {
    return parent;
  }

  public void setParent(FibonacciHeapNode<E> parent) {
    this.parent = parent;
  }

  public FibonacciHeapNode<E> getLeft() {
    return left;
  }

  public void setLeft(FibonacciHeapNode<E> left) {
    this.left = left;
  }

  public FibonacciHeapNode<E> getRight() {
    return right;
  }

  public void setRight(FibonacciHeapNode<E> right) {
    this.right = right;
  }

  public FibonacciHeapNode<E> getChild() {
    return child;
  }

  public void setChild(FibonacciHeapNode<E> child) {
    this.child = child;
  }

  public int getDegree() {
    return degree;
  }

  public void incraeseDegree() {
    degree++;
  }

  public void decraeseDegree() {
    degree--;
  }

  public boolean isMarked() {
    return marked;
  }

  public void setMarked(boolean marked) {
    this.marked = marked;
  }

  public E getElement() {
    return element;
  }

  @Override
  public String toString() {
    return element.toString();
  }
}
