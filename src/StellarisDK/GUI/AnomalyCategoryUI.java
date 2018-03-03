package StellarisDK.GUI;

import StellarisDK.FileClasses.Anomaly.AnomalyCategory;

public class AnomalyCategoryUI extends AbstractUI {

    public AnomalyCategoryUI(AnomalyCategory obj) {
        init("FXML/anomalyCategoryFX.fxml");
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
