package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assignable {

    private final List<Comment> comments = new ArrayList<Comment>();

    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("CreateDate")
    private Date createDate;
    @SerializedName("ModifyDate")
    private Date modifyDate;
    @SerializedName("EntityType")
    private TPObject entityType;
    @SerializedName("Project")
    private TPObject project;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public String getProject() {
        return project.name;
    }

    public String getEntityType() {
        return entityType.name;
    }

    private class TPObject {
        @SerializedName("Name")
        private String name;
    }
}
