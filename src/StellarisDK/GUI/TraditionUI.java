package StellarisDK.GUI;

import StellarisDK.FileClasses.Tradition;

public class TraditionUI extends AbstractUI {

    public TraditionUI(Tradition obj, boolean type) {
        if(type){
            init("FXML/traditionFX.fxml");
            window.setText("Tradition Editor");
            this.obj = obj;
        }else{
            init("FXML/apFX.fxml");
            window.setText("Ascension Editor");
            this.obj = obj;
        }
    }
}
