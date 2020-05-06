package hw7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

// handles chaining

public class HashMap<K, V> implements Map<K, V> {

  private LinkedList<Entry<K, V>>[] table;
  private int numElements;
  private int tableSize;

  /**
   * Constructor for HashMapChainingBuiltInArray class.
   */
  public HashMap() {
    tableSize = 10;
    numElements = 0;
    table = new LinkedList[tableSize];
  }

  private void rehash() {
    tableSize *= 2;
    LinkedList<Entry<K, V>>[] temp = table.clone();
    table = new LinkedList[tableSize];
    for (int i = 0; i < temp.length; i++) {
      LinkedList<Entry<K, V>> list = table[i];
      if (list == null) {
        continue;
      }
      for (Entry<K,V> e : list) {
        insert(e.key, e.value);
        numElements--;
      }
    }


  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    Entry<K,V> e = find(k);
    // this key exists
    if (e != null) {
      throw new IllegalArgumentException();
    }
    int index = hash(k);
    // if this index is not existent yet
    if (table[index] == null) {
      LinkedList<Entry<K,V>> list = new LinkedList<>();
      list.add(new Entry<>(k, v));
      table[index] = list;
    } else {
      // there is some other element here with this index
      LinkedList<Entry<K,V>> list = table[index];
      if (list.size() == 10) {
        rehash();
      }
      list.add(new Entry<>(k, v));
    }
    numElements++;
    return;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Entry<K,V> e = find(k);
    if (e == null) {
      throw new IllegalArgumentException();
    }
    int index = hash(k);
    table[index].remove(e); //todo this removes the first presence only?
    numElements--;
    return e.value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }
    Entry<K,V> e = find(k);
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

  private Entry<K,V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException();
    }
    int index = hash(k);
    if (table[index] == null) {
      return null;
    }
    LinkedList<Entry<K, V>> list = table[index];
    ListIterator<Entry<K, V>> it = list.listIterator();
    Entry<K, V> cur;
    while (it.hasNext()) {
      cur = it.next();
      if (cur.key == k) {
        return cur;
      }
    }
    return null;
  }

  private int hash(K k) {
    // use built in
    int index = k.hashCode() % tableSize;
    if (index < 0) {
      index += tableSize;
    }
    return index;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    List<K> keys = new ArrayList<K>();
    for (int i = 0; i < table.length; i++) {
      if (table[i] == null) {
        continue;
      }
      for (Entry<K,V> e: table[i]) {
        if (e.key != null) {
          keys.add(e.key);
        }
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

