package StellarisDK.FileClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class ModDescriptor extends GenericData {

    public ModDescriptor() {
        super();
    }

    public ModDescriptor(String path) {
        super(path);
    }

    @Override
    public Object load(String path) throws IOException {
        HashMap<String, Object> data = new LinkedHashMap<>();
        for (String key : data.keySet()) {
            data.replace(key, null);
        }
        String input = new String(Files.readAllBytes(Paths.get(path)));

        Matcher kv_match = DataPattern.kv.matcher(input);
        Matcher cv_match = DataPattern.mDSpec.matcher(input);

        while (kv_match.find()) {
            data.put(kv_match.group(1), kv_match.group(3).replaceAll("\"", "")
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
            Collections.addAll(temp, cv_match.group(2).replaceAll("[\"\t]", "").replaceAll("\r", "").trim().split("\n", 0));
            data.put(cv_match.group(1), temp);
        }
        return data;
    }

    @Override
    public String export() {
        String out = "";
        for (String key : data.keySet()) {
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