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
        ValueTriplet[] objs = compressToPairArray();
        for (ValueTriplet obj : objs) {
            if (((VPair) obj.getValue()).getValue() instanceof DataMap) {
                root.getChildren().add(((DataMap) ((VPair) obj.getValue()).getValue()).toTreeItem(obj.getKey().toString()));
            } else if (((VPair) obj.getValue()).getValue() instanceof PairArrayList) {
                TreeItem group = ((PairArrayList) ((VPair) obj.getValue()).getValue()).toTreeItem(obj.getKey().toString());
                root.getChildren().add(group);
            }else {
                root.getChildren().add(new TreeItem<>(obj));
            }
        }
        return root;
    }

    public ValueTriplet[] compressToPairArray() {
        ValueTriplet[] objs = new ValueTriplet[getFullSize()];
        for (Object key : keySet()) {
            for (Object data : ((ArrayList) get(key))) {
                try {
                    if (((DataEntry)data).getEntry().getValue() != null){
                        objs[((DataEntry)data).getEntry().getOrder()] = new ValueTriplet<>(key, ((DataEntry)data).getEntry().toPair(), ((DataEntry)data).getEntry().getOrder());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("[ERROR] StackOverFlow, FROM: DataMap");
                    System.out.println("[ERROR] CAUSE: Tried " + key + " Value: " + ((DataEntry)data).getEntry().getKey());
                    System.out.println("[ERROR] SIZE: " + getFullSize() + ", Actual: " + ((DataEntry)data).getEntry().getOrder());
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