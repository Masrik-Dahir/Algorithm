package datastructures.utils;

import java.util.*;

public final class TestUtils {



  public static List<Integer> randomIntegerList(int sz, int min, int max) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(randInt(min, max));
    return lst;
  }



  public static List<Integer> randomUniformUniqueIntegerList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }

  public static List<Integer> randomUniformUniqueIntegerList(int min, int max) {
    List<Integer> lst = new ArrayList<>(max - min);
    for (int i = min; i < max; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }


  public static int randInt(int min, int max) {
    return min + (int) (Math.random() * ((max - min)));
  }


  public static long randLong(long min, long max) {
    return min + (long) (Math.random() * ((max - min)));
  }


  public static List<Integer> sortedIntegerList(int min, int max) {
    List<Integer> lst = new ArrayList<>(max - min);
    for (int i = min; i < max; i++) lst.add(i);
    return lst;
  }
}
