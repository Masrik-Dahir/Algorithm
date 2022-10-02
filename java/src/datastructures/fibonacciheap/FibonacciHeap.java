
package datastructures.fibonacciheap;

import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public final class FibonacciHeap<E> implements Queue<E> {

  private static final double LOG_PHI = log((1 + sqrt(5)) / 2);

  private final Set<E> elementsIndex = new HashSet<E>();

  private final Comparator<? super E> comparator;

  private int size = 0;

  private int trees = 0;

  private int markedNodes = 0;

  private FibonacciHeapNode<E> minimumNode;

  public FibonacciHeap() {
    this(null);
  }

  public FibonacciHeap( Comparator<? super E> comparator) {
    this.comparator = comparator;
  }

  private void moveToRoot(FibonacciHeapNode<E> node) {

    if (isEmpty()) {

      minimumNode = node;
    } else {

      node.getLeft().setRight(node.getRight());
      node.getRight().setLeft(node.getLeft());

      node.setLeft(minimumNode);
      node.setRight(minimumNode.getRight());
      minimumNode.setRight(node);
      node.getRight().setLeft(node);


      if (compare(node, minimumNode) < 0) {

        minimumNode = node;
      }
    }
  }

  public boolean add(E e) {
    if (e == null) {
      throw new IllegalArgumentException(
          "Null elements not allowed in this FibonacciHeap implementation.");
    }


    FibonacciHeapNode<E> node = new FibonacciHeapNode<E>(e);


    moveToRoot(node);


    size++;

    elementsIndex.add(e);

    return true;
  }

  
  public boolean addAll(Collection<? extends E> c) {
    for (E element : c) {
      add(element);
    }

    return true;
  }

  
  public void clear() {
    minimumNode = null;
    size = 0;
    trees = 0;
    markedNodes = 0;
    elementsIndex.clear();
  }

  
  public boolean contains(Object o) {
    if (o == null) {
      return false;
    }

    return elementsIndex.contains(o);
  }

  
  public boolean containsAll(Collection<?> c) {
    if (c == null) {
      return false;
    }

    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }

    return true;
  }

  
  public boolean isEmpty() {
    return minimumNode == null;
  }

  
  public Iterator<E> iterator() {
    throw new UnsupportedOperationException();
  }

  
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  
  public int size() {
    return size;
  }

  
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  
  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException();
  }

  
  public E element() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return peek();
  }

  
  public boolean offer(E e) {
    return add(e);
  }

  
  public E peek() {
    if (isEmpty()) {
      return null;
    }

    return minimumNode.getElement();
  }

  public E poll() {

    if (isEmpty()) {
      return null;
    }


    FibonacciHeapNode<E> z = minimumNode;
    int numOfKids = z.getDegree();

    FibonacciHeapNode<E> x = z.getChild();
    FibonacciHeapNode<E> tempRight;

    while (numOfKids > 0) {

      tempRight = x.getRight();


      moveToRoot(x);


      x.setParent(null);

      x = tempRight;
      numOfKids--;
    }


    z.getLeft().setRight(z.getRight());
    z.getRight().setLeft(z.getLeft());


    if (z == z.getRight()) {

      minimumNode = null;
    } else {

      minimumNode = z.getRight();

      consolidate();
    }


    size--;

    E minimum = z.getElement();
    elementsIndex.remove(minimum);

    return minimum;
  }

  
  public E remove() {


    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    return poll();
  }

  private void consolidate() {
    if (isEmpty()) {
      return;
    }




    int arraySize = ((int) floor(log(size) / LOG_PHI));


    List<FibonacciHeapNode<E>> nodeSequence = new ArrayList<FibonacciHeapNode<E>>(arraySize);
    for (int i = 0; i < arraySize; i++) {

      nodeSequence.add(i, null);
    }

    int numRoots = 0;



    FibonacciHeapNode<E> x = minimumNode;

    if (x != null) {
      numRoots++;
      x = x.getRight();

      while (x != minimumNode) {
        numRoots++;
        x = x.getRight();
      }
    }

    while (numRoots > 0) {

      int degree = x.getDegree();
      FibonacciHeapNode<E> next = x.getRight();


      while (nodeSequence.get(degree) != null) {

        FibonacciHeapNode<E> y = nodeSequence.get(degree);


        if (compare(x, y) > 0) {

          FibonacciHeapNode<E> pointer = y;
          y = x;
          x = pointer;
        }


        link(y, x);


        nodeSequence.set(degree, null);


        degree++;
      }


      nodeSequence.set(degree, x);

      x = next;
      numRoots--;
    }


    minimumNode = null;


    for (FibonacciHeapNode<E> pointer : nodeSequence) {
      if (pointer == null) {
        continue;
      }
      if (minimumNode == null) {
        minimumNode = pointer;
      }



      if (minimumNode != null) {

        moveToRoot(pointer);
      }
    }
  }

  private void link(FibonacciHeapNode<E> y, FibonacciHeapNode<E> x) {

    y.getLeft().setRight(y.getRight());
    y.getRight().setLeft(y.getLeft());

    y.setParent(x);

    if (x.getChild() == null) {

      x.setChild(y);
      y.setRight(y);
      y.setLeft(y);
    } else {
      y.setLeft(x.getChild());
      y.setRight(x.getChild().getRight());
      x.getChild().setRight(y);
      y.getRight().setLeft(y);
    }

    x.incraeseDegree();


    y.setMarked(false);
    markedNodes++;
  }

  private void cut(FibonacciHeapNode<E> x, FibonacciHeapNode<E> y) {

    moveToRoot(x);


    y.decraeseDegree();

    x.setParent(null);


    x.setMarked(false);
    markedNodes--;
  }

  private void cascadingCut(FibonacciHeapNode<E> y) {

    FibonacciHeapNode<E> z = y.getParent();


    if (z != null) {

      if (!y.isMarked()) {

        y.setMarked(true);
        markedNodes++;
      } else {

        cut(y, z);

        cascadingCut(z);
      }
    }
  }

  public int potential() {
    return trees + 2 * markedNodes;
  }

  private int compare(FibonacciHeapNode<E> o1, FibonacciHeapNode<E> o2) {
    if (comparator != null) {
      return comparator.compare(o1.getElement(), o2.getElement());
    }
    @SuppressWarnings("unchecked")
    Comparable<? super E> o1Comparable = (Comparable<? super E>) o1.getElement();
    return o1Comparable.compareTo(o2.getElement());
  }

  
  public String toString() {
    if (minimumNode == null) {
      return "FibonacciHeap=[]";
    }


    Stack<FibonacciHeapNode<E>> stack = new Stack<FibonacciHeapNode<E>>();
    stack.push(minimumNode);

    StringBuilder buf = new StringBuilder("FibonacciHeap=[");


    while (!stack.empty()) {
      FibonacciHeapNode<E> curr = stack.pop();
      buf.append(curr);
      buf.append(", ");

      if (curr.getChild() != null) {
        stack.push(curr.getChild());
      }

      FibonacciHeapNode<E> start = curr;
      curr = curr.getRight();

      while (curr != start) {
        buf.append(curr);
        buf.append(", ");

        if (curr.getChild() != null) {
          stack.push(curr.getChild());
        }

        curr = curr.getRight();
      }
    }

    buf.append(']');

    return buf.toString();
  }
}
