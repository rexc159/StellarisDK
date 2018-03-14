package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AmbientObjectUI;

public class AmbientObject extends GenericData {

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{};
    }

    public AmbientObject() {
        super();
        this.type = new DataEntry("ambient_object", 1011);
        ui = new AmbientObjectUI(this);
    }

    public AmbientObject(String input) {
        super(input);
        this.type = new DataEntry("ambient_object", 1011);
        ui = new AmbientObjectUI(this);
    }

    @Override
    public GenericData createNew() {
        return new AmbientObject();
    }

}
