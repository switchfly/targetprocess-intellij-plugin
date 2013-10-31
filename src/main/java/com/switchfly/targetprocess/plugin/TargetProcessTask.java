package com.switchfly.targetprocess.plugin;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.TaskType;
import com.switchfly.targetprocess.TargetProcessIcons;
import com.switchfly.targetprocess.model.Assignable;
import org.jetbrains.annotations.NotNull;

public class TargetProcessTask extends Task {

    private final Assignable assignable;
    private final TargetProcessRepository taskRepository;

    public TargetProcessTask(Assignable assignable, TargetProcessRepository taskRepository) {
        this.assignable = assignable;
        this.taskRepository = taskRepository;
    }

    @NotNull
    @Override
    public String getId() {
        return String.valueOf(assignable.getId());
    }

    @NotNull
    @Override
    public String getSummary() {
        return assignable.getName();
    }

    @Override
    public String getDescription() {
        return assignable.getDescription();
    }

    @NotNull
    @Override
    public Comment[] getComments() {
        List<com.switchfly.targetprocess.model.Comment> assignableComments = assignable.getComments(); //TODO improve
        Comment[] comments = new Comment[assignableComments.size()];
        for (int i = 0; i < assignableComments.size(); i++) {
            comments[i] = new TargetProcessComment(assignableComments.get(i));
        }
        return comments;
    }

    @Override
    public TaskRepository getRepository() {
        return taskRepository;
    }

    @NotNull
    @Override
    public String getNumber() {
        return getId();
    }

    @Override
    public String getProject() {
        return assignable.getProjectName();
    }

    @NotNull
    @Override
    public Icon getIcon() {
        switch (getType()) {
            case FEATURE:
                return TargetProcessIcons.UserStory;
            case BUG:
                return TargetProcessIcons.Bug;
        }

        return TargetProcessIcons.Task;
    }

    @NotNull
    @Override
    public TaskType getType() {
        String entityType = assignable.getEntityTypeName();
        if ("UserStory".equalsIgnoreCase(entityType)) {
            return TaskType.FEATURE;
        }
        if ("Bug".equalsIgnoreCase(entityType)) {
            return TaskType.BUG;
        }
        return TaskType.OTHER;
    }

    @Override
    public Date getUpdated() {
        return assignable.getModifyDate();
    }

    @Override
    public Date getCreated() {
        return assignable.getCreateDate();
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isIssue() {
        return true;
    }

    @Override
    public String getIssueUrl() {
        return taskRepository.getUrl() + "/RestUI/TpView.aspx?id=" + assignable.getId();
    }
}
