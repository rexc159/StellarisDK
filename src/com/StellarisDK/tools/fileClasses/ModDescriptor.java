package com.StellarisDK.tools.fileClasses;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ModDescriptor {
    private String name;
    private String path;
    private LinkedList<String> dependencies = new LinkedList<>();
    private String replacePath;
    private LinkedList<String> tags = new LinkedList<>();
    private String picture;
    private String remoteFileID;
    private String version;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public LinkedList<String> getDependencies() {
        return dependencies;
    }

    public String getReplacePath() {
        return replacePath;
    }

    public LinkedList<String> getTags() {
        return tags;
    }

    public String getPicture() {
        return picture;
    }

    public String getRemoteFileID() {
        return remoteFileID;
    }

    public String getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDependencies(LinkedList<String> dependencies) {
        this.dependencies = dependencies;
    }

    public void setReplacePath(String replacePath) {
        this.replacePath = replacePath;
    }

    public void setTags(LinkedList<String> tags) {
        this.tags = tags;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setRemoteFileID(String remoteFileID) {
        this.remoteFileID = remoteFileID;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void load(String path) throws IOException {
        File file = new File(path);
        Scanner scan = new Scanner(file);
        name = scan.nextLine().split("=\"")[1].replaceAll("\"", "");
        String temp = scan.nextLine();
        if (temp.contains("path")) {
            this.path = temp.split("=\"")[1].replaceAll("\"", "");
            temp = scan.nextLine();
        }

        if (temp.contains("dependencies")) {
            do {
                dependencies.add(scan.nextLine().replaceAll("\"", ""));
                temp = scan.nextLine();
            } while (!temp.contains("}"));
            temp = scan.nextLine();
        }

        if (temp.contains("tags")) {
            do {
                tags.add(scan.nextLine().replaceAll("[\t\"]", ""));
                temp = scan.nextLine();
            } while (!temp.contains("}"));
        }

        while (scan.hasNextLine()) {
            temp = scan.nextLine();
            if (temp.contains("replacePath")) {
                replacePath = temp.split("=\"")[1].replaceAll("\"", "");
            } else if (temp.contains("picture")) {
                picture = temp.split("=\"")[1].replaceAll("\"", "");
            } else if (temp.contains("remoteFileID")) {
                remoteFileID = temp.split("=\"")[1].replaceAll("\"", "");
            } else if (temp.contains("supported_version")) {
                version = temp.split("=\"")[1].replaceAll("\"", "");
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String output() {
        System.out.println("name=\"" + name + "\"");
        System.out.println("path=\"" + path + "\"");
        if (dependencies.size() != 0) {
            System.out.println("dependencies={");
            for (String dependency : dependencies) {
                System.out.println("\t\"" + dependency + "\"");
            }
            System.out.println("}");
        }
        System.out.println("tags={");
        for (String tag : tags) {
            System.out.println("\t\"" + tag + "\"");
        }
        System.out.println("}");
        if(picture!=null){
            System.out.println("picture=\"" + picture + "\"");
        }
        if(remoteFileID !=null){
            System.out.println("remoteFileID=\"" + remoteFileID + "\"");
        }
        System.out.println("supported_version=\"" + version + "\"");
        return "";
    }
}