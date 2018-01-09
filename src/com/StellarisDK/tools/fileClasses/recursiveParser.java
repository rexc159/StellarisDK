package com.StellarisDK.tools.fileClasses;

import java.util.regex.Pattern;

public class recursiveParser {

    private final static Pattern object = Pattern.compile("(?s)(?m)(^\\w+)\\s=\\s\\{(.+?)\\n\\}\\n?");
    private final static Pattern constant = Pattern.compile("(\\@\\w+) = (.+)");
    private final static Pattern repeater = Pattern.compile("(?s)(?m)^(\\w+) = \\{\\s*([^\\}]+?)\\}");
    private final static Pattern kv = Pattern.compile("(?m)^(\\w+) = ([^{#\\n]+)(#.+)*");

    public recursiveParser(String input){

    }
}
