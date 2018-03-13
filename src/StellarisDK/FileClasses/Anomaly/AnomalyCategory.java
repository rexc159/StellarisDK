package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AnomalyCategoryUI;
import javafx.scene.control.TreeItem;

public class AnomalyCategory extends GenericData {

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{};
    }

    public AnomalyCategory() {
        super();
        this.type = new DataEntry("anomaly_category", 1001);
        ui = new AnomalyCategoryUI(this);
    }

    public AnomalyCategory(String input) {
        super(input);
        this.type = new DataEntry("anomaly_category", 1001);
        ui = new AnomalyCategoryUI(this);
    }

    @Override
    public GenericData createNew() {
        return new AnomalyCategory();
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }
}
