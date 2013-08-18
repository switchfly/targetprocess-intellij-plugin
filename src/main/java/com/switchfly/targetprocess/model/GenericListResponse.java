package com.switchfly.targetprocess.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GenericListResponse<T> {

	@SerializedName("Items")
	private List<T> _items;
	@SerializedName("Next")
	private String next;

	public List<T> getItems() {
		return _items;
	}

	public String getNext() {
		return next;
	}
}
