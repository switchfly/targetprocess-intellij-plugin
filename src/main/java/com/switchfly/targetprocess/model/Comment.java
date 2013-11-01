package com.switchfly.targetprocess.model;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class Comment {

    public final static String[] FIELDS = {"Description", "CreateDate", "General", "Owner"};

    @SerializedName("Description")
    private String description;
    @SerializedName("CreateDate")
    private Date createDate;
    @SerializedName("General")
    private General general;
    @SerializedName("Owner")
    private User owner;

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getAssignableId() {
        return general.id;
    }

    public User getOwner() {
        return owner;
    }

    private class General {
        @SerializedName("Id")
        private int id;
    }
}
