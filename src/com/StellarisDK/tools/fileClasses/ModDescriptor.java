package com.StellarisDK.tools.fileClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModDescriptor {
    private HashMap<String, Object> data = new HashMap<>();
    private String keys[] = {"name", "path", "archive", "dependencies", "tags", "picture", "remote_file_id", "supported_version"};

    // Pattern matches for single value variable
    // i.e. key, size, power
    protected Pattern kv = Pattern.compile("(\\w+)=([^{].*)");

    // Pattern matches for multi value variables
    // i.e. modifier, prerequisites
    protected Pattern cv = Pattern.compile("(?s)(?m)(\\w+)=\\{(.+?)\\}");

    public ModDescriptor() {
        for (String key : keys) {
            data.put(key, null);
        }
    }

    public String[] getKeys() {
        return keys;
    }

    public Object getValue(String key){
        return data.get(key);
    }

    public void setValue(String key, Object value){
        data.replace(key, value);
    }

    public void load(String path) throws IOException {
        String input = new String(Files.readAllBytes(Paths.get(path)));

        Matcher kv_match = kv.matcher(input);
        Matcher cv_match = cv.matcher(input);

        while (kv_match.find()) {
            data.replace(kv_match.group(1), kv_match.group(2).replaceAll("\"", ""));
        }

        while (cv_match.find()) {
            LinkedList<String> temp = new LinkedList<String>() {
                @Override
                public String toString() {
                    String out = "{\n";
                    for (String i : this) {
                        out += "\t\""+i.trim() +"\"\n";
                    }
                    return out + "}\n";
                }
            };
            Collections.addAll(temp, cv_match.group(2).replaceAll("[\"\t]","").trim().split("\n",0));
            data.replace(cv_match.group(1), temp);
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (String key : keys) {
            if(data.get(key)!=null){
                if (data.get(key) instanceof String){
                    if(((String) data.get(key)).length() != 0)
                        out += key + "=\"" + data.get(key).toString() + "\"\n";
                }
                else {
                    out += key + "=" + data.get(key).toString();
                }
            }
        }
        return out;
    }
}