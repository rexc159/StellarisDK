package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.EntryArrayList;
import StellarisDK.GUI.AttitudeUI;

import java.util.Arrays;

public class Attitude extends GenericData {

    public Attitude() {
        super();
        this.type = new DataEntry("new_attitude");
        data.put("behaviour", new EntryArrayList<>(Arrays.asList(new DataMap())));
        ui = new AttitudeUI(this);
    }

    public Attitude(String input, String type) {
        super(input, type);
        ui = new AttitudeUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Attitude();
    }
}
