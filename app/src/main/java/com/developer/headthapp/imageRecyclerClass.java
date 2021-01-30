package com.developer.headthapp;

import android.graphics.Bitmap;

public class imageRecyclerClass {
    String imageF,id,type;
    public imageRecyclerClass(String imageF, String id, String type) {
        this.imageF = imageF;
        this.id = id;
        this.type = type;
    }

    public String getImageF() {
        return imageF;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
