package com.StellarisDK.tools.fileClasses;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A major issue with the current implementation is it DOES NOT SUPPORT ALL LANGUAGES
// Secondly, due to the nature of static variables, all locale files provided to the editor will
// be compressed into a single file
public class Locale {
    private final Pattern kv = Pattern.compile("(\\w+):(\\d) (.+)");
    private static HashMap<String, Pair<Integer, String>> data = new HashMap<>();

    public Locale(String path) throws IOException {
        String input = new String(Files.readAllBytes(Paths.get(path)));

        Matcher kv_match = kv.matcher(input);

        while (kv_match.find()) {
            data.put(kv_match.group(1).trim(), new Pair<Integer, String>(Integer.parseInt(kv_match.group(2).trim()), kv_match.group(3).trim()) {
                @Override
                public String toString() {
                    return getKey() + " " + getValue();
                }
            });
        }
    }

    public static String getLocale(String key){
        return data.get(key).getValue();
    }

    @Override
    public String toString() {
        String out = "l_english:\n";
        for (String key : data.keySet()) {
            out += key+":"+data.get(key).toString()+"\n";
        }
        return out;
    }
}