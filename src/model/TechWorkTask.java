package model;

import exceptions.DomainException;

public final class TechWorkTask extends WorkTask {

    private final String techStack;

    public TechWorkTask(String title, String priority, double estimatedHours, int deadlineDays, boolean isCompleted, CategoryTag tag, String projectName, String techStack)
            throws DomainException {
        super(title, priority, estimatedHours, deadlineDays, isCompleted, tag, projectName);
        this.techStack = (techStack == null || techStack.isBlank()) ? "Java" : techStack;
    }

    public String getTechStack() { return techStack; }

    @Override
    public String getTaskType() { return "TECH_WORK"; }

    @Override
    public String toString() {
        return super.toString() + " [Стек: " + techStack + "]";
    }
}