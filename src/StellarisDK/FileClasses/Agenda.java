package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.AgendaUI;

import java.util.ArrayList;
import java.util.Arrays;

public class Agenda extends GenericData {

    @Override
    public void setDataEntries() {
        dataEntries = new ArrayList<>(Arrays.asList(
                new DataEntry<>("weight_modifier", 1001),
                new DataEntry<>("modifier", 1001)
        ));
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
    public GenericData createNew() {
        return new Agenda();
    }
}