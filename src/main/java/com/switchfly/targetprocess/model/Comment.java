package com.switchfly.targetprocess.model;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class Comment extends TPObject {

	@SerializedName("Description")
	private String _description;
	@SerializedName("Owner")
	private Owner _owner;
	@SerializedName("CreateDate")
	private Date _createDate;
	@SerializedName("General")
	private TPObject _general;

	public String getDescription() {
		return _description;
	}

	public Owner getOwner() {
		return _owner;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public TPObject getGeneral() {
		return _general;
	}
}
