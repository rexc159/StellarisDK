package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.GUI.CompSetUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Pretty much a dummy class, as components using different icons is not something recommended.
public class CompSet extends GenericData {
    private String key;
    private String icon;
    private String icon_frame = "1";
    protected final Pattern kv = Pattern.compile("(?m)^\\t?(\\w+) . ([^\\{#\\n]+)(#.+)*");

    public CompSet() {
        super();
        ui = new CompSetUI(this);
    }

    public CompSet(String input) {
        load(input);
        ui = new CompSetUI(this);
    }

    @Override
    public Object load(String input) {
        Matcher kv_match = kv.matcher(input);
        while (kv_match.find()) {
            switch (kv_match.group(1).trim()) {
                case "key":
                    key = kv_match.group(2).replaceAll("\"", "").trim();
                    break;
                case "icon":
                    icon = kv_match.group(2).replaceAll("\"", "").trim();
                    break;
                case "icon_frame":
                    icon_frame = kv_match.group(2).trim();
                    break;
            }
        }
        return this;
    }

    @Override
    public Object getValue(String key) {
        switch (key) {
            case "key":
                return this.key;
            case "icon":
                return this.icon;
            case "icon_frame":
                return this.icon_frame;
            default:
                return null;
        }
    }

    @Override
    public void setValue(String key, Object value, boolean addIfAbsent) {
        switch (key) {
            case "key":
                this.key = value.toString();
            case "icon":
                this.icon = value.toString();
            case "icon_frame":
                this.icon_frame = value.toString();
        }
    }

    @Override
    public String export() {
        return "component_set = {\n" +
                "\tkey = \"" + key + "\"\n" +
                "\t\n" +
                "\ticon = \"" + icon + "\"\n" +
                "\ticon_frame = " + icon_frame + "\n" +
                "}\n";
    }

    @Override
    public String toString() {
        return key;
    }
}