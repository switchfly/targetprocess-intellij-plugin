package com.switchfly.targetprocess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Assignable {

    private final List<Comment> _comments = new ArrayList<Comment>();
    @SerializedName("Id")
    private int _id;
    @SerializedName("Name")
    private String _name;
    @SerializedName("Description")
    private String _description;
    @SerializedName("CreateDate")
    private Date _createDate;
    @SerializedName("ModifyDate")
    private Date _modifyDate;
    @SerializedName("EntityType")
    private EntityType _entityType;
    @SerializedName("Project")
    private Project _project;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public List<Comment> getComments() {
        return _comments;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public EntityType getEntityType() {
        return _entityType;
    }

    public void setEntityType(EntityType entityType) {
        _entityType = entityType;
    }

    public Date getCreateDate() {
        return _createDate;
    }

    public void setCreateDate(Date createDate) {
        _createDate = createDate;
    }

    public Date getModifyDate() {
        return _modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        _modifyDate = modifyDate;
    }

    public Project getProject() {
        return _project;
    }

    public void setProject(Project project) {
        _project = project;
    }
}
