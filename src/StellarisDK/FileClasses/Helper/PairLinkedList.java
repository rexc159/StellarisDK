package StellarisDK.FileClasses.Helper;

import javafx.util.Pair;

import java.util.LinkedList;

public class PairLinkedList extends LinkedList {

    @Override
    public String toString() {
        String out = "";
        for (Object pair : this) {
            if (this.size() == 1) {
                return "#key " + ((Pair) pair).getKey() + " " + ((Pair) pair).getValue();
            }
            out += "#key " + ((Pair) pair).getKey() + " " + ((Pair) pair).getValue();
        }
        return out;
    }
}