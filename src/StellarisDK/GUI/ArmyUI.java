package StellarisDK.GUI;

import StellarisDK.FileClasses.Army;

public class ArmyUI extends AbstractUI{
    public ArmyUI(Army obj) {
        init("FXML/default.fxml");
        window.setText("Army Editor");
        this.obj = obj;
    }
}
