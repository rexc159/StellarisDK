package StellarisDK.FileClasses.Helper;

import javafx.util.Pair;

import java.util.ArrayList;

public class PairArrayList extends ArrayList {

    public ArrayList<String> toStringList() {
        ArrayList<String> list = new ArrayList<>();
        for (Object pair : this) {
            list.add(((Pair) pair).getValue().toString());
        }
        return list;
    }

    public String getFirstString() {
        return this.get(0).toString().replaceAll("\"", "");
    }

    @Override
    public String toString() {
        String out = "";
        for (Object pair : this) {
//            try {
                if (this.size() == 1) {
                    return "#key " + ((ValueTriplet) pair).getKey() + " " + ((ValueTriplet) pair).getValue();
                }
                out += "#key " + ((ValueTriplet) pair).getKey() + " " + ((ValueTriplet) pair).getValue();
//            } catch (ClassCastException e) {
//                System.out.println("[ERROR] toString Failed, CLASS:"+pair.getClass());
//                System.out.println("[ERROR] toString Failed, CAUSE:"+pair.toString());
//            }
        }
        return out;
    }
}