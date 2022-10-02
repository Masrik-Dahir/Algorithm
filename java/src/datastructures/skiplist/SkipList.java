
package datastructures.skiplist;

import java.util.Random;

class SkipList {
  private Random rand = new Random();
  private int height;
  private Node head;
  private Node tail;

  
  public SkipList(int height, int minValue, int maxValue) {

    this.height = height;
    head = new Node(minValue);
    tail = new Node(maxValue);
    Node currLeft = head;
    Node currRight = tail;

    for (int i = 1; i < height; i++) {
      setHeadTail(height - i, currLeft, currRight);

      currLeft.down = new Node(currLeft.value);
      currLeft.down.up = currLeft;
      currRight.down = new Node(currRight.value);
      currRight.down.up = currRight;
      currLeft = currLeft.down;
      currRight = currRight.down;
    }

    setHeadTail(0, currLeft, currRight);
  }

  private static void setHeadTail(int height, Node nLeft, Node nRight) {
    nLeft.right = nRight;
    nRight.left = nLeft;
    nLeft.index = 0;
    nRight.index = 1;
    nLeft.height = height;
    nRight.height = height;
  }

  public int size() {
    return this.tail.index + 1;
  }

  
  public boolean find(int num) {
    return (search(num).value == num);
  }


  private Node search(int num) {
    return search(num, this.head);
  }


  private Node search(int num, Node node) {

    if (node.compareTo(num) < 0) {
      if (node.down != null) return search(num, node.down);
      else if (node.right != null && node.right.compareTo(num) <= 0) return search(num, node.right);
    }
    return node;
  }

  
  public boolean insert(int num) {
    if (num < head.value || num > tail.value) return false;
    Node node = search(num);
    if (node.value == num) return false;
    int nodeHeight = 0;

    while (rand.nextBoolean() && nodeHeight < (height - 1)) {
      nodeHeight++;
    }
    insert(node, new Node(num), null, nodeHeight, node.index + 1);
    return true;
  }

  
  private void insert(Node startNode, Node insertNode, Node lower, int insertHeight, int distance) {
    if (startNode.height <= insertHeight) {
      insertNode.left = startNode;
      insertNode.right = startNode.right;

      if (lower != null) {
        lower.up = insertNode;
        insertNode.down = lower;
      }

      else if (startNode.height == 0) {
        increaseRank(insertNode.right);
      }
      startNode.right.left = insertNode;
      startNode.right = insertNode;
      insertNode.height = startNode.height;
      insertNode.index = distance;
      Node curr = startNode;
      while (curr.up == null && curr.left != null) {
        curr = curr.left;
      }
      if (curr.up != null) {
        curr = curr.up;
        insert(curr, new Node(insertNode.value), insertNode, insertHeight, distance);
      }
    }
  }

  
  public boolean remove(int num) {
    if (num == head.value || num == tail.value) return false;

    Node node = search(num);
    if (node.value != num) return false;

    decreaseRank(node.right);


    while (node.up != null) {
      node.left.right = node.right;
      node.right.left = node.left;
      node = node.up;
    }
    node.left.right = node.right;
    node.right.left = node.left;

    return true;
  }


  private void decreaseRank(Node startNode) {
    modifyRank(startNode, -1);
  }


  private void increaseRank(Node startNode) {
    modifyRank(startNode, 1);
  }


  private void modifyRank(Node startNode, int change) {
    Node node = startNode;

    while (startNode != null) {
      node = startNode;

      while (node.up != null) {
        node.index += change;
        node = node.up;
      }
      node.index += change;
      startNode = startNode.right;
    }
  }

  
  public int getIndex(int num) {
    Node node = search(num);
    return num == node.value ? node.index : -1;
  }

  class Node implements Comparable<Node> {
    Node left;
    Node right;
    Node up;
    Node down;
    int height;
    int index;
    int value;

    public Node(int value) {
      this.value = value;
    }

    public int compareTo(Node n2) {
      return Integer.compare(this.value, n2.value);
    }

    public int compareTo(int num) {
      return Integer.compare(this.value, num);
    }
  }
}
