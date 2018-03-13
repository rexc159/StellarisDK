package StellarisDK.FileClasses.Helper;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class EntryArrayList<T> extends ArrayList<T> {

    public TreeItem toTreeItem(String key) {
        TreeItem root = new TreeItem<>(key);
        for (Object obj : this) {
            if(obj instanceof DataEntry){
                if(((DataEntry) obj).getValue() instanceof EntryArrayList){
                    root.getChildren().add(((EntryArrayList) ((DataEntry) obj).getValue()).toTreeItem(((DataEntry) obj).getKey()));
                }else{
                    root.getChildren().add(new TreeItem<>(obj));
                }
            }else {
                root.getChildren().add(new TreeItem<>(obj));
            }
        }
        return root;
    }

    public String getFirstString() {
        if(get(0) instanceof DataEntry){
            return ((DataEntry) get(0)).getValue().toString().replaceAll("\"", "");
        }else{
            return get(0).toString().replaceAll("\"", "");
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (Object pair : this) {
            out += "#tabs" + pair;
        }
        return out;
    }
}