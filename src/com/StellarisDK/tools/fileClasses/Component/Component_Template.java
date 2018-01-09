package com.StellarisDK.tools.fileClasses.Component;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * This class contains potentially all data parsing necessary for a GUI output
 */
public abstract class Component_Template {
    protected HashMap<String, Object> data = new HashMap<>();

    // Pattern matches for single value variable
    // i.e. key, size, power
    protected Pattern kv = Pattern.compile("(?m)^\\t(\\w+) = ([^{].*)");

    // Pattern matches for multi value variables
    // i.e. modifier, prerequisites
    protected Pattern cv = Pattern.compile("(?s)(?m)^\\t(\\w+) = \\{(.+?)(^[^\\n]\\}|})");

    // Pattern matches for variables within cv
    // i.e. ship_shield_hp_add...
    protected Pattern internal = Pattern.compile("(\\w+)( . )?([^\"\\n\\{ ]*)|(#.+)");

}