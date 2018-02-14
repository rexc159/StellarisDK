package com.StellarisDK.Tools.FileClasses;

import java.util.regex.Pattern;

public class DataPattern {

    // Pattern matches for single value variable
    // i.e. key, size, power
    // Pattern Groups :
    // Group 1 : Key
    // Group 2 : Modifier specific for conditions
    // Group 3 : Value
    // Group 4 : Comments, currently voided.
    protected static final Pattern kv = Pattern.compile("(?m)^\\t?\\s{0,4}(\\w+)(\\s?[=<>]\\s?)([^\\s\\{#\\n]+)(#.+)*");

    // Pattern matches for single line "complex" key variable
    // i.e. modifier, preq...
    protected static final Pattern type1 = Pattern.compile("(?m)^\\t(\\w+) = \\{\\s*(.+?)\\}");

    // Pattern matches specific for list values
    // i.e. weapon damage, windup, tags...
    // Group 1 : Key
    // Group 2 : Modifier specific for conditions
    // Group 3 : Value
    protected static final Pattern type1_sub = Pattern.compile("\\t?(\\w+)( . )?([\\d.]+)?");

    // Pattern matches for multi line "complex" key variable
    // i.e. immediate, trigger, mainly event related variables
    protected static final Pattern type2 = Pattern.compile("(?s)(?m)^\\t?(\\w+)( ?= ?)\\{[\\r\\n](.+?)^\\t?\\}");
}
