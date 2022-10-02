
package strings;

public class ManachersAlgorithm {










  public static int[] manachers(char[] str) {
    char[] arr = preProcess(str);
    int n = arr.length, c = 0, r = 0;
    int[] p = new int[n];
    for (int i = 1; i < n - 1; i++) {
      int invI = 2 * c - i;
      p[i] = r > i ? Math.min(r - i, p[invI]) : 0;
      while (arr[i + 1 + p[i]] == arr[i - 1 - p[i]]) p[i]++;
      if (i + p[i] > r) {
        c = i;
        r = i + p[i];
      }
    }
    return p;
  }




  private static char[] preProcess(char[] str) {
    char[] arr = new char[str.length * 2 + 3];
    arr[0] = '^';
    for (int i = 0; i < str.length; i++) {
      arr[i * 2 + 1] = '#';
      arr[i * 2 + 2] = str[i];
    }
    arr[arr.length - 2] = '#';
    arr[arr.length - 1] = '$';
    return arr;
  }




  public static java.util.TreeSet<String> findPalindromeSubstrings(String str) {
    char[] S = str.toCharArray();
    int[] centers = manachers(S);
    java.util.TreeSet<String> palindromes = new java.util.TreeSet<>();

    for (int i = 0; i < centers.length; i++) {
      int diameter = centers[i];
      if (diameter >= 1) {


        if (i % 2 == 1) {
          while (diameter > 1) {
            int index = (i - 1) / 2 - diameter / 2;
            palindromes.add(new String(S, index, diameter));
            diameter -= 2;
          }

        } else {
          while (diameter >= 1) {
            int index = (i - 2) / 2 - (diameter - 1) / 2;
            palindromes.add(new String(S, index, diameter));
            diameter -= 2;
          }
        }
      }
    }
    return palindromes;
  }

  public static void main(String[] args) {
    String s = "abbaabba";


    System.out.println(findPalindromeSubstrings(s));
  }
}
