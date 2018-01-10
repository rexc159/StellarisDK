package com.StellarisDK.tools.gui;

import com.StellarisDK.tools.fileClasses.ModDescriptor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ModDescUI extends AbstractUI {

    private TextField keys[] = new TextField[8];
    private String key_labels[];

    private Button btn_save = new Button("Save");
    private Button btn_cancel = new Button("Cancel");

    private Label list[] = new Label[8];

    public ModDescriptor md;

    public ModDescUI() {
        md = new ModDescriptor();
        key_labels = md.getKeys();
        init();
        String labels[] = {"Name", "Path", "Archive", "Dependencies", "Tags", "Picture", "Remote File ID", "Game Version"};
        VBox fp = new VBox();
        title.setText("Mod Descriptor");
        for (int i = 0; i < 8; i++) {
            keys[i] = new TextField();
            keys[i].setId(key_labels[i]);
            list[i] = new Label(labels[i]);
            list[i].setLabelFor(keys[i]);
            fp.getChildren().add(list[i]);
            fp.getChildren().add(keys[i]);
        }
        btn_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saved!");
                save();
            }
        });
        window.setBottom(btn_save);
        window.setCenter(fp);
    }

    public void load(String path) {
        try {
            md.load(path);
            for (TextField key: keys) {
                if(md.getValue(key.getId()) != null)
                    key.setText(md.getValue(key.getId()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ModDescriptor save() {
        for (TextField key: keys) {
            md.setValue(key.getId(), key.getText());
        }
        return md;
    }
}