
package strings;

public class BoothsAlgorithm {





  public static int leastCyclicRotation(String s) {
    s += s;
    int[] f = new int[s.length()];
    java.util.Arrays.fill(f, -1);
    int k = 0;
    for (int j = 1; j < s.length(); j++) {
      char sj = s.charAt(j);
      int i = f[j - k - 1];
      while (i != -1 && sj != s.charAt(k + i + 1)) {
        if (sj < s.charAt(k + i + 1)) k = j - i - 1;
        i = f[i];
      }
      if (sj != s.charAt(k + i + 1)) {
        if (sj < s.charAt(k)) k = j;
        f[j - k] = -1;
      } else f[j - k] = i + 1;
    }
    return k;
  }

  public static void main(String[] args) {

    String s = "abcde";
    int index = leastCyclicRotation(s);


    System.out.println(index);

    s = "cdeab";
    index = leastCyclicRotation(s);



    System.out.println(index);
  }
}
