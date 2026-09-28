public class Task {

    private String title;          
    private String priority;      
    private double estimatedHours; 
    private int deadlineDays;      
    private boolean isCompleted;   

    public Task(String title, String priority, double estimatedHours, int deadlineDays, boolean isCompleted) {
        this.title = title;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.deadlineDays = deadlineDays;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public int getDeadlineDays() {
        return deadlineDays;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return String.format(
                "Завдання: %-20s | Пріоритет: %-6s | Оцінка часу: %.1f год | Дедлайн: %d дн. | Виконано: %s",
                title, priority, estimatedHours, deadlineDays, isCompleted ? "так" : "ні"
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;

        return Double.compare(estimatedHours, other.estimatedHours) == 0
                && deadlineDays == other.deadlineDays
                && isCompleted == other.isCompleted
                && title.equals(other.title)
                && priority.equals(other.priority);
    }
}
