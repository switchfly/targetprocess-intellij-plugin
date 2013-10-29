package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenericList<T> { //TODO move inside TPParser

    @SerializedName("Items")
    private List<T> items;

    public List<T> getItems() {
        return items;
    }
}