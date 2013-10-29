package com.switchfly.targetprocess.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.config.TaskRepositoryEditor;
import com.intellij.tasks.impl.BaseRepositoryType;
import com.intellij.util.Consumer;
import com.switchfly.targetprocess.TargetProcessIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class TargetProcessRepositoryType extends BaseRepositoryType<TargetProcessRepository> {

    public TargetProcessRepositoryType() {
        super();
    }

    @NotNull
    @Override
    public String getName() {
        return "TargetProcess";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return TargetProcessIcons.Logo;
    }

    @NotNull
    @Override
    public TaskRepositoryEditor createEditor(TargetProcessRepository repository, Project project, Consumer<TargetProcessRepository> changeListener) {
        return new TargetProcessRepositoryEditor(project, repository, changeListener);
    }

    @NotNull
    @Override
    public TaskRepository createRepository() {
        return new TargetProcessRepository(this);
    }

    @Override
    public Class<TargetProcessRepository> getRepositoryClass() {
        return TargetProcessRepository.class;
    }
    
}
