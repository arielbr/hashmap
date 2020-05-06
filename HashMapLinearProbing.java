package hw7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HashMapLinearProbing<K, V> implements Map<K, V> {
  private Entry<K, V>[] table;
  private int tableSize;
  private int numElements;

  /**
   * Constructor for HashMapLinearProbing class.
   */
  public HashMapLinearProbing() {
    tableSize = 10;
    numElements = 0;
    table = new Entry[tableSize];
  }

  private Entry<K,V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException();
    }
    int index = hash(k);
    Entry<K,V> e = table[index];
    // that place has always been empty
    if (e == null) {
      return e;
    }
    return findHelper(k, index);
  }

  private Entry<K,V> findHelper(K k, int index) {
    int count = 0;
    // if sth has been there but is removed,
    // look for next location
    while (table[index] != null && count < tableSize) {
      if (table[index].key == k) {
        return table[index];
      }
      // look for the next
      if (index == tableSize - 1) {
        index = 0;
      } else {
        index++;
      }
      count++;
    }
    return null;
  }

  private int hash(K k) {
    int code = k.hashCode() % tableSize;
    if (code < 0) {
      code += tableSize;
    }
    return code;
  }

  private void rehash() {
    tableSize *= 2;
    // put a pointer to old table
    Entry<K,V>[] old = table.clone();
    // replace with a new table
    table  = new Entry[tableSize];
    // re-hash the keys and put them into new table
    for (int i = 0; i < old.length; i++) {
      insert(old[i].key, old[i].value);
      numElements--;
    }
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    // grow if table is full
    if (tableSize == numElements) {
      rehash();
    }
    Entry<K,V> e = find(k);
    int index = hash(k);
    if (e == null) {
      linearProbing(k, v, index);
      numElements++;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This function performs linear probing to find an empty place.
   * @param k key of entry to be inserted
   * @param v value of entry to be inserted
   * @param index index of this entry after hashing
   */
  public void linearProbing(K k, V v, int index) {
    // linear probing
    while (table[index] != null) { // todo check
      if (table[index].key != null) {
        index++;
        if (index == tableSize) {
          index = 0;
        }
      } else {
        break;
      }
    }
    table[index] = new Entry<>(k, v);
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Entry<K,V> e = find(k);
    if (e == null) {
      throw new IllegalArgumentException();
    }
    V val = e.value;
    e.key = null; //todo check if this change the copy or within the table
    numElements--;
    return val;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Entry<K, V> e = find(k);
    if (e == null) {
      throw new IllegalArgumentException();
    }
    e.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Entry<K,V> e = find(k);
    if (e == null) {
      throw new IllegalArgumentException();
    }
    return e.value;
  }

  @Override
  public boolean has(K k) {
    return find(k) != null;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    List<K> keys = new ArrayList<K>();
    for (Entry<K,V> e: table) {
      if (e == null) {
        continue;
      }
      if (e.key != null) {
        keys.add(e.key);
      }
    }
    return keys.iterator();
  }

  private static class Entry<K, V> {
    K key;
    V value;

    Entry(K k, V v) {
      this.key = k;
      this.value = v;
    }
  }
}
