package com.switchfly.targetprocess.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Assignable {

    public final static String[] INCLUDE = {"Id", "Name", "Description", "CreateDate", "ModifyDate", "EntityType", "Project"};

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

    private final List<Comment> comments = new LinkedList<Comment>();

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

    public String getProjectName() {
        return project.name;
    }

    public String getEntityTypeName() {
        return entityType.name;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    private class TPObject {
        @SerializedName("Name")
        private String name;
    }
}
