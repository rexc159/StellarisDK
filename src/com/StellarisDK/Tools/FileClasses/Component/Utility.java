package com.StellarisDK.Tools.FileClasses.Component;

import com.StellarisDK.Tools.FileClasses.DataPattern;
import com.StellarisDK.Tools.FileClasses.GenericData;
import com.StellarisDK.Tools.FileClasses.Locale;
import com.StellarisDK.Tools.FileClasses.MapKeys;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class Utility extends GenericData {
    public static int tab = 1;

    public Utility() {
        super(MapKeys.util);
    }

    public Utility(String input) {
        this();
        this.load(input);
    }

    @Override
    public Object load(String input) {
//        HashMap<String, List<Object>> mCData = new HashMap<>();

        Matcher kv_match = DataPattern.kv.matcher(input);

        Matcher sC_match = DataPattern.sComplex.matcher(input);
        Matcher mC_match = DataPattern.mComplex.matcher(input);

        while (kv_match.find()) {
            data.replace(kv_match.group(1).trim(), kv_match.group(3).replaceAll("\"", "").trim());
        }

        while (sC_match.find()) {
            Matcher sCs_match = DataPattern.sComplex_sub.matcher(sC_match.group(2));
            if (sCs_match.find() && sCs_match.group(2) != null) {
                HashMap<String, Object> sCsMap = new HashMap<String, Object>() {
                    @Override
                    public String toString() {
                        String out = "";
                        for (String key : keySet()) {
                            out += key + "\n";
                        }
                        return out;
                    }
                };
                do {
                    sCsMap.put(sCs_match.group(1).trim(), sCs_match.group(3).trim());
                } while (sCs_match.find());
                data.replace(sC_match.group(1).trim(), sCsMap);
            } else {
                LinkedList<String> sCsList = new LinkedList<String>() {
                    @Override
                    public String toString() {
                        String out = "{";
                        for (String i : this) {
                            out += " \"" + i.trim() + "\"";
                        }
                        return out + " }";
                    }
                };
                do {
                    sCsList.add(sCs_match.group(2));
                } while (sCs_match.find());
                data.replace(sC_match.group(1).trim(), sCsList);
            }
        }

        while (mC_match.find()) {
            data.replace(mC_match.group(1).trim(), mC_match.group(3).trim());
//            System.out.println("\t"+mC_match.group(1).trim()+":");
//            load(mC_match.group(2).replaceAll("(?m)^\t",""));
        }
        return null;
    }

    @Override
    public String export() {
        String out = "";
        for (String key : MapKeys.util) {
            if (data.get(key) != null) {
                if (data.get(key) instanceof String)
                    out += key + " = \"" + data.get(key).toString() + "\"\n";
                else
                    out += key + " = " + data.get(key).toString();
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