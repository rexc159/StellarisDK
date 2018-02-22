package StellarisDK.FileClasses.Helper;

import javafx.util.Pair;

import java.util.LinkedList;

public class PairLinkedList extends LinkedList {

    public LinkedList<String> toStringList(){
        LinkedList<String> list = new LinkedList<>();
        for(Object pair : this){
            list.add(((Pair) pair).getValue().toString());
        }
        return list;
    }

    public String getFirstString(){
        return getFirst().toString().replaceAll("\"","");
    }

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