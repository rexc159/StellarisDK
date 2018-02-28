package StellarisDK.FileClasses.Helper;

public class ValueTriplet<K,V> {
    private K key;
    private V value;
    private int order;

    public ValueTriplet(K key, V value, int order) {
        this.key = key;
        this.value = value;
        this.order = order;
    }

    public VPair toPair() {
        return new VPair(key, value);
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        if (getValue() instanceof VPair)
            return "#tabs" + getKey() + ((VPair) getValue()).toExport();
        else
            return getValue().toString();
    }
}