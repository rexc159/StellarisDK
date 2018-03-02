package StellarisDK.FileClasses.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class DataMap<K, V> extends HashMap<K, V> {
    public int getFullSize() {
        int count = 0;
        for (Object key : keySet()) {
            count += ((ArrayList) get(key)).size();
        }
        return count;
    }

    public Object[] compressToPairArray() {
        Object[] objs = new Object[getFullSize()];
        for (Object key : keySet()) {
            for (Object data : ((ArrayList) get(key))) {
                try {
                    if (((ValueTriplet) data).getValue() != null)
                        objs[((ValueTriplet) data).getOrder()] = new ValueTriplet<>(key, ((ValueTriplet) data).toPair(), ((ValueTriplet) data).getOrder());
                } catch (StackOverflowError e) {
                    System.out.println("[ERROR] StackOverFlow, FROM: DataMap");
                    System.out.println("[ERROR] CAUSE: Tried " + ((ValueTriplet) data).getOrder());
                    System.out.println("[ERROR] SIZE: " + getFullSize());
                }
            }
        }
        return objs;
    }
}