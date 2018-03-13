package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AgendaUI;
import javafx.scene.control.TreeItem;

public class Agenda extends GenericData {

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{
                new DataEntry("weight_modifier", 1001),
                new DataEntry("modifier", 1001)
        };
    }

    public Agenda() {
        super();
        this.type = new DataEntry("new_agenda", 1011);
        ui = new AgendaUI(this);
    }

    public Agenda(String input, String type) {
        super(input, type);
        ui = new AgendaUI(this);
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        TreeItem root = new TreeItem<>(type);
        for (DataEntry entry : requiredSet) {
            TreeItem temp = new TreeItem<>(entry);
            if(!entry.isSingleEntry()){
                temp.getChildren().add(new TreeItem<>("click_to_edit"));
            }
            root.getChildren().add(temp);
        }
        return root;
    }

    @Override
    public GenericData createNew() {
        return new Agenda();
    }
}