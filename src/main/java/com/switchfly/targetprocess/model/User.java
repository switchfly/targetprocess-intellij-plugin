package com.switchfly.targetprocess.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Id")
    private int _id;
    @SerializedName("FirstName")
    private String _firstName;
    @SerializedName("LastName")
    private String _lastName;
    @SerializedName("Email")
    private String _email;
    @SerializedName("Login")
    private String _login;

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

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public String getLogin() {
        return _login;
    }

    public void setLogin(String login) {
        _login = login;
    }
}