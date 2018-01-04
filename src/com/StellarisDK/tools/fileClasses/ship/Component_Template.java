package com.StellarisDK.tools.fileClasses.ship;

import com.StellarisDK.tools.fileClasses.technology.technology;

import com.StellarisDK.tools.fileClasses.gfx.Icon;

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
    private String key;
    private Size size;
    private Icon Icon;
    private double power;
    private double cost;
    private technology preReq;
    private boolean hidden;
    private ComponentSet compSet;
    private Component_Template upgrade=null;

    public String getKey() {
        return key;
    }

    public Size getSize() {
        return size;
    }

    public Icon getIcon() {
        return Icon;
    }

    public double getPower() {
        return power;
    }

    public double getCost() {
        return cost;
    }

    public technology getPreReq() {
        return preReq;
    }

    public boolean isHidden() {
        return hidden;
    }

    public ComponentSet getCompSet() {
        return compSet;
    }

    public Component_Template getUpgrade() {
        return upgrade;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setIcon(Icon icon) {
        Icon = icon;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPreReq(technology preReq) {
        this.preReq = preReq;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setCompSet(ComponentSet compSet) {
        this.compSet = compSet;
    }

    public void setUpgrade(Component_Template upgrade) {
        this.upgrade = upgrade;
    }
}