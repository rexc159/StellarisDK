package com.StellarisDK.Tools.FileClasses.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Pretty much a dummy class, as components using different icons is not something recommended.
public class CompSet {
    private String key;
    private String icon;
    private int icon_frame = 1;
    protected final Pattern kv = Pattern.compile("(?m)^\\t?(\\w+) . ([^\\{#\\n]+)(#.+)*");

    public CompSet(String input){
        Matcher kv_match = kv.matcher(input);
        while(kv_match.find()){
            switch (kv_match.group(1).trim()){
                case "key":
                    key = kv_match.group(2).replaceAll("\"","").trim();
                    break;
                case "icon":
                    icon = kv_match.group(2).replaceAll("\"","").trim();
                    break;
                case "icon_frame":
                    icon_frame = Integer.parseInt(kv_match.group(2).trim());
                    break;
            }
        }
    }

    public String output(){
        return  "component_set = {\n" +
                "\tkey = \""+key+"\"\n" +
                "\t\n" +
                "\ticon = \""+icon+"\"\n" +
                "\ticon_frame = "+icon_frame+"\n" +
                "}\n";
    }

    @Override
    public String toString() {
        return key;
    }
}
