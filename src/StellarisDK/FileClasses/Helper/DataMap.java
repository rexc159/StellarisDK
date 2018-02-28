package StellarisDK.FileClasses.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class DataMap extends HashMap<String, ArrayList<Object>> {
    public Object[] compressToPairArray(int size) {
        Object[] objs = new Object[size];
        for (Object key : keySet()) {
            for (Object data : get(key)) {
                try {
                    objs[((ValueTriplet) data).getOrder()] = new ValueTriplet<>(key, ((ValueTriplet) data).toPair() ,((ValueTriplet) data).getOrder());
                } catch (StackOverflowError e) {
                    System.out.println("[ERROR] StackOverFlow, FROM: DataMap");
                    System.out.println("[ERROR] CAUSE: Tried " + ((ValueTriplet) data).getOrder());
                    System.out.println("[ERROR] SIZE: " + size);
                }
            }
        }
        return objs;
    }
}