package StellarisDK.FileClasses.Helper;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class PairArrayList<T> extends ArrayList<T> {

    public TreeItem toTreeItem(String key) {
        TreeItem root = new TreeItem<>(key);
        for (Object obj : this) {
            if(obj instanceof VPair){
                if(((VPair) obj).getValue() instanceof VPair){
                    if(((VPair) ((VPair) obj).getValue()).getValue() instanceof PairArrayList){
                        root.getChildren().add(((PairArrayList) ((VPair) ((VPair) obj).getValue()).getValue()).toTreeItem(((VPair) obj).getKey().toString()));
                    }else{
                        root.getChildren().add(new TreeItem<>(obj));
                    }
                }else{
                    root.getChildren().add(new TreeItem<>(obj));
                }
            }else if (obj instanceof ValueTriplet){
                if(((ValueTriplet) obj).getValue() instanceof PairArrayList){
                    root.getChildren().add(((PairArrayList) ((ValueTriplet) obj).getValue()).toTreeItem(((ValueTriplet) obj).getKey().toString()));
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