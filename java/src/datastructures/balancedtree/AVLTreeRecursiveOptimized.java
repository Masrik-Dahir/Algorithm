package datastructures.balancedtree;

public class AVLTreeRecursiveOptimized<T extends Comparable<T>> implements Iterable<T> {

  public class Node {

    public int bf;

    public T value;


    public int height;


    public Node left, right;

    public Node(T value) {
      this.value = value;
    }
  }


  public Node root;


  private int nodeCount = 0;





  private Node TOKEN = new Node(null);




  public int height() {
    if (root == null) return 0;
    return root.height;
  }


  public int size() {
    return nodeCount;
  }


  public boolean isEmpty() {
    return size() == 0;
  }


  public boolean contains(T value) {

    Node node = root;
    while (node != null) {


      int cmp = value.compareTo(node.value);

      if (cmp < 0) node = node.left;
      else if (cmp > 0) node = node.right;
      else return true;
    }
    return false;
  }


  public boolean insert(T value) {
    if (value == null) return false;
    Node newRoot = insert(root, value);
    boolean insertedNode = (newRoot != TOKEN);
    if (insertedNode) {
      nodeCount++;
      root = newRoot;
    }
    return insertedNode;
  }




  private Node insert(Node node, T value) {


    if (node == null) return new Node(value);


    int cmp = value.compareTo(node.value);


    if (cmp < 0) {
      Node newLeftNode = insert(node.left, value);
      if (newLeftNode == TOKEN) return TOKEN;
      node.left = newLeftNode;


    } else if (cmp > 0) {
      Node newRightNode = insert(node.right, value);
      if (newRightNode == TOKEN) return TOKEN;
      node.right = newRightNode;


    } else return TOKEN;


    update(node);


    return balance(node);
  }


  private void update(Node node) {

    int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
    int rightNodeHeight = (node.right == null) ? -1 : node.right.height;


    node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);


    node.bf = rightNodeHeight - leftNodeHeight;
  }


  private Node balance(Node node) {


    if (node.bf == -2) {


      if (node.left.bf <= 0) {
        return leftLeftCase(node);


      } else {
        return leftRightCase(node);
      }


    } else if (node.bf == +2) {


      if (node.right.bf >= 0) {
        return rightRightCase(node);


      } else {
        return rightLeftCase(node);
      }
    }


    return node;
  }

  private Node leftLeftCase(Node node) {
    return rightRotation(node);
  }

  private Node leftRightCase(Node node) {
    node.left = leftRotation(node.left);
    return leftLeftCase(node);
  }

  private Node rightRightCase(Node node) {
    return leftRotation(node);
  }

  private Node rightLeftCase(Node node) {
    node.right = rightRotation(node.right);
    return rightRightCase(node);
  }

  private Node leftRotation(Node node) {
    Node newParent = node.right;
    node.right = newParent.left;
    newParent.left = node;
    update(node);
    update(newParent);
    return newParent;
  }

  private Node rightRotation(Node node) {
    Node newParent = node.left;
    node.left = newParent.right;
    newParent.right = node;
    update(node);
    update(newParent);
    return newParent;
  }


  public boolean remove(T elem) {

    Node newRoot = remove(root, elem);
    boolean removedNode = (newRoot != TOKEN) || (newRoot == null);

    if (removedNode) {
      root = newRoot;
      nodeCount--;
      return true;
    }

    return false;
  }




  private Node remove(Node node, T elem) {


    if (node == null) return TOKEN;

    int cmp = elem.compareTo(node.value);



    if (cmp < 0) {
      Node newLeftNode = remove(node.left, elem);
      if (newLeftNode == TOKEN) return TOKEN;
      node.left = newLeftNode;



    } else if (cmp > 0) {
      Node newRightNode = remove(node.right, elem);
      if (newRightNode == TOKEN) return TOKEN;
      node.right = newRightNode;


    } else {




      if (node.left == null) {
        return node.right;




      } else if (node.right == null) {
        return node.left;






      } else {


        if (node.left.height > node.right.height) {


          T successorValue = findMax(node.left);
          node.value = successorValue;


          Node replacement = remove(node.left, successorValue);
          if (replacement == TOKEN) return TOKEN;
          node.left = replacement;

        } else {


          T successorValue = findMin(node.right);
          node.value = successorValue;




          Node replacement = remove(node.right, successorValue);
          if (replacement == TOKEN) return TOKEN;
          node.right = replacement;
        }
      }
    }


    update(node);


    return balance(node);
  }


  private T findMin(Node node) {
    while (node.left != null) node = node.left;
    return node.value;
  }


  private T findMax(Node node) {
    while (node.right != null) node = node.right;
    return node.value;
  }


  public java.util.Iterator<T> iterator() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>() {
      Node trav = root;

      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next() {

        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

        while (trav != null && trav.left != null) {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != null) {
          stack.push(node.right);
          trav = node.right;
        }

        return node.value;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }




  boolean validateBSTInvarient(Node node) {
    if (node == null) return true;
    T val = node.value;
    boolean isValid = true;
    if (node.left != null) isValid = isValid && node.left.value.compareTo(val) < 0;
    if (node.right != null) isValid = isValid && node.right.value.compareTo(val) > 0;
    return isValid && validateBSTInvarient(node.left) && validateBSTInvarient(node.right);
  }
}
