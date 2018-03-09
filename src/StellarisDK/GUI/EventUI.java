package StellarisDK.GUI;

import StellarisDK.FileClasses.Event;

public class EventUI extends AbstractUI {

    public EventUI() {
        init("FXML/eventFX.fxml");
        window.setText("Event Editor");
    }

    public EventUI(Event obj) {
        init("FXML/eventFX.fxml");
        window.setText("Event Editor");
        this.obj = obj;
    }
}