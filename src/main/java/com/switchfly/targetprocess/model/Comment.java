package com.switchfly.targetprocess.model;

import java.util.Date;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public class Comment extends com.intellij.tasks.Comment {

    @SerializedName("Id")
    private int _id;
    @SerializedName("Description")
    private String _description;
    @SerializedName("Owner")
    private Owner _owner;
    @SerializedName("CreateDate")
    private Date _createDate;
    @SerializedName("General")
    private General _general;

    @Override
    public String getText() {
        return _description;
    }

    @Nullable
    @Override
    public String getAuthor() {
        return _owner.toString();
    }

    @Nullable
    @Override
    public Date getDate() {
        return _createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Comment comment = (Comment) o;

        return _id == comment._id;
    }

    @Override
    public int hashCode() {
        return _id;
    }

    public General getGeneral() {
        return _general;
    }

    public void setGeneral(General general) {
        _general = general;
    }
}
