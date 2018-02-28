package StellarisDK.FileClasses.Helper;

import java.util.ArrayList;

public class PairArrayList extends ArrayList {

    public String getFirstString() {
        return this.get(0).toString().replaceAll("\"", "");
    }

    @Override
    public String toString() {
        String out = "";
        for (Object pair : this) {
                if (this.size() == 1) {
                    return "#tabs" + ((ValueTriplet) pair).getKey() + " " + ((ValueTriplet) pair).getValue();
                }
                out += "#tabs" + ((ValueTriplet) pair).getKey() + " " + ((ValueTriplet) pair).getValue();
        }
        return out;
    }
}