package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.GUI.AnomalyCategoryUI;

public class AnomalyCategory extends GenericData {
    public AnomalyCategory() {
        super();
        this.type = "anomaly_category";
        ui = new AnomalyCategoryUI(this);
    }

    public AnomalyCategory(String input) {
        super(input);
        this.type = "anomaly_category";
        ui = new AnomalyCategoryUI(this);
    }

    @Override
    public GenericData createNew() {
        return new AnomalyCategory();
    }
}
