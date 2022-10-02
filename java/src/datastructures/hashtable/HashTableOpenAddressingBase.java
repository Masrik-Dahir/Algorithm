
package datastructures.hashtable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class HashTableOpenAddressingBase<K, V> implements Iterable<K> {

  protected double loadFactor;
  protected int capacity, threshold, modificationCount;




  protected int usedBuckets, keyCount;


  protected K[] keys;
  protected V[] values;


  protected final K TOMBSTONE = (K) (new Object());

  private static final int DEFAULT_CAPACITY = 7;
  private static final double DEFAULT_LOAD_FACTOR = 0.65;

  protected HashTableOpenAddressingBase() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  protected HashTableOpenAddressingBase(int capacity) {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }


  protected HashTableOpenAddressingBase(int capacity, double loadFactor) {
    if (capacity <= 0) throw new IllegalArgumentException("Illegal capacity: " + capacity);

    if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
      throw new IllegalArgumentException("Illegal loadFactor: " + loadFactor);

    this.loadFactor = loadFactor;
    this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
    adjustCapacity();
    threshold = (int) (this.capacity * loadFactor);

    keys = (K[]) new Object[this.capacity];
    values = (V[]) new Object[this.capacity];
  }



  protected abstract void setupProbing(K key);

  protected abstract int probe(int x);




  protected abstract void adjustCapacity();


  protected void increaseCapacity() {
    capacity = (2 * capacity) + 1;
  }

  public void clear() {
    for (int i = 0; i < capacity; i++) {
      keys[i] = null;
      values[i] = null;
    }
    keyCount = usedBuckets = 0;
    modificationCount++;
  }


  public int size() {
    return keyCount;
  }


  public int getCapacity() {
    return capacity;
  }


  public boolean isEmpty() {
    return keyCount == 0;
  }

  public V put(K key, V value) {
    return insert(key, value);
  }

  public V add(K key, V value) {
    return insert(key, value);
  }


  public boolean containsKey(K key) {
    return hasKey(key);
  }


  public List<K> keys() {
    List<K> hashtableKeys = new ArrayList<>(size());
    for (int i = 0; i < capacity; i++)
      if (keys[i] != null && keys[i] != TOMBSTONE) hashtableKeys.add(keys[i]);
    return hashtableKeys;
  }


  public List<V> values() {
    List<V> hashtableValues = new ArrayList<>(size());
    for (int i = 0; i < capacity; i++)
      if (keys[i] != null && keys[i] != TOMBSTONE) hashtableValues.add(values[i]);
    return hashtableValues;
  }


  protected void resizeTable() {
    increaseCapacity();
    adjustCapacity();

    threshold = (int) (capacity * loadFactor);

    K[] oldKeyTable = (K[]) new Object[capacity];
    V[] oldValueTable = (V[]) new Object[capacity];


    K[] keyTableTmp = keys;
    keys = oldKeyTable;
    oldKeyTable = keyTableTmp;


    V[] valueTableTmp = values;
    values = oldValueTable;
    oldValueTable = valueTableTmp;



    keyCount = usedBuckets = 0;

    for (int i = 0; i < oldKeyTable.length; i++) {
      if (oldKeyTable[i] != null && oldKeyTable[i] != TOMBSTONE)
        insert(oldKeyTable[i], oldValueTable[i]);
      oldValueTable[i] = null;
      oldKeyTable[i] = null;
    }
  }



  protected final int normalizeIndex(int keyHash) {
    return (keyHash & 0x7FFFFFFF) % capacity;
  }


  protected static final int gcd(int a, int b) {
    if (b == 0) return a;
    return gcd(b, a % b);
  }



  public V insert(K key, V val) {
    if (key == null) throw new IllegalArgumentException("Null key");
    if (usedBuckets >= threshold) resizeTable();

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {


      if (keys[i] == TOMBSTONE) {
        if (j == -1) j = i;


      } else if (keys[i] != null) {


        if (keys[i].equals(key)) {

          V oldValue = values[i];
          if (j == -1) {
            values[i] = val;
          } else {
            keys[i] = TOMBSTONE;
            values[i] = null;
            keys[j] = key;
            values[j] = val;
          }
          modificationCount++;
          return oldValue;
        }


      } else {

        if (j == -1) {
          usedBuckets++;
          keyCount++;
          keys[i] = key;
          values[i] = val;




        } else {
          keyCount++;
          keys[j] = key;
          values[j] = val;
        }

        modificationCount++;
        return null;
      }
    }
  }


  public boolean hasKey(K key) {
    if (key == null) throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());



    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {



      if (keys[i] == TOMBSTONE) {

        if (j == -1) j = i;


      } else if (keys[i] != null) {


        if (keys[i].equals(key)) {





          if (j != -1) {

            keys[j] = keys[i];
            values[j] = values[i];
            keys[i] = TOMBSTONE;
            values[i] = null;
          }
          return true;
        }


      } else return false;
    }
  }




  public V get(K key) {
    if (key == null) throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());



    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {



      if (keys[i] == TOMBSTONE) {

        if (j == -1) j = i;


      } else if (keys[i] != null) {


        if (keys[i].equals(key)) {





          if (j != -1) {

            keys[j] = keys[i];
            values[j] = values[i];
            keys[i] = TOMBSTONE;
            values[i] = null;
            return values[j];
          } else {
            return values[i];
          }
        }


      } else return null;
    }
  }




  public V remove(K key) {
    if (key == null) throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());



    for (int i = offset, x = 1; ; i = normalizeIndex(offset + probe(x++))) {


      if (keys[i] == TOMBSTONE) continue;


      if (keys[i] == null) return null;


      if (keys[i].equals(key)) {
        keyCount--;
        modificationCount++;
        V oldValue = values[i];
        keys[i] = TOMBSTONE;
        values[i] = null;
        return oldValue;
      }
    }
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("{");
    for (int i = 0; i < capacity; i++)
      if (keys[i] != null && keys[i] != TOMBSTONE) sb.append(keys[i] + " => " + values[i] + ", ");
    sb.append("}");

    return sb.toString();
  }

  @Override
  public Iterator<K> iterator() {



    final int MODIFICATION_COUNT = modificationCount;

    return new Iterator<K>() {
      int index, keysLeft = keyCount;

      @Override
      public boolean hasNext() {

        if (MODIFICATION_COUNT != modificationCount) throw new ConcurrentModificationException();
        return keysLeft != 0;
      }


      @Override
      public K next() {
        while (keys[index] == null || keys[index] == TOMBSTONE) index++;
        keysLeft--;
        return keys[index++];
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
