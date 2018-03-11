package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AgendaUI;
import javafx.scene.control.TreeItem;

public class Agenda extends GenericData {

    private DataEntry[] requiredSet = {
                    new DataEntry("weight_modifier", false, false),
                    new DataEntry("modifier", false, false)
            };

    public Agenda() {
        super();
        this.type = "new_agenda";
        ui = new AgendaUI(this);
    }

    public Agenda(String input, String type) {
        super(input, type);
        ui = new AgendaUI(this);
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        TreeItem root = new TreeItem<>(new DataEntry(type, true, false));
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