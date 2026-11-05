package model;

import exceptions.DomainException;
import exceptions.InvalidEstimateException;
import exceptions.InvalidPriorityException;
import java.util.Objects;

public abstract sealed class Task extends BaseEntity implements Comparable<Task>, Exportable permits WorkTask, PersonalTask {

    private final String priority;
    private final double estimatedHours;
    private final int deadlineDays;
    private boolean isCompleted;
    private final CategoryTag tag;

    protected Task(long id, String title, String priority, double estimatedHours, int deadlineDays, boolean isCompleted, CategoryTag tag)
            throws DomainException {
        super(id, title);

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

        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.deadlineDays = deadlineDays;
        this.isCompleted = isCompleted;
        this.tag = tag;
    }

    public String getTitle() { return getName(); }
    public String getPriority() { return priority; }
    public double getEstimatedHours() { return estimatedHours; }
    public int getDeadlineDays() { return deadlineDays; }
    public boolean isCompleted() { return isCompleted; }
    public CategoryTag getTag() { return tag; }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public abstract String getTaskType();

    @Override
    public int compareTo(Task other) {
        int nameCompare = this.getName().compareToIgnoreCase(other.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }
        return Long.compare(this.getId(), other.getId());
    }

    @Override
    public String toCsvLine() {
        String tagStr = (tag != null) ? tag.getTagName() : "NONE";
        return String.join(";", String.valueOf(getId()), getTaskType(), getName(), priority,
                String.valueOf(estimatedHours), String.valueOf(deadlineDays),
                String.valueOf(isCompleted), tagStr);
    }

    @Override
    public String toString() {
        String tagInfo = (tag != null) ? " [" + tag.getTagName() + "]" : "";
        return String.format("ID: %2d | [%s] [%-12s] %-20s%s | %-6s | %5.1f год | дедлайн: %2d дн.",
                getId(), isCompleted ? "x" : " ", getTaskType(), getName(), tagInfo, priority, estimatedHours, deadlineDays);
    }
}