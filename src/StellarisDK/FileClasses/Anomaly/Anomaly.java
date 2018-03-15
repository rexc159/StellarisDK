package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AnomalyUI;

import java.util.ArrayList;
import java.util.Arrays;

public class Anomaly extends GenericData {

    @Override
    public void setDataEntries() {
        DataEntry event = new DataEntry<>("event", "=", "...", 1111);
        DataEntry category = new DataEntry<>("category", "=", "...", 1111);
        DataEntry weight = new DataEntry<>("weight", "=", "...", 1110);
        DataEntry potential = new DataEntry<>("potential", 1001);

        this.dataEntries = new ArrayList<>(Arrays.asList(event, category, weight, potential));
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