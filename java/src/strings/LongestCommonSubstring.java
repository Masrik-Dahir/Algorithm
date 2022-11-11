
package strings;

import java.util.*;

public class LongestCommonSubstring {

  public static void main(String[] args) {
    String[] strings = new String[] {"abcde", "habcab", "ghabcdf"};


    List<Integer> sentinelIndexes = new ArrayList<>();
    String t = addSentinels(strings, sentinelIndexes);
    SuffixArray sa = new SuffixArrayImpl(t);
    sa.toString(sentinelIndexes);


    int k = 2;
    LcsSolver solver = new LcsSolver(strings);
    System.out.println("Longest common substrings: " + solver.getLongestCommonSubstrings(k));
  }


  private static String addSentinels(String[] s, List<Integer> sentinelIndexes) {
    int token = 35;
    String t = "";
    for (String string : s) {
      t += string;
      t += (char) token;
      token++;
      if (sentinelIndexes != null) sentinelIndexes.add(t.length());
    }
    return t;
  }

  public abstract static class SuffixArray {


    protected final int N;


    protected int[] T;


    protected int[] sa;


    protected int[] lcp;

    private boolean constructedSa = false;
    private boolean constructedLcpArray = false;

    public SuffixArray(int[] text) {
      if (text == null) throw new IllegalArgumentException("Text cannot be null.");
      this.T = text;
      this.N = text.length;
    }

    public int getTextLength() {
      return T.length;
    }


    public int[] getSa() {
      buildSuffixArray();
      return sa;
    }


    public int[] getLcpArray() {
      buildLcpArray();
      return lcp;
    }


    protected void buildSuffixArray() {
      if (constructedSa) return;
      construct();
      constructedSa = true;
    }


    protected void buildLcpArray() {
      if (constructedLcpArray) return;
      buildSuffixArray();
      kasai();
      constructedLcpArray = true;
    }

    protected static int[] toIntArray(String s) {
      if (s == null) return null;
      int[] t = new int[s.length()];
      for (int i = 0; i < s.length(); i++) t[i] = s.charAt(i);
      return t;
    }



    protected abstract void construct();



    private void kasai() {
      lcp = new int[N];
      int[] inv = new int[N];
      for (int i = 0; i < N; i++) inv[sa[i]] = i;
      for (int i = 0, len = 0; i < N; i++) {
        if (inv[i] > 0) {
          int k = sa[inv[i] - 1];
          while ((i + len < N) && (k + len < N) && T[i + len] == T[k + len]) len++;
          lcp[inv[i]] = len;
          if (len > 0) len--;
        }
      }
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("------i------SA------LCP--------Suffix\n");

      for (int i = 0; i < N; i++) {
        int suffixLen = N - sa[i];
        char[] string = new char[suffixLen];
        for (int j = sa[i], k = 0; j < N; j++, k++) string[k] = (char) (T[j]);
        String suffix = new String(string);
        String formattedStr = String.format("% 7d % 7d % 7d %s\n", i, sa[i], lcp[i], suffix);
        sb.append(formattedStr);
      }
      return sb.toString();
    }


    private static Color findColorFromPos(int pos, List<Integer> sentinelIndexes) {
      Color[] colors = {
        Color.GREEN,
        Color.RED,
        Color.BLUE,
        Color.YELLOW,
        Color.MAGENTA,
        Color.CYAN,
        Color.WHITE,
        Color.BLACK_BACKGROUND_BRIGHT
      };
      int colorIndex = 0;
      for (int tokenIndex : sentinelIndexes) {
        if (tokenIndex <= pos) colorIndex++;
      }
      if (colorIndex >= colors.length) {
        throw new IllegalStateException("Too many strings, not enough terminal colors :/");
      }
      return colors[colorIndex];
    }


    public void toString(List<Integer> sentinelIndexes) {
      System.out.println("------i------SA------LCP--------Suffix");
      buildLcpArray();
      for (int i = 0; i < N; i++) {
        int suffixLen = N - sa[i];
        char[] string = new char[suffixLen];
        for (int j = sa[i], k = 0; j < N; j++, k++) string[k] = (char) (T[j]);
        String suffix = new String(string);

        System.out.print(findColorFromPos(sa[i], sentinelIndexes));
        String formattedStr = String.format("% 7d % 7d % 7d %s", i, sa[i], lcp[i], suffix);
        System.out.println(formattedStr + Color.RESET);
      }
    }





    private enum Color {
      RESET("\033[0m"),

      BLACK("\033[0;30m"),
      RED("\033[0;31m"),
      GREEN("\033[0;32m"),
      YELLOW("\033[0;33m"),
      BLUE("\033[0;34m"),
      MAGENTA("\033[0;35m"),
      CYAN("\033[0;36m"),
      WHITE("\033[0;37m"),
      BLACK_BACKGROUND_BRIGHT("\033[0;100m");

      private final String code;

      Color(String code) {
        this.code = code;
      }

      @Override
      public String toString() {
        return code;
      }
    }
  }

  static class SuffixArrayImpl extends SuffixArray {


    static class SuffixRankTuple implements Comparable<SuffixRankTuple> {
      int firstHalf, secondHalf, originalIndex;


      @Override
      public int compareTo(SuffixRankTuple other) {
        int cmp = Integer.compare(firstHalf, other.firstHalf);
        if (cmp == 0) return Integer.compare(secondHalf, other.secondHalf);
        return cmp;
      }
    }

    public SuffixArrayImpl(String text) {
      super(toIntArray(text));
    }

    public SuffixArrayImpl(int[] text) {
      super(text);
    }


    @Override
    protected void construct() {
      sa = new int[N];



      int[][] suffixRanks = new int[2][N];
      SuffixRankTuple[] ranks = new SuffixRankTuple[N];


      for (int i = 0; i < N; i++) {
        suffixRanks[0][i] = T[i];
        ranks[i] = new SuffixRankTuple();
      }


      for (int pos = 1; pos < N; pos *= 2) {
        for (int i = 0; i < N; i++) {
          SuffixRankTuple suffixRank = ranks[i];
          suffixRank.firstHalf = suffixRanks[0][i];
          suffixRank.secondHalf = i + pos < N ? suffixRanks[0][i + pos] : -1;
          suffixRank.originalIndex = i;
        }


        Arrays.sort(ranks);

        int newRank = 0;
        suffixRanks[1][ranks[0].originalIndex] = 0;

        for (int i = 1; i < N; i++) {
          SuffixRankTuple lastSuffixRank = ranks[i - 1];
          SuffixRankTuple currSuffixRank = ranks[i];


          if (currSuffixRank.firstHalf != lastSuffixRank.firstHalf
              || currSuffixRank.secondHalf != lastSuffixRank.secondHalf) newRank++;

          suffixRanks[1][currSuffixRank.originalIndex] = newRank;
        }


        suffixRanks[0] = suffixRanks[1];


        if (newRank == N - 1) break;
      }


      for (int i = 0; i < N; i++) {
        sa[i] = ranks[i].originalIndex;
        ranks[i] = null;
      }


      suffixRanks[0] = suffixRanks[1] = null;
      suffixRanks = null;
      ranks = null;
    }
  }

  public static class LcsSolver {


    int k, numSentinels, textLength;
    String[] strings;


    int shift, lcsLen;
    int lowestAsciiValue;
    int highestAsciiValue;
    int[] imap, text, sa, lcp;


    TreeSet<String> lcss;

    private static final boolean DEBUG_MODE = false;


    public LcsSolver(String[] strings) {
      if (strings == null || strings.length <= 1)
        throw new IllegalArgumentException("Invalid strings array provided.");
      this.strings = strings;
    }

    private void init() {
      shift = lcsLen = 0;
      lowestAsciiValue = Integer.MAX_VALUE;
      highestAsciiValue = Integer.MIN_VALUE;
      numSentinels = strings.length;
      lcss = new TreeSet<>();
      imap = text = sa = lcp = null;

      computeTextLength(strings);
      buildReverseColorMapping();
      computeShift();
      buildText();
    }

    private void computeTextLength(String[] strings) {
      textLength = 0;
      for (String str : strings) textLength += str.length();
      textLength += numSentinels;
    }



    private void buildReverseColorMapping() {
      imap = new int[textLength];
      for (int i = 0, k = 0; i < strings.length; i++) {
        String str = strings[i];
        for (int j = 0; j < str.length(); j++) {
          int asciiVal = str.charAt(j);
          if (asciiVal < lowestAsciiValue) lowestAsciiValue = asciiVal;
          if (asciiVal > highestAsciiValue) highestAsciiValue = asciiVal;
          imap[k++] = i;
        }

        imap[k++] = i;
      }
    }

    private void verifyMinAndMaxAsciiValues() {
      if (lowestAsciiValue == Integer.MAX_VALUE || highestAsciiValue == Integer.MIN_VALUE)
        throw new IllegalStateException("Must set min/max ascii values!");
    }

    private void computeShift() {
      verifyMinAndMaxAsciiValues();
      shift = numSentinels - lowestAsciiValue;
    }






    private void buildText() {
      verifyMinAndMaxAsciiValues();
      text = new int[textLength];
      int sentinel = 0;

      for (int i = 0, k = 0; i < strings.length; i++) {
        String str = strings[i];
        for (int j = 0; j < str.length(); j++) {
          text[k++] = ((int) str.charAt(j)) + shift;
          if (!(numSentinels <= text[k - 1]
              && text[k - 1] <= (numSentinels + highestAsciiValue - lowestAsciiValue))) {
            throw new IllegalStateException(
                String.format(
                    "Unexpected character range. Was: %d, wanted between [%d, %d]",
                    text[k - 1],
                    numSentinels,
                    (numSentinels + highestAsciiValue - lowestAsciiValue)));
          }
        }
        text[k++] = sentinel++;
        if (!(0 <= text[k - 1] && text[k - 1] < numSentinels)) {
          throw new IllegalStateException(
              String.format(
                  "Unexpected character range. Was: %d, wanted between [%d, %d)",
                  text[k - 1], 0, numSentinels));
        }
      }
    }



    private boolean enoughUniqueColorsInWindow(int lo, int hi) {

      Set<Integer> set = new HashSet<>();
      for (int i = lo; i <= hi; i++) {
        set.add(imap[sa[i]]);
      }

      return set.size() == k;
    }


    private String retrieveString(int i, int len) {
      char[] s = new char[len];
      for (int j = 0; j < len; j++) s[j] = (char) (text[i + j] - shift);
      return new String(s);
    }

    private void log(String s) {
      if (DEBUG_MODE) {
        System.out.println(s);
      }
    }

    private void addLcs(int lo, int hi, int windowLcp) {
      if (hi - lo + 1 < k) {
        log(
            String.format(
                "lo: %d, hi: %d. Too small range. lo: %d, hi: %d, k: %d, hi - lo + 1 < k",
                lo, hi, lo, hi, k));
        return;
      }
      if (windowLcp == 0) {
        log("LCP is 0");
        return;
      }
      if (!enoughUniqueColorsInWindow(lo, hi)) {
        log(
            String.format(
                "lo: %d, hi: %d. Not enough unique colors in range [%d, %d]", lo, hi, lo, hi));
        return;
      }
      if (windowLcp > lcsLen) {
        lcsLen = windowLcp;
        lcss.clear();
      }
      if (windowLcp == lcsLen) {
        lcss.add(retrieveString(sa[lo], windowLcp));
      }
    }

    public TreeSet<String> getLongestCommonSubstrings(int k) {
      if (k < 2) throw new IllegalArgumentException("k must be greater than or equal to 2");
      this.k = k;
      solve();
      return lcss;
    }

    private void solve() {
      init();

      SuffixArray suffixArray = new SuffixArrayImpl(text);
      sa = suffixArray.getSa();
      lcp = suffixArray.getLcpArray();


      CompactMinSegmentTree tree = new CompactMinSegmentTree(lcp);

      int lo = numSentinels;
      int hi = numSentinels;

      while (true) {


        boolean shrinkWindow = (hi == textLength - 1) ? true : enoughUniqueColorsInWindow(lo, hi);

        if (shrinkWindow) {
          lo++;
        } else {
          hi++;
        }

        if (lo == textLength - 1) break;



        if (lo == hi) continue;

        int windowLcp = tree.query(lo + 1, hi + 1);
        addLcs(lo, hi, windowLcp);
      }
    }
  }

  static class SlidingWindowMinimum {
    int[] values;
    int N, lo, hi;

    Deque<Integer> deque = new ArrayDeque<>();

    public SlidingWindowMinimum(int[] values) {
      if (values == null) throw new IllegalArgumentException();
      this.values = values;
      N = values.length;
    }


    public void advance() {

      while (!deque.isEmpty() && values[deque.peekLast()] > values[hi]) deque.removeLast();


      deque.addLast(hi);


      hi++;
    }


    public void shrink() {

      lo++;



      while (!deque.isEmpty() && deque.peekFirst() < lo) deque.removeFirst();
    }


    public int getMin() {
      if (lo >= hi) throw new IllegalStateException("Make sure lo < hi");
      return values[deque.peekFirst()];
    }
  }

  static class CompactMinSegmentTree {
    private int n;


    private int UNIQUE = 93136074;


    private int[] tree;

    public CompactMinSegmentTree(int size) {
      tree = new int[2 * (n = size)];
      Arrays.fill(tree, UNIQUE);
    }

    public CompactMinSegmentTree(int[] values) {
      this(values.length);
      for (int i = 0; i < n; i++) modify(i, values[i]);
    }


    private int function(int a, int b) {
      if (a == UNIQUE) return b;
      else if (b == UNIQUE) return a;
      return (a < b) ? a : b;
    }


    public void modify(int i, int value) {
      tree[i + n] = function(tree[i + n], value);
      for (i += n; i > 1; i >>= 1) {
        tree[i >> 1] = function(tree[i], tree[i ^ 1]);
      }
    }


    public int query(int l, int r) {
      int res = UNIQUE;
      for (l += n, r += n; l < r; l >>= 1, r >>= 1) {
        if ((l & 1) != 0) res = function(res, tree[l++]);
        if ((r & 1) != 0) res = function(res, tree[--r]);
      }
      if (res == UNIQUE) {
        throw new IllegalStateException("UNIQUE should not be the return value.");
      }
      return res;
    }
  }
}
