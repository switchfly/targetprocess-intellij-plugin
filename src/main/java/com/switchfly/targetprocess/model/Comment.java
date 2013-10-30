package com.switchfly.targetprocess.model;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class Comment {

    public final static String[] INCLUDE = {"Description", "CreateDate", "Owner"};

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
