
package strings;

import java.math.*;
import java.util.*;

public class RabinKarp {






  private static final long ALPHABET_BASE = 95 + 1;
  private static final long[] ALPHABET = new long[127];
  private static final BigInteger BIG_ALPHA = new BigInteger(String.valueOf(ALPHABET_BASE));





  private static final long[] MODS = {10_000_019, 10_000_079, 10_000_103};
  private static final int N_HASHES = MODS.length;
  private static final BigInteger[] BIG_MODS = new BigInteger[N_HASHES];
  private static final long[] MOD_INVERSES = new long[N_HASHES];

  static {


    for (int i = 32, n = 1; i < ALPHABET.length; i++, n++) ALPHABET[i] = n;


    for (int i = 0; i < N_HASHES; i++) {
      BigInteger mod = new BigInteger(String.valueOf(MODS[i]));
      MOD_INVERSES[i] = BIG_ALPHA.modInverse(mod).longValue();
      BIG_MODS[i] = mod;
    }
  }


  public static void main(String[] args) {

    String str = "P@TTerNabcdefP@TTerNP@TTerNabcdefabcdefabcdefabcdefP@TTerN";
    String pat = "P@TTerN";

    System.out.println(rabinKarp(str, pat));


    str = "ababababa";
    pat = "a";

    System.out.println(rabinKarp(str, pat));


    str = "123";
    pat = "123456";

    System.out.println(rabinKarp(str, pat));


    str = "123456";
    pat = "123456";

    System.out.println(rabinKarp(str, pat));
  }



  public static List<Integer> rabinKarp(String text, String pattern) {

    List<Integer> matches = new ArrayList<>();
    if (text == null || pattern == null) return matches;


    final int PL = pattern.length(), TL = text.length();
    if (PL > TL) return matches;


    long[] patternHash = computeHash(pattern);
    long[] rollingHash = computeHash(text.substring(0, PL));

    final BigInteger BIG_PL = new BigInteger(String.valueOf(PL));
    final long[] POWERS = new long[N_HASHES];
    for (int i = 0; i < N_HASHES; i++)
      POWERS[i] = BIG_ALPHA.modPow(BIG_PL, BIG_MODS[i]).longValue();

    for (int i = PL - 1; ; ) {

      if (Arrays.equals(patternHash, rollingHash)) {
        matches.add(i - PL + 1);
      }
      if (++i == TL) return matches;

      char firstValue = text.charAt(i - PL);
      char lastValue = text.charAt(i);


      for (int j = 0; j < patternHash.length; j++) {
        rollingHash[j] = addRight(rollingHash[j], lastValue, j);
        rollingHash[j] = removeLeft(rollingHash[j], POWERS[j], firstValue, j);
      }
    }
  }




  public static List<Integer> rabinKarpBackwards(String text, String pattern) {

    List<Integer> matches = new ArrayList<>();
    if (text == null || pattern == null) return matches;


    final int PL = pattern.length(), TL = text.length();
    if (PL > TL) return matches;

    long[] patternHash = computeHash(pattern);
    long[] rollingHash = computeHash(text.substring(TL - PL, TL));

    final BigInteger BIG_PL = new BigInteger(String.valueOf(PL));
    final long[] POWERS = new long[N_HASHES];
    for (int i = 0; i < N_HASHES; i++)
      POWERS[i] = BIG_ALPHA.modPow(BIG_PL, BIG_MODS[i]).longValue();

    for (int i = TL - PL; ; ) {

      if (Arrays.equals(patternHash, rollingHash)) {
        matches.add(i);
      }
      if (--i < 0) return matches;

      char firstValue = text.charAt(i);
      char lastValue = text.charAt(i + PL);


      for (int j = 0; j < patternHash.length; j++) {
        rollingHash[j] = addLeft(rollingHash[j], POWERS[j], firstValue, j);
        rollingHash[j] = removeRight(rollingHash[j], lastValue, j);
      }
    }
  }


  private static long addRight(long rollingHash, char lastValue, int modIndex) {
    rollingHash = (rollingHash * ALPHABET_BASE + ALPHABET[lastValue]) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }




  private static long removeRight(long rollingHash, char lastValue, int modIndex) {
    rollingHash =
        (((rollingHash - ALPHABET[lastValue]) % MODS[modIndex]) + MODS[modIndex]) % MODS[modIndex];
    return (rollingHash * MOD_INVERSES[modIndex]) % MODS[modIndex];
  }







  private static long addLeft(
      long rollingHash, long alphabetBasePower, char firstValue, int modIndex) {
    rollingHash = (ALPHABET[firstValue] * alphabetBasePower + rollingHash) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }







  private static long removeLeft(
      long rollingHash, long alphabetBasePower, char firstValue, int modIndex) {
    rollingHash = (rollingHash - ALPHABET[firstValue] * alphabetBasePower) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }


  public static long[] computeHash(String str) {

    long[] rollingHashes = new long[N_HASHES];
    for (int k = 0; k < N_HASHES; k++)
      for (int i = 0; i < str.length(); i++)
        rollingHashes[k] = addRight(rollingHashes[k], str.charAt(i), k);
    return rollingHashes;
  }
}
