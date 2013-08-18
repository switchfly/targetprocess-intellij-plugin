package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

public class Priority {

    @SerializedName("Name")
    private String _name;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }
}
