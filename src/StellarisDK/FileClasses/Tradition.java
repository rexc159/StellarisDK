package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.TraditionUI;
import javafx.scene.control.TreeItem;

public class Tradition extends GenericData {

    private boolean ap;

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{};
    }

    public Tradition(boolean ap) {
        super();
        if (ap) {
            this.type = new DataEntry("tradition", 1011);
            this.ap = ap;
            ui = new TraditionUI(this, ap);
        } else {
            this.type = new DataEntry("ascension_perk", 1011);
            this.ap = !ap;
            ui = new TraditionUI(this, !ap);
        }
    }

    public Tradition(String input, String type, boolean ap) {
        super(input, type);
        this.ap = ap;
        ui = new TraditionUI(this, ap);
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }

    @Override
    public GenericData createNew() {
        return new Tradition(ap);
    }
}
