package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AnomalyUI;

public class Anomaly extends GenericData {

    @Override
    public void setRequiredSet() {
        DataEntry event = new DataEntry<>("event", "=", "...", 1111);
        DataEntry category = new DataEntry<>("category", "=", "...", 1111);
        DataEntry potential = new DataEntry<>("potential", 1001);

        this.requiredSet = new DataEntry[]{event, category, potential};
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
}