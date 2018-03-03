package StellarisDK.FileClasses.Helper;

import StellarisDK.FileClasses.GenericData;
import javafx.scene.control.TreeItem;

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

    public TreeItem toTreeItem(String key) {
        TreeItem root = new TreeItem<>(key);
        Object[] objs = compressToPairArray();
        for (Object obj : objs) {
            if (((VPair) ((ValueTriplet) obj).getValue()).getValue() instanceof DataMap) {
                root.getChildren().add(((DataMap) ((VPair) ((ValueTriplet) obj).getValue()).getValue()).toTreeItem(((ValueTriplet) obj).getKey().toString()));
            } else {
                root.getChildren().add(new TreeItem<>(obj));
            }
        }
        return root;
    }

    public Object[] compressToPairArray() {
        Object[] objs = new Object[getFullSize()];
        for (Object key : keySet()) {
            for (Object data : ((ArrayList) get(key))) {
                try {
                    if (((ValueTriplet) data).getValue() != null)
                        objs[((ValueTriplet) data).getOrder()] = new ValueTriplet<>(key, ((ValueTriplet) data).toPair(), ((ValueTriplet) data).getOrder());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("[ERROR] StackOverFlow, FROM: DataMap");
                    System.out.println("[ERROR] CAUSE: Tried " + key + " Value: " + ((ValueTriplet) data).getKey());
                    System.out.println("[ERROR] SIZE: " + getFullSize() + ", Actual: " + ((ValueTriplet) data).getOrder());
                }
            }
        }
        return objs;
    }

    @Override
    public String toString() {
        GenericData.changeTab(true);
        String tabs = "\r\n";
        for (int k = 0; k < GenericData.getTab(); k++) {
            tabs += "\t";
        }
        String out = "{";
        Object[] temp = compressToPairArray();
        for (int i = 0; i < getFullSize(); i++)
            if (temp[i] != null)
                out += temp[i].toString().replaceAll("#tabs", tabs);
        GenericData.changeTab(false);
        return out + tabs.replaceFirst("\t", "") + "}";
    }
}