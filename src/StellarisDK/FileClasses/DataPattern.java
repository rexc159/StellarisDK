package StellarisDK.FileClasses;

import java.util.regex.Pattern;

public class DataPattern {


    // Pattern matches for single value variable
    // i.e. key, size, power
    // Pattern Groups :
    // Group 1 : Key
    // Group 2 : Modifier specific for conditions
    // Group 3 : Value
    // Group 4 : Comments, currently voided.
    public static final Pattern kv = Pattern.compile("(?m)^\\t?(\\w+)(\\s?[=<>]+?\\s?)([^\\s\\{#\\n]+)(#.+)*");

    // Pattern matches for single line "complex" key variable
    // i.e. modifier, preq...
//    public static final Pattern sComplex = Pattern.compile("(?m)^\\t(\\w+) = \\{(.+?)\\}[\\r\\n]");

    // Pattern matches specific for list values
    // i.e. weapon damage, windup, tags...
    // Group 1 : Key
    // Group 2 : Modifier specific for conditions
    // Group 3 : Value
    public static final Pattern sComplex_sub = Pattern.compile("\\t?([-\\w\\d.\"]+)( .{1,2} )?([^\\s\\{\\}]+)?");

    // Pattern matches for multi line "complex" key variable
    // i.e. immediate, trigger, mainly event related variables
//    public static final Pattern mComplex = Pattern.compile("(?s)(?m)^\\t(\\w+)( ?= ?)\\{\\s*[\\r\\n](.+?)^\\t?\\}");


    // Combined Pattern
    // Group 1 : Key
    // Group 2 : Modifier specific for conditions
    // Group 3 : Value
    // Group 4 : Comments, currently voided.

    // Group 5 : Key
    // Group 6 : Recursion Pattern to sComplex_sub
    // Group 7 : Comments, currently voided.

    // Group 8 : Key
    // Group 9 : Modifier specific for conditions
    // Group 10 : Recursion Pattern to C_MATCH

    public static final Pattern C_MATCH = Pattern.compile("(?m)^\\s?(\\w+)(\\s*[=<>]+?\\s*)([^\\s\\{#\\n]+)(#.+)*|" +
            "^\\s(\\w+)\\s*=\\s*\\{(.+?)\\}(\\s*#.+?)?[\\r\\n]|" +
            "^\\s(\\w+)(\\s*=\\s*)\\{\\s*[\\r\\n]([\\W\\D\\S]+?)^\\t?\\}");

    // Mod Descriptor Specific Pattern
    public static final Pattern mDSpec = Pattern.compile("(?s)(?m)^\\t?(\\w+)=\\{[\\r\\n](.+?)^\\}");
}