
package strings;

public class KMP {



  public static java.util.List<Integer> kmp(String txt, String pat) {
    java.util.List<Integer> matches = new java.util.ArrayList<>();
    if (txt == null || pat == null) return matches;

    int m = pat.length(), n = txt.length(), i = 0, j = 0;
    if (m > n) return matches;

    int[] arr = kmpHelper(pat, m);

    while (i < n) {
      if (pat.charAt(j) == txt.charAt(i)) {
        j++;
        i++;
      }
      if (j == m) {
        matches.add(i - j);
        j = arr[j - 1];
      } else if (i < n && pat.charAt(j) != txt.charAt(i)) {
        if (j != 0) j = arr[j - 1];
        else i = i + 1;
      }
    }

    return matches;
  }



  private static int[] kmpHelper(String pat, int m) {
    int[] arr = new int[m];
    for (int i = 1, len = 0; i < m; ) {
      if (pat.charAt(i) == pat.charAt(len)) {
        arr[i++] = ++len;
      } else {
        if (len > 0) len = arr[len - 1];
        else i++;
      }
    }
    return arr;
  }

  public static void main(String[] args) {
    java.util.List<Integer> matches = kmp("abababa", "aba");
    System.out.println(matches);

    matches = kmp("abc", "abcdef");
    System.out.println(matches);

    matches = kmp("P@TTerNabcdefP@TTerNP@TTerNabcdefabcdefabcdefabcdefP@TTerN", "P@TTerN");
    System.out.println(matches);
  }
}
