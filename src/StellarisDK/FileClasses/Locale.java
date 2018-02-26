package StellarisDK.FileClasses;

import javafx.scene.control.TreeItem;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A major issue with the current implementation is it DOES NOT SUPPORT ALL LANGUAGES
// Secondly, due to the nature of static variables, all locale files provided to the editor will
// be compressed into a single file
public class Locale {
    private String lang;
    private final Pattern kv = Pattern.compile("(\\w+):(\\d) (.+)");
    private static HashMap<String, Pair<Integer, String>> data = new HashMap<>();

    public Locale(String path) throws IOException {
        String input = new String(Files.readAllBytes(Paths.get(path)));

        Matcher temp = Pattern.compile("(\\w+):[\r\n]").matcher(input);
        if(temp.find())
            lang = temp.group(1);

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
        if(data.get(key) != null)
            return data.get(key).getValue();
        else
            return null;
    }

    public ArrayList<TreeItem> toTree(){
        ArrayList<TreeItem> out = new ArrayList<>();
        for (String key : data.keySet()) {
            out.add(new TreeItem<>(data.get(key)));
        }
        return out;
    }

    public String export(){
        String out = lang+":\r\n";
        for (String key : data.keySet()) {
            out += key+":"+data.get(key).toString()+"\r\n";
        }
        return out;
    }

    @Override
    public String toString() {
        return lang;
    }
}