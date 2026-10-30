package model;

import exceptions.DomainException;
import exceptions.InvalidEstimateException;
import exceptions.InvalidPriorityException;
import java.util.Objects;

public abstract sealed class Task implements Comparable<Task>, Exportable permits WorkTask, PersonalTask {

    private final String title;
    private final String priority;
    private final double estimatedHours;
    private final int deadlineDays;
    private boolean isCompleted;
    private final CategoryTag tag;

    protected Task(String title, String priority, double estimatedHours, int deadlineDays, boolean isCompleted, CategoryTag tag)
            throws DomainException {

        if (title == null || title.isBlank()) {
            throw new DomainException("Назва завдання не може бути порожньою.", title);
        }
        if (title.contains(";")) {
            throw new DomainException("Назва не може містити символ ';'.", title);
        }
        if (priority == null || !(priority.equalsIgnoreCase("High")
                || priority.equalsIgnoreCase("Medium")
                || priority.equalsIgnoreCase("Low"))) {
            throw new InvalidPriorityException(priority);
        }
        if (estimatedHours <= 0) {
            throw new InvalidEstimateException("estimatedHours", estimatedHours, "має бути більше 0.");
        }
        if (deadlineDays < 0) {
            throw new InvalidEstimateException("deadlineDays", deadlineDays, "не може бути від'ємним.");
        }

        this.title = title;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.deadlineDays = deadlineDays;
        this.isCompleted = isCompleted;
        this.tag = tag;
    }

    public String getTitle() { return title; }
    public String getPriority() { return priority; }
    public double getEstimatedHours() { return estimatedHours; }
    public int getDeadlineDays() { return deadlineDays; }
    public boolean isCompleted() { return isCompleted; }
    public CategoryTag getTag() { return tag; }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public abstract String getTaskType(); // абстрактний метлд

    public final double getHoursPerDay() { //...іюажівадіщвлаів
        return estimatedHours / (deadlineDays == 0 ? 1 : deadlineDays);
    }

    @Override
    public int compareTo(Task other) {
        return Double.compare(this.estimatedHours, other.estimatedHours);
    }

    @Override
    public String toCsvLine() {
        String tagStr = (tag != null) ? tag.getTagName() : "NONE";
        return String.join(";", getTaskType(), title, priority,
                String.valueOf(estimatedHours), String.valueOf(deadlineDays),
                String.valueOf(isCompleted), tagStr);
    }

    @Override
    public String toString() {
        String tagInfo = (tag != null) ? " [" + tag.getTagName() + "]" : "";
        return String.format("[%s] [%-12s] %-20s%s | %-6s | %5.1f год | дедлайн: %2d дн.",
                isCompleted ? "x" : " ", getTaskType(), title, tagInfo, priority, estimatedHours, deadlineDays);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; 
        Task other = (Task) obj;
        return Double.compare(other.estimatedHours, estimatedHours) == 0 &&
                deadlineDays == other.deadlineDays &&
                isCompleted == other.isCompleted &&
                Objects.equals(title, other.title) &&
                Objects.equals(priority, other.priority) &&
                Objects.equals(tag, other.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, priority, estimatedHours, deadlineDays, isCompleted, tag);
    }
}