package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.GUI.AnomalyUI;
import javafx.scene.control.TreeItem;

public class Anomaly extends GenericData {

    public Anomaly() {
        super();
        this.type = "anomaly";
        ui = new AnomalyUI(this);
    }

    public Anomaly(String input) {
        super(input);
        this.type = "anomaly";
        ui = new AnomalyUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Anomaly();
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }
}
