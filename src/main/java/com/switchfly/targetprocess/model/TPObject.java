package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

public class TPObject {

	@SerializedName("Id")
	private int _id;
	@SerializedName("Name")
	private String _name;

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}
}
