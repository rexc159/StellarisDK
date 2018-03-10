package StellarisDK.FileClasses;

import StellarisDK.GUI.AmbientObjectUI;
import javafx.scene.control.TreeItem;

public class AmbientObject extends GenericData {

    public AmbientObject() {
        super();
        this.type = "ambient_object";
        ui = new AmbientObjectUI(this);
    }

    public AmbientObject(String input) {
        super(input);
        this.type = "ambient_object";
        ui = new AmbientObjectUI(this);
    }

    @Override
    public GenericData createNew() {
        return new AmbientObject();
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }
}
