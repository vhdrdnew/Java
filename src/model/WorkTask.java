package model;

import exceptions.DomainException;
import java.util.Objects;

public non-sealed class WorkTask extends Task {

    private final String projectName;

    public WorkTask(String title, String priority, double estimatedHours, int deadlineDays, boolean isCompleted, CategoryTag tag, String projectName)
            throws DomainException {
        super(title, priority, estimatedHours, deadlineDays, isCompleted, tag);
        this.projectName = (projectName == null || projectName.isBlank()) ? "General" : projectName; // перевантаження конструкторів з делегуванням через this(...)
    }

    public WorkTask(String title, String priority, double estimatedHours, int deadlineDays, String projectName)
            throws DomainException {
        this(title, priority, estimatedHours, deadlineDays, false, new CategoryTag("Work"), projectName);
    }

    public String getProjectName() { return projectName; }

    @Override
    public String getTaskType() { return "WORK"; }

    @Override
    public String toString() {
        return super.toString() + " | Проєкт: " + projectName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        WorkTask workTask = (WorkTask) obj;
        return Objects.equals(projectName, workTask.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), projectName);
    }
}