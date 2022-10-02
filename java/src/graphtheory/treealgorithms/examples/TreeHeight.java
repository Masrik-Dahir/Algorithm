
package graphtheory.treealgorithms.examples;

public class TreeHeight {

  public static class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }



  public static int treeHeight1(TreeNode node) {
    if (node == null) return -1;
    return Math.max(treeHeight1(node.left), treeHeight1(node.right)) + 1;
  }



  public static int treeHeight2(TreeNode node) {

    if (node == null) return -1;
    if (isLeafNode(node)) return 0;
    return Math.max(treeHeight2(node.left), treeHeight2(node.right)) + 1;
  }

  private static boolean isLeafNode(TreeNode node) {
    return node.left == null && node.right == null;
  }

  

  public static void main(String[] args) {
    testTreeHeight1();
    testTreeHeight2();
  }

  private static void testTreeHeight1() {
    System.out.printf("Empty tree: %d\n", treeHeight1(null));
    System.out.printf("Singleton height: %d\n", treeHeight1(new TreeNode(0)));
    TreeNode root = makeTree();
    System.out.printf("Tree height: %d\n", treeHeight1(root));





  }

  private static void testTreeHeight2() {
    System.out.printf("Empty tree: %d\n", treeHeight2(null));
    System.out.printf("Singleton height: %d\n", treeHeight2(new TreeNode(0)));
    TreeNode root = makeTree();
    System.out.printf("Tree height: %d\n", treeHeight2(root));





  }








  private static TreeNode makeTree() {
    TreeNode node0 = new TreeNode(0);

    TreeNode node1 = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    node0.left = node1;
    node0.right = node2;

    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    node1.left = node3;
    node1.right = node4;

    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);
    node2.left = node5;
    node2.right = node6;

    TreeNode node7 = new TreeNode(7);
    TreeNode node8 = new TreeNode(8);
    node3.left = node7;
    node3.right = node8;

    return node0;
  }
}
