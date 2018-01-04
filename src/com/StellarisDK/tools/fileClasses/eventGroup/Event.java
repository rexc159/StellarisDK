package com.StellarisDK.tools.fileClasses.eventGroup;

enum Type{
    EVENT,
    COUNTRY,
    PLANET,
    FLEET,
    SHIP,
    POP_FACTION,
    POP
}

public class Event {
    private Type type;
    private String id;
    private String title;
    private String desc;
    private String picture;
    private String location;
    private boolean hide_window;
    private boolean diplomatic;
    private String picture_event_data;
    private boolean is_triggered_only;
    private String mean_time_to_happen;
    private String trigger;
    private String immediat_effect;
    private String option;
    private String after;


}
