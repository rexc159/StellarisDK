package StellarisDK.FileClasses;

import java.util.regex.Pattern;

public class DataPattern {
    /*
        Groups are always defined as follows:
        1 : Key
        2 : Modifier specific for conditions
        3 : Value
        4 : Comments, if applicable
     */

    // Pattern matches for single value variable
    // i.e. key, size, power
    public static final Pattern kv = Pattern.compile("(?m)^\\t?(\\w+)(\\s?[=<>]+\\s?)([^\\s\\{#\\n]+)(#.+)*");

    // Pattern matches for single lines objects
    // Group 1-3:   Special match for hsv
    // Group 4-6:   Recursion Pattern
    // Group 7-9:   Standard key value pair
    // Group 10-12: String literal pair
    // Group 13: Special match for tags
    public static final Pattern sLre = Pattern.compile("([\\w:]+)(\\s*[=><]+\\s*)(hsv\\s*\\{.+?\\})|([\\w:]+)(\\s*[=><]+\\s*)\\{(.+?\\})|([\\w:]+)(\\s*[=><]+\\s*)([^\"]+?)\\s|([\\w:]+)(\\s*[=><]+\\s*)(.+?\")\\s*|(\\w+)");

    // Pattern matches for main data structure
    // Group 1-4:   Multi line recursion
    // Group 5-8:   Single line
    public static final Pattern newCombine = Pattern.compile("(?m)^\\s?(\\w+)(\\s*[=<>]+\\s*)\\{(\\s*#.*)?[\\r\\n]([\\W\\D\\S]+?)^\\s?\\}|^\\s?(\\w+)(\\s*[=<>]+\\s*)([^#\\r\\n]+)(#.+)?");

    // Mod Descriptor Specific Pattern
    public static final Pattern mDSpec = Pattern.compile("(?s)(?m)^\\t?(\\w+)=\\{[\\r\\n](.+?)^\\}");
}