package StellarisDK.FileClasses.Helper;

import StellarisDK.FileClasses.GenericData;
import javafx.scene.control.TreeItem;

import java.util.HashMap;

public class DataMap<V> extends HashMap<String, V> {

    public int getFullSize() {
        int count = 0;
        for (Object key : keySet()) {
            count += ((EntryArrayList) get(key)).size();
        }
        return count;
    }

    public Object getFirstValue(String key) {
        if (get(key) != null)
            return ((EntryArrayList) get(key)).get(0);
        else
            return null;
    }

    public TreeItem<DataEntry> toTreeItem(DataEntry key) {
        TreeItem<DataEntry> root = new TreeItem<>(new DataEntry(key.getKey(), key.getBinary()));
        DataEntry[] objs = compressToPairArray();
        for (DataEntry obj : objs) {
            if(obj.getValue() instanceof DataMap){
                TreeItem item = ((DataMap) obj.getValue()).toTreeItem(obj);
                root.getChildren().add(item);
            } else if (obj.getValue() instanceof EntryArrayList) {
                TreeItem group = ((EntryArrayList) obj.getValue()).toTreeItem(obj);
                root.getChildren().add(group);
            } else {
                root.getChildren().add(new TreeItem<>(obj));
            }
        }
        return root;
    }

    private DataEntry[] compressToPairArray() {
        DataEntry[] objs = new DataEntry[getFullSize()];
        for (String key : keySet()) {
            for (DataEntry data : ((EntryArrayList<DataEntry>) get(key))) {
                if (data.getValue() != null) {
                    objs[data.getOrder()] = data;
                }
            }
        }
        return objs;
    }

    @Override
    public String toString() {
        GenericData.tab++;
        String tabs = "\r\n";
        for (int k = 0; k < GenericData.tab; k++) {
            tabs += "\t";
        }
        String out = "{";
        Object[] temp = compressToPairArray();
        for (int i = 0; i < getFullSize(); i++)
            if (temp[i] != null)
                out += tabs+temp[i].toString();
        GenericData.tab--;
        return out + tabs.replaceFirst("\t", "") + "}";
    }
}