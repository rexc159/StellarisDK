package com.StellarisDK.tools.gui;

import com.StellarisDK.tools.fileClasses.ModDescriptor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ModDescUI extends AbstractUI {

    private TextField keys[] = new TextField[6];

    private Label list[] = new Label[6];

    public ModDescriptor md = new ModDescriptor();

    public ModDescUI() {
        init();
        String labels[] = {"Name: ", "Path: ", "Replace Path: ", "Remote File ID: ", "Picture: ", "GAme Version: "};
        VBox fp = new VBox();
        title.setText("Mod Descriptor");
        for (int i = 0; i < 6; i++) {
            keys[i] = new TextField();
            list[i] = new Label(labels[i]);
            list[i].setLabelFor(keys[i]);
            fp.getChildren().add(list[i]);
            fp.getChildren().add(keys[i]);
        }
        window.setCenter(fp);
    }

    public void load(String path) {
        try {
            md.load(path);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

//        this.keys[0].setText(md.getName());
//        this.keys[1].setText(md.getPath());
//        this.keys[2].setText(md.getReplacePath());
//        this.keys[3].setText(md.getPicture());
//        this.keys[4].setText(md.getRemoteFileID());
//        this.keys[5].setText(md.getVersion());
    }

    public ModDescriptor save() {
//        md.setName(this.keys[0].getText());
//        md.setPath(this.keys[1].getText());
//        md.setReplacePath(this.keys[2].getText());
//        md.setPicture(this.keys[3].getText());
//        md.setRemoteFileID(this.keys[4].getText());
//        md.setVersion(this.keys[5].getText());
        return md;
    }
}