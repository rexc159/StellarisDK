package StellarisDK.FileClasses.Helper;

import javafx.util.Pair;

public class VPair extends Pair {
    public VPair(Object key, Object value){
        super(key, value);
    }

    public VPair setValue(Object value){
        return new VPair(getKey(), value);
    }

    public VPair setKey(Object key){
        return new VPair(key, getValue());
    }

    public String toExport(){
        return " " + getKey() + " " + getValue();
    }

    @Override
    public String toString(){
        return getKey() + " " + getValue();
    }
}
