package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.GUI.ModDescUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

public class ModDescriptor extends GenericData {

    public ModDescriptor() {
        data = new DataMap();
        name = "New Mod";
        ui = new ModDescUI(this);
    }

    public ModDescriptor(String path) {
        data = load(path);
        ui = new ModDescUI(this);
    }

    @Override
    public ModDescriptor createNew(){
        return new ModDescriptor();
    }

    @Override
    public void setValue(String key, Object value, boolean addIfAbsent, int index) {
        if (addIfAbsent) {
            data.put(key, value);
        } else {
            data.replace(key, value);
        }
    }

    @Override
    public DataMap load(String path) {
        DataMap<Object> data = new DataMap<>();
        for (String key : data.keySet()) {
            data.replace(key, null);
        }
        String input;
        try {
            input = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            System.out.println("Error: Unable to load .mod");
            return null;
        }

        Matcher kv_match = DataPattern.kv.matcher(input);
        Matcher cv_match = DataPattern.mDSpec.matcher(input);

        while (kv_match.find()) {
            data.put(kv_match.group(1), kv_match.group(3).replaceAll("\"", "")
                    .replaceAll("\\\\\\\\", "\\\\"));
        }

        while (cv_match.find()) {
            ArrayList<String> temp = new ArrayList<String>() {
                @Override
                public String toString() {
                    String out = "{\n";
                    for (String i : this) {
                        out += "\t\"" + i.trim() + "\"\n";
                    }
                    return out + "}\n";
                }
            };
            Collections.addAll(temp, cv_match.group(2).replaceAll("[\"\t]", "").replaceAll("\r", "").trim().split("\n", 0));
            data.put(cv_match.group(1), temp);
        }
        return data;
    }

    public String getDir() {
        return data.get("path").toString().split("/")[1].replaceAll("\"", "").trim();
    }

    @Override
    public String export() {
        String out = "";
        for (Object key : data.keySet()) {
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
}