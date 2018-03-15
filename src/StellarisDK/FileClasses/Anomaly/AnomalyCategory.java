package StellarisDK.FileClasses.Anomaly;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AnomalyCategoryUI;

import java.util.ArrayList;
import java.util.Arrays;

public class AnomalyCategory extends GenericData {

    @Override
    public void setDataEntries() {
        DataEntry key = new DataEntry<>("key", "=", "...", 1111);
        DataEntry desc = new DataEntry<>("desc", "=", "...", 1111);
        DataEntry picture = new DataEntry<>("picture", "=", "...", 1111);
        DataEntry level = new DataEntry<>("level", "=", "...", 1111);
        DataEntry spawn_chance = new DataEntry<>("spawn_chance", 1001);
        DataEntry on_spawn = new DataEntry<>("on_spawn", 1001);
        DataEntry on_success = new DataEntry<>("on_success", 1001);
        DataEntry on_fail = new DataEntry<>("on_fail", 1001);
        DataEntry on_critical_fail = new DataEntry<>("on_critical_fail", 1001);

        this.dataEntries = new ArrayList<>(
                Arrays.asList(key, desc, picture, level, spawn_chance, on_spawn,
                                on_success, on_fail, on_critical_fail));
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
}
