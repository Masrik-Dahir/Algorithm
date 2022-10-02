package utils;

import java.util.*;

public final class TestUtils {



  public static int[] randomIntegerArray(int sz, int min, int max) {
    int[] ar = new int[sz];
    for (int i = 0; i < sz; i++) ar[i] = randValue(min, max);
    return ar;
  }



  public static long[] randomLongArray(int sz, long min, long max) {
    long[] ar = new long[sz];
    for (int i = 0; i < sz; i++) ar[i] = randValue(min, max);
    return ar;
  }



  public static List<Integer> randomIntegerList(int sz, int min, int max) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(randValue(min, max));
    return lst;
  }



  public static List<Integer> randomUniformUniqueIntegerList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }


  public static int randValue(int min, int max) {
    return min + (int) (Math.random() * ((max - min)));
  }


  public static long randValue(long min, long max) {
    return min + (long) (Math.random() * ((max - min)));
  }
}
