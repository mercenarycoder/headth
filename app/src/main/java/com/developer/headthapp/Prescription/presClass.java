package com.developer.headthapp.Prescription;

public class presClass {
    String diesease,date,doctor,img_url,id;

    public presClass(String diesease, String date, String doctor, String img_url, String id) {
        this.diesease = diesease;
        this.date = date;
        this.doctor = doctor;
        this.img_url = img_url;
        this.id = id;
    }

    public String getDiesease() {
        return diesease;
    }

    public String getDate() {
        return date;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getId() {
        return id;
    }
}
