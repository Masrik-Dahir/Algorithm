
package other;

public class BitManipulations {


  public static int setBit(int set, int i) {
    return set | (1 << i);
  }


  public static boolean isSet(int set, int i) {
    return (set & (1 << i)) != 0;
  }


  public static int clearBit(int set, int i) {
    return set & ~(1 << i);
  }


  public static int toggleBit(int set, int i) {
    return set ^ (1 << i);
  }


  public static int setAll(int n) {
    return (1 << n) - 1;
  }


  public static boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
  }
}
