package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.TraditionUI;

public class Tradition extends GenericData {

    private boolean ap;

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
    public GenericData createNew() {
        return new Tradition(ap);
    }
}
