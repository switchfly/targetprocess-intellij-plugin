package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

public class General {
    @SerializedName("Id")
    private int _id;
    @SerializedName("Name")
    private String _name;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }
}
