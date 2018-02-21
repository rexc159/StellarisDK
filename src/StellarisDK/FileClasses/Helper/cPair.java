package StellarisDK.FileClasses.Helper;

import javafx.util.Pair;

public class cPair extends Pair {

    public cPair(Object key, Object value){
        super(key, value);
    }

    public cPair setValue(Object value){
        return new cPair(getKey(), value);
    }

    public cPair setKey(Object key){
        return new cPair(key, getValue());
    }

    @Override
    public String toString(){
        return getValue().toString();
    }
}
