package com.developer.headthapp;

public class typeClass {
String title,type,thing1,thing2,thing3,id;

    public typeClass(String title, String type, String thing1, String thing2, String thing3,String id) {
        this.title = title;
        this.type = type;
        this.thing1 = thing1;
        this.thing2 = thing2;
        this.thing3 = thing3;
        this.id=id;
    }

    public String getId() {
        return id;
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
