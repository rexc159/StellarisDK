package com.StellarisDK.Tools.FileClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class ModDescriptor extends GenericData {

// Pattern matches for single value variable
// i.e. key, size, power
//     protected Pattern kv = Pattern.compile("(\\w+)=([^{].*)");

// Pattern matches for multi value variables
// i.e. modifier, prerequisites
//     protected Pattern cv = Pattern.compile("(?s)(?m)(\\w+)=\\{(.+?)\\}");

    public ModDescriptor(){
        super(MapKeys.modDescriptor);
    }

    public ModDescriptor(String path){
        super(MapKeys.modDescriptor, path);
    }

    @Override
    public Object load(String path) throws IOException {
        for (String key : MapKeys.modDescriptor) {
            data.replace(key, null);
        }
        String input = new String(Files.readAllBytes(Paths.get(path)));

        Matcher kv_match = DataPattern.kv.matcher(input);
        Matcher cv_match = DataPattern.mComplex.matcher(input);

        while (kv_match.find()) {
            data.replace(kv_match.group(1), kv_match.group(3).replaceAll("\"", "")
                    .replaceAll("\\\\\\\\", "\\\\"));
        }

        while (cv_match.find()) {
            LinkedList<String> temp = new LinkedList<String>() {
                @Override
                public String toString() {
                    String out = "{\n";
                    for (String i : this) {
                        out += "\t\"" + i.trim() + "\"\n";
                    }
                    return out + "}\n";
                }
            };
            Collections.addAll(temp, cv_match.group(3).replaceAll("[\"\t]", "").replaceAll("\r", "").trim().split("\n", 0));
            data.replace(cv_match.group(1), temp);
        }
        return this.toString();
    }

    @Override
    public String export() {
        String out = "";
        for (String key : MapKeys.modDescriptor) {
            if (data.get(key) != null) {
                if (data.get(key) instanceof String) {
                    if (((String) data.get(key)).length() != 0)
                        out += key + "=\"" + data.get(key).toString() + "\"\n";
                } else {
                    out += key + "=" + data.get(key).toString();
                }
            }
        }
        return out.replaceAll("\\\\", "\\\\\\\\");
    }

    @Override
    public String toString() {
        return data.get("name").toString();
    }
}