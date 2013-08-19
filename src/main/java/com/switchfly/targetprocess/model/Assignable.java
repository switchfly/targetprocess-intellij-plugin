package com.switchfly.targetprocess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Assignable extends TPObject {

	private final List<Comment> _comments = new ArrayList<Comment>();
	@SerializedName("Description")
	private String _description;
	@SerializedName("CreateDate")
	private Date _createDate;
	@SerializedName("ModifyDate")
	private Date _modifyDate;
	@SerializedName("EntityType")
	private TPObject _entityType;
	@SerializedName("Project")
	private TPObject _project;

	public List<Comment> getComments() {
		return _comments;
	}

	public String getDescription() {
		return _description;
	}

	public TPObject getEntityType() {
		return _entityType;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Date getModifyDate() {
		return _modifyDate;
	}

	public TPObject getProject() {
		return _project;
	}
}
