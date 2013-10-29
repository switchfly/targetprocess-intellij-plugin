package com.switchfly.targetprocess.plugin;

import com.intellij.tasks.Comment;
import com.switchfly.targetprocess.model.User;

import java.util.Date;

public class TargetProcessComment extends Comment {

    private final com.switchfly.targetprocess.model.Comment comment;

    public TargetProcessComment(com.switchfly.targetprocess.model.Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getText() {
        return comment.getDescription();
    }

    @Override
    public String getAuthor() {
        User owner = comment.getOwner();
        return owner.getFirstName() + ' ' + owner.getLastName();
    }

    @Override
    public Date getDate() {
        return comment.getCreateDate();
    }
}
