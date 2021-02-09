package com.developer.headthapp;

public class reportClass {
    String name,type,date,id,icon,typeD,cats,details;

    public String getTypeD() {
        return typeD;
    }

    public reportClass(String name, String type, String date, String id, String icon, String typeD,String cats,String details) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.id = id;
        this.icon = icon;
        this.typeD=typeD;
        this.cats=cats;
        this.details=details;
    }

    public String getDetails() {
        return details;
    }

    public String getCats() {
        return cats;
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
