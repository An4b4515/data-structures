package dataStructures.nonLinear;

import java.util.Objects;


public class HashTable<K, V> {
  /* -------------------- Inner Class -------------------- */
  private class Entry {
    K key;
    V val;
    int hash;
    Entry next;

    Entry(K key, V val, int h, Entry next) {
      this.key  = key;
      this.val  = val;
      this.hash = h;
      this.next = next;
    }

    @Override
    public String toString() {
      return
        String.format (
          "%s:%s",
          this.key,
          this.val
        );
    }
  }
  /* ----------------- End of Inner Class ----------------- */


// Fields:
  private int count = 0;
  private Entry[] table;


// Constructors:
  public HashTable() {
    this(32);
  }

  @SuppressWarnings("unchecked")
  public HashTable(int size) {
    this.table = (HashTable<K,V>.Entry[]) new HashTable.Entry[size];
  }


// Info Methods:
  public int size() {
    return this.count;
  }

  public boolean isEmpty() {
    return 0 == this.count;
  }

  public boolean containsKey(K key) {
    return this.extract(key, false) != null;
  }

  public boolean containsValue(V val) {
    if ( null == val ) {
      for (Entry x : this.table) {
        if ( null == x )
          continue;

        for (Entry e = x; e != null; e = e.next)
          if ( null == e.val )
            return true;
      }
    }
    else {
      for (Entry x : this.table) {
        if ( null == x )
          continue;

        for (Entry e = x; e != null; e = e.next)
          if ( val.equals( e.val ) )
            return true;
      }
    }

    return false;
  }


// Other Methods:
  public K[] getKeys() {
    @SuppressWarnings("unchecked")
    var keys = (K[]) new Object[ this.size() ];

    int i = 0;
    for ( Entry e : this.table ) {
      if ( null == e )
        continue;

      keys[i++] = e.key;
    }

    return keys;
  }

  public V[] getValues() {
    @SuppressWarnings("unchecked")
    var values = (V[]) new Object[ this.size() ];

    int i = 0;
    for ( Entry e : this.table ) {
      if ( null == e )
        continue;

      values[i++] = e.val;
    }

    return values;
  }


// Control Methods:
  public V put(K key, V val) {
    return this.insert(key, val, false);
  }

  public V putIfAbsent(K key, V val) {
    return this.insert(key, val, true);
  }

  public V get(K key) {
    Entry e = this.extract(key, false);
    return (e != null) ? e.val : null;
  }

  public V remove(K key) {
    Entry e = this.extract(key, true);
    V old = (e != null) ? e.val : null;
    clear( e );
    return old;
  }


// Helper Methods:
  private V insert(K key, V val, boolean onlyIfAbsent) {
    int h = hash( key ),
        i = getHashIndex( h );

    for (Entry e = this.table[i]; e != null; e = e.next) {
      if ( e.key.equals(key) && e.hash == h ) {
        if (onlyIfAbsent)
          return e.val;

        V old_val = e.val;
        e.val = val;
        return old_val;
      }
    }

    this.table[i] = new Entry(key, val, h, this.table[i]);
    ++this.count;

    return null;
  }

  private Entry extract(K key, boolean remove) {
    int h = hash( key ),
        i = getHashIndex( h );

    for (Entry e = this.table[i], prev = null; e != null; e = e.next, prev = e) {
      if ( e.key.equals(key) && (e.hash == h) ) {
        if (remove) {
          if (null == prev)
            this.table[i] = e.next;
          else
            prev.next = e.next;

          --this.count;
          return e;
        }

        return e;
      }
    }

    return null;
  }

  private int hash(K key) {
    int h = Objects.requireNonNull(key).hashCode();
    return (h >>> 16) ^ h;
  }

  private int getHashIndex(int hash) {
    return hash % this.table.length;
  }

  private void clear(Entry e) {
    e.key  = null;
    e.val  = null;
    e.hash = -1;
    e.next = null;
    e = null;
  }


// Print Method:
  @Override
  public String toString() {
    if ( this.isEmpty() )
      return "{}";

    var sb = new StringBuilder().append('{');
    for (Entry x : this.table) { // For-each / enhanced-for loop ...
      if (null == x)
        continue;

      for (Entry e = x; ; ) {
        sb.append(e);

        if ( (e = e.next) == null )
          break;

        sb.append(',');
      }

      sb.append(',');
    }

    sb.deleteCharAt( sb.length()-1 ); // Remove the last comma ...
    return sb.append('}').toString();
  }
}


