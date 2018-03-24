package StellarisDK.FileClasses.Helper;

import javafx.scene.control.TreeItem;

import java.util.Comparator;

public class DataComparator implements Comparator<TreeItem> {

    @Override
    public int compare(TreeItem o1, TreeItem o2) {
        return o1.getValue().toString().compareTo(o2.getValue().toString());
    }
}
