
package datastructures.kdtree;

public class GeneralKDTree<T extends Comparable<T>> {

  private int k;
  private KDNode<T> root;

  
  public GeneralKDTree(int dimensions) {
    if (dimensions <= 0)
      throw new IllegalArgumentException("Error: GeneralKDTree must have positive dimensions");
    k = dimensions;
    root = null;
  }

  
  public int getDimensions() {
    return k;
  }

  public T[] getRootPoint() {
    return (root == null) ? null : root.point;
  }

  

  public void insert(T[] toAdd) {

    KDNode<T> newNode = new KDNode<T>(toAdd);
    if (root == null) root = newNode;

    else insertRecursive(newNode, root, 0);
  }

  private void insertRecursive(KDNode<T> toAdd, KDNode<T> curr, int axis) {

    if ((toAdd.point[axis]).compareTo(curr.point[axis]) < 0) {
      if (curr.left == null) curr.left = toAdd;
      else insertRecursive(toAdd, curr.left, (++axis) % k);
    }

    else {
      if (curr.right == null) curr.right = toAdd;
      else insertRecursive(toAdd, curr.right, (++axis) % k);
    }
  }


  public boolean search(T[] element) {
    KDNode<T> elemNode = new KDNode<T>(element);
    return searchRecursive(elemNode, root, 0);
  }

  private boolean searchRecursive(KDNode<T> toSearch, KDNode<T> curr, int axis) {

    if (curr == null) return false;

    if ((curr.point).equals(toSearch.point)) return true;

    KDNode<T> nextNode =
        ((toSearch.point[axis]).compareTo(curr.point[axis]) < 0) ? curr.left : curr.right;
    return searchRecursive(toSearch, nextNode, (++axis) % k);
  }


  public T[] findMin(int dim) {
    if (dim < 0 || dim >= k) throw new IllegalArgumentException("Error: Dimension out of bounds");
    return findMinRecursive(dim, root, 0);
  }

  private T[] findMinRecursive(int dim, KDNode<T> curr, int axis) {

    if (curr == null) return null;

    if (dim == axis) {
      if (curr.left == null) return curr.point;
      return findMinRecursive(dim, curr.left, (axis + 1) % k);
    }

    if (curr.left == null && curr.right == null) return curr.point;

    T[] leftSubTree = findMinRecursive(dim, curr.left, (axis + 1) % k);
    T[] rightSubTree = findMinRecursive(dim, curr.right, (axis + 1) % k);
    T[] minSubTree;

    if (leftSubTree == null || rightSubTree == null) {
      minSubTree = (rightSubTree == null) ? leftSubTree : rightSubTree;
    }

    else {
      minSubTree =
          ((leftSubTree[dim]).compareTo(rightSubTree[dim]) < 0) ? leftSubTree : rightSubTree;
    }


    T[] min = ((curr.point[dim]).compareTo(minSubTree[dim]) < 0) ? curr.point : minSubTree;
    return min;
  }


  public T[] delete(T[] toRemove) {

    if (!search(toRemove)) return null;

    if (toRemove.equals(root.point)) return deleteRecursiveRoot();

    KDNode<T> removeElem = new KDNode<T>(toRemove);
    return deleteRecursiveSearch(removeElem, root, 0);
  }

  private T[] deleteRecursiveRoot() {

    T[] replacedPoint = root.point;

    if (root.left == null && root.right == null) {
      root = null;
      return replacedPoint;
    }

    else if (root.right != null) {
      root.point = findMinRecursive(0, root.right, 1 % k);
      deleteRecursiveSearch(new KDNode<T>(root.point), root, 0);
      return replacedPoint;
    }

    else {
      root.point = findMinRecursive(0, root.left, 1 % k);
      deleteRecursiveSearch(new KDNode<T>(root.point), root, 0);
      root.right = root.left;
      root.left = null;
      return replacedPoint;
    }
  }

  private T[] deleteRecursiveSearch(KDNode<T> toRemove, KDNode<T> curr, int axis) {

    if (curr.right != null && (toRemove.point).equals(curr.right.point)) {
      T[] removed = deleteRecursiveExtract(toRemove, curr.right, (axis + 1) % k);
      if (removed == null) curr.right = null;
      return toRemove.point;
    } else if (curr.left != null && (toRemove.point).equals(curr.left.point)) {
      T[] removed = deleteRecursiveExtract(toRemove, curr.left, (axis + 1) % k);
      if (removed == null) curr.left = null;
      return toRemove.point;
    }

    else {
      KDNode<T> nextNode =
          ((toRemove.point[axis]).compareTo(curr.point[axis]) < 0) ? curr.left : curr.right;
      return deleteRecursiveSearch(toRemove, nextNode, (axis + 1) % k);
    }
  }

  private T[] deleteRecursiveExtract(KDNode<T> toRemove, KDNode<T> curr, int axis) {

    T[] replacedPoint = curr.point;

    if (curr.left == null && curr.right == null) return null;

    else if (curr.right != null) {
      curr.point = findMinRecursive(axis, curr.right, (axis + 1) % k);
      deleteRecursiveSearch(new KDNode<T>(curr.point), curr, axis);
      return replacedPoint;
    }

    else {
      curr.point = findMinRecursive(axis, curr.left, (axis + 1) % k);
      deleteRecursiveSearch(new KDNode<T>(curr.point), curr, axis);
      curr.right = curr.left;
      curr.left = null;
      return replacedPoint;
    }
  }

  
  private class KDNode<E extends Comparable<E>> {

    private E[] point;
    private KDNode<E> left;
    private KDNode<E> right;

    public KDNode(E[] coords) {
      if (coords == null) throw new IllegalArgumentException("Error: Null coordinate set passed");
      if (coords.length != k)
        throw new IllegalArgumentException(
            "Error: Expected " + k + "dimensions, but given " + coords.length);
      point = coords;
      left = null;
      right = null;
    }
  }
}
