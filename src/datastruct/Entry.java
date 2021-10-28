package datastruct;

public class Entry<K,V> {
    
    private K k;
    private V v;

    public Entry(K key, V value) {
        this.k = key;
        this.v = value;
    }

    public K getKey() { return k; }
    public V getValue() { return v; }

    // utilities 
    protected void setKey(K key) { k = key; }
    protected V setValue(V value) {
      V old = v;
      v = value;
      return old;
    }

    /** Returns string representation (for debugging only) */
    public String toString() { return "<" + k + ", " + v + ">"; }
}