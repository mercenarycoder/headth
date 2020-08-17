package com.developer.headthapp;

public class reportClass {
    String name,type,date,id,icon;

    public reportClass(String name, String type, String date, String id, String icon) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.id = id;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }
}
