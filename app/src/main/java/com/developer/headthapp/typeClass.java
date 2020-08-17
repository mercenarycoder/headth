package com.developer.headthapp;

public class typeClass {
String title,type,thing1,thing2,thing3;

    public typeClass(String title, String type, String thing1, String thing2, String thing3) {
        this.title = title;
        this.type = type;
        this.thing1 = thing1;
        this.thing2 = thing2;
        this.thing3 = thing3;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getThing1() {
        return thing1;
    }

    public String getThing2() {
        return thing2;
    }

    public String getThing3() {
        return thing3;
    }
}
