
package datastructures.hashtable;

public class HashTableLinearProbing<K, V> extends HashTableOpenAddressingBase<K, V> {




  private static final int LINEAR_CONSTANT = 17;

  public HashTableLinearProbing() {
    super();
  }

  public HashTableLinearProbing(int capacity) {
    super(capacity);
  }

  public HashTableLinearProbing(int capacity, double loadFactor) {
    super(capacity, loadFactor);
  }

  @Override
  protected void setupProbing(K key) {}

  @Override
  protected int probe(int x) {
    return LINEAR_CONSTANT * x;
  }



  @Override
  protected void adjustCapacity() {
    while (gcd(LINEAR_CONSTANT, capacity) != 1) {
      capacity++;
    }
  }
}
