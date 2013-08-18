package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("FirstName")
    private String _firstName;
    @SerializedName("LastName")
    private String _lastName;
    @SerializedName("Id")
    private int _id;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    @Override
    public String toString() {
        return _firstName + ' ' +
            _lastName;
    }
}
