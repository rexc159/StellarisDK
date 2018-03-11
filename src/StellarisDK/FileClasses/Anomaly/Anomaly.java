package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.FileClasses.Helper.VPair;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import StellarisDK.GUI.AnomalyUI;
import javafx.scene.control.TreeItem;

public class Anomaly extends GenericData {

    private DataEntry[] requiredSet = {
            new DataEntry(new ValueTriplet<>("event", new VPair<>("=", "..."), 0), true, true),
            new DataEntry(new ValueTriplet<>("category", new VPair<>("=", "..."), 0), true, true),
            new DataEntry("potential", false, false)
    };

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
        TreeItem root = new TreeItem<>(new DataEntry(type, false, false));
        for (DataEntry entry : requiredSet) {
            root.getChildren().add(entry.toNestedTree());
        }
        return root;
    }
}
