package com.StellarisDK.tools.gui;

import com.StellarisDK.tools.fileClasses.ModDescriptor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ModDescUI extends AbstractUI {

    private TextField name = new TextField();
    private TextField path = new TextField();
    private TextField replacePath = new TextField();
    private TextField picture = new TextField();
    private TextField remoteFileID = new TextField();
    private TextField version = new TextField();

    private Label list[] = new Label[6];

    private ModDescriptor md = new ModDescriptor();

    public ModDescUI(){
        init();
        title.setText("Mod Descriptor");
        list[0] = new Label("Name: ");
        list[1] = new Label("Path: ");
        list[2] = new Label("Replace Path: ");
        list[3] = new Label("Picture: ");
        list[4] = new Label("Remote File ID: ");
        list[5] = new Label("Game Version: ");
        list[0].setLabelFor(name);
        list[1].setLabelFor(path);
        list[2].setLabelFor(replacePath);
        list[3].setLabelFor(picture);
        list[4].setLabelFor(remoteFileID);
        list[5].setLabelFor(version);
        VBox fp = new VBox();
        fp.getChildren().add(list[0]);
        fp.getChildren().add(name);

        fp.getChildren().add(list[1]);
        fp.getChildren().add(path);

        fp.getChildren().add(list[2]);
        fp.getChildren().add(replacePath);

        fp.getChildren().add(list[3]);
        fp.getChildren().add(picture);

        fp.getChildren().add(list[4]);
        fp.getChildren().add(remoteFileID);

        fp.getChildren().add(list[5]);
        fp.getChildren().add(version);
        window.setCenter(fp);
    }

    public void load(String path){
        try{
            md.load(path);
        } catch(IOException e){
            System.out.println(e.getStackTrace());
        }

        this.name.setText(md.getName());
        this.path.setText(md.getPath());
        this.replacePath.setText(md.getReplacePath());
        this.picture.setText(md.getPicture());
        this.remoteFileID.setText(md.getRemoteFileID());
        this.version.setText(md.getVersion());
    }

    public ModDescriptor save(){
        md.setName(this.name.getText());
        md.setPath(this.path.getText());
        md.setReplacePath(this.replacePath.getText());
        md.setPicture(this.picture.getText());
        md.setRemoteFileID(this.remoteFileID.getText());
        md.setVersion(this.version.getText());
        return md;
    }
}
