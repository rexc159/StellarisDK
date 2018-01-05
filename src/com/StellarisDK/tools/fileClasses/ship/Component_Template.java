package com.StellarisDK.tools.fileClasses.ship;

import com.StellarisDK.tools.fileClasses.technology.technology;

import com.StellarisDK.tools.fileClasses.gfx.Icon;

import java.util.HashMap;
import java.util.regex.Pattern;

enum Size{
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE,
    TORPEDO,
    POINT_DEFENSE,
    AUX
}

public abstract class Component_Template {
    protected HashMap<String, Object> data = new HashMap<>();
    protected Pattern kv = Pattern.compile("(?m)^\\t(\\w+) = ([^{].*)");
    protected Pattern cv = Pattern.compile("(?s)(?m)^\\t(\\w+) = \\{(.+?)(^\\t\\}|[^\\t]})");

}