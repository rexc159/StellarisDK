package StellarisDK.FileClasses;

import StellarisDK.GUI.TraditionUI;
import javafx.scene.control.TreeItem;

public class Tradition extends GenericData {

    private boolean ap;

    public Tradition(boolean ap) {
        super();
        if(ap){
            this.type = "tradition";
            this.ap = ap;
            ui = new TraditionUI(this, ap);
        }else{
            this.type = "ascension_perk";
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
