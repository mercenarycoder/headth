package com.developer.headthapp.NotificationsDir;

public class notiClass {
    String id,title,date,decription,status;

    public notiClass(String id, String title, String date, String decription,String status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.decription = decription;
        this.status=status;
    }

    public String getStatus() {
        return status;
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
