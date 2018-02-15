package com.StellarisDK.Tools.FileClasses.Component;

import com.StellarisDK.Tools.FileClasses.DataPattern;
import com.StellarisDK.Tools.FileClasses.GenericData;
import com.StellarisDK.Tools.FileClasses.Locale;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

enum CompType {
    UTILITY,
    STRIKE_CRAFT,
    WEAPON
}

public class Component extends GenericData {
    public static int tab = 1;
    private CompType type;

    public Component(String path){
        super(path);
    }

    @Override
    public Object load(String input) {
        HashMap<String, Object> data = new LinkedHashMap<>();

        Matcher kv_match = DataPattern.kv.matcher(input);

        Matcher sC_match = DataPattern.sComplex.matcher(input);
        Matcher mC_match = DataPattern.mComplex.matcher(input);

        while (kv_match.find()) {
            data.put(kv_match.group(1).trim(), kv_match.group(3).replaceAll("\"", "").trim());
        }

        while (sC_match.find()) {
            Matcher sCs_match = DataPattern.sComplex_sub.matcher(sC_match.group(2));
            if (sCs_match.find() && sCs_match.group(2) != null) {
                HashMap<String, Pair<String, String>> sCsMap = new HashMap<String, Pair<String, String>>() {
                    @Override
                    public String toString() {
                        String out = "{";
                        for (String key : keySet()) {
                            out += " " + key + " " + this.get(key).getKey() + " " + this.get(key).getValue();
                        }
                        return out + " }";
                    }
                };
                do {
                    sCsMap.put(sCs_match.group(1).trim(), new Pair<>(sCs_match.group(2).trim(), sCs_match.group(3).trim()));
                } while (sCs_match.find());
                data.put(sC_match.group(1).trim(), sCsMap);
            } else {
                LinkedList<String> sCsList = new LinkedList<String>() {
                    @Override
                    public String toString() {
                        String out = "{";
                        for (String i : this) {
                            out += " \"" + i + "\"";
                        }
                        return out + " }";
                    }
                };
                do {
                    sCsList.add(sCs_match.group(1).trim());
                } while (sCs_match.find());
                data.put(sC_match.group(1).trim(), sCsList);
            }
        }

        while (mC_match.find()) {
//            System.out.println(mC_match.group(3).replaceAll("(?m)^\t",""));
            data.put(mC_match.group(1).trim(), load(mC_match.group(3).replaceAll("(?m)^\t","")));
//            System.out.println("\t"+mC_match.group(1).trim()+":");
//            load(mC_match.group(3).replaceAll("(?m)^\t",""));
        }
        return data;
    }

    @Override
    public String export() {
        String out = "";
        for (String key : data.keySet()) {
            if (data.get(key) != null) {
                if (data.get(key) instanceof String)
                    out += key + " = \"" + data.get(key).toString() + "\"\n";
                else
                    out += key + " = " + data.get(key).toString() + "\n";
            }
        }
        return out;
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }

    @Override
    public String toString() {
        String temp = Locale.getLocale((String) data.get("key"));
        if (temp != null)
            return temp;
        else
            return data.get("key").toString();
    }
}