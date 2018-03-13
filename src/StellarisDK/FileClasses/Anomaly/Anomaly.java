package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AnomalyUI;
import javafx.scene.control.TreeItem;

public class Anomaly extends GenericData {

    @Override
    public void setRequiredSet() {
        this.requiredSet = new DataEntry[]{
                new DataEntry<>("event", "=", "...", 1111),
                new DataEntry<>("category", "=", "...", 1111),
                new DataEntry<>("potential", 1001)
        };
    }

    public Anomaly() {
        super();
        this.type = new DataEntry("anomaly", 1001);
        ui = new AnomalyUI(this);
    }

    public Anomaly(String input) {
        super(input);
        this.type = new DataEntry("anomaly", 1001);
        ui = new AnomalyUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Anomaly();
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        TreeItem root = new TreeItem<>(type);
        for (DataEntry entry : requiredSet) {
            TreeItem temp = new TreeItem<>(entry);
            if(!entry.isSingleEntry()){
                temp.getChildren().add(new TreeItem<>("click_to_edit"));
            }
            root.getChildren().add(temp);
        }
        return root;
    }
}
