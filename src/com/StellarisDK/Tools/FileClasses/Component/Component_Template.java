package com.StellarisDK.Tools.FileClasses.Component;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains potentially all data parsing necessary for a GUI output
 */
public abstract class Component_Template {
    protected HashMap<String, Object> data = new HashMap<>();

    // Pattern matches for single value variable
    // i.e. key, size, power
    protected final Pattern kv = Pattern.compile("(?m)^\\t?(\\w+) . ([^\\{#\\n]+)(#.+)*");

    // Pattern matches for all "complex" key variable
    // i.e. modifier, preq...
    protected final Pattern type1 = Pattern.compile("(?m)^\\t(\\w+) = \\{\\s*(.+?)\\}");
    protected final Pattern type1_sub = Pattern.compile("(?m)(\\w+) = ([^ \\n]+)|(\\w+)");
    protected final Pattern type2 = Pattern.compile("(?s)(?m)^\\t(\\w+) = \\{[\\r\\n](.+?)^\t\\}");

    public void parse(String input){

        Matcher kv_match = kv.matcher(input);

        Matcher t1_match = type1.matcher(input);
        Matcher t2_match = type2.matcher(input);

        while(kv_match.find()){
            data.replace(kv_match.group(1).trim(), kv_match.group(2).replaceAll("\"","").trim());
//            System.out.println(kv_match.group(1).trim()+":!"+kv_match.group(2).trim());
        }

        while(t1_match.find()){
            data.replace(t1_match.group(1).trim(), t1_match.group(2).trim());
            Matcher t1s_match = type1_sub.matcher(t1_match.group(2));
            while(t1s_match.find()){
                if(t1s_match.group(1) !=null)
                    System.out.println(t1s_match.group(1)+":"+t1s_match.group(2));
                else
                    System.out.println(t1s_match.group(3));
            }
//            System.out.println(t1_match.group(1).trim()+":{");
//            parse(t1_match.group(2).replaceAll("(?m)^\t",""));
        }
        while(t2_match.find()){
            data.replace(t2_match.group(1).trim(), t2_match.group(2).trim());
//            System.out.println("\t"+t2_match.group(1).trim()+":");
//            parse(t2_match.group(2).replaceAll("(?m)^\t",""));
        }
    }
}