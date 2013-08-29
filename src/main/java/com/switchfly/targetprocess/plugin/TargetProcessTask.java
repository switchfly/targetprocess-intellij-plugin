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
import com.switchfly.targetprocess.model.TPObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TargetProcessTask extends Task {

	private final Assignable _assignable;
	private final String _url;
	private final TargetProcessRepository _taskRepository;

	public TargetProcessTask(Assignable assignable, String url, TargetProcessRepository taskRepository) {
		_assignable = assignable;
		_url = url;
		_taskRepository = taskRepository;
	}

	@NotNull
	@Override
	public String getId() {
		return String.valueOf(_assignable.getId());
	}

	@NotNull
	@Override
	public String getSummary() {
		return _assignable.getName();
	}

	@Override
	public String getDescription() {
		return _assignable.getDescription();
	}

	@NotNull
	@Override
	public Comment[] getComments() {
		//List<com.switchfly.targetprocess.model.Comment> comments = _assignable.getComments(); //TODO FIX
		return new Comment[0];
	}

	@Nullable
	@Override
	public TaskRepository getRepository() {
		return _taskRepository;
	}

	@NotNull
	@Override
	public String getNumber() {
		return getId();
	}

	@Nullable
	@Override
	public String getProject() {
		TPObject project = _assignable.getProject();
		return project != null ? project.getName() : "";
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
		String entityType = _assignable.getEntityType().getName();
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
		return _assignable.getModifyDate();
	}

	@Override
	public Date getCreated() {
		return _assignable.getCreateDate();
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
		return _url + "/RestUI/TpView.aspx?id=" + _assignable.getId();
	}
}
