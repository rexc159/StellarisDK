package StellarisDK.FileClasses.Helper;

public class VPair<K,V> {
    private K key;
    private V value;

    public VPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public VPair setValue(V value) {
        this.value = value;
        return this;
    }

    public VPair setKey(K key) {
        this.key = key;
        return this;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public String toExport() {
        return " " + getKey() + " " + getValue();
    }

    @Override
    public String toString() {
        return getKey() + " " + getValue();
    }
}