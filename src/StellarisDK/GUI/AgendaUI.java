package StellarisDK.GUI;

import StellarisDK.FileClasses.Agenda;

public class AgendaUI extends AbstractUI {

    public AgendaUI(Agenda obj) {
        init("FXML/agendaFX.fxml");
        window.setText("Agenda Editor");
        this.obj = obj;
    }

}