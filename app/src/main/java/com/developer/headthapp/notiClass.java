package com.developer.headthapp;

public class notiClass {
    String id,title,date,decription;

    public notiClass(String id, String title, String date, String decription) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.decription = decription;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDecription() {
        return decription;
    }
}
