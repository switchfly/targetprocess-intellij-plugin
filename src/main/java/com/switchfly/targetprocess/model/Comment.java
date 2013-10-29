package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment {

    @SerializedName("Description")
    private String description;
    @SerializedName("CreateDate")
    private Date createDate;
    @SerializedName("Owner")
    private User owner;

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public User getOwner() {
        return owner;
    }
}
