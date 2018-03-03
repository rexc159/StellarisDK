package StellarisDK.GUI;

import StellarisDK.FileClasses.Anomaly.Anomaly;

public class AnomalyUI extends AbstractUI {

    public AnomalyUI(Anomaly obj) {
        init("FXML/anomalyFX.fxml");
        window.setText("Anomaly Editor");
        load(obj);
    }

    @Override
    public Object save() {
        return null;
    }

    @Override
    public void load(Object object) {

    }
}
