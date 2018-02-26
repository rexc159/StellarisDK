package StellarisDK.FileClasses.Helper;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;

public class PairArrayList extends ArrayList {

    public LinkedList<String> toStringList() {
        LinkedList<String> list = new LinkedList<>();
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
            try {
                if (this.size() == 1) {
                    return "#key " + ((Pair) pair).getKey() + " " + ((Pair) pair).getValue();
                }
                out += "#key " + ((Pair) pair).getKey() + " " + ((Pair) pair).getValue();
            } catch (ClassCastException e) {
                System.out.println("[ERROR] toString Failed, CLASS:"+pair.getClass());
                System.out.println("[ERROR] toString Failed, CAUSE:"+pair.toString());
            }
        }
        return out;
    }
}