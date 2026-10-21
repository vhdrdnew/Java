package strategy;
import model.Task;

public class WorkloadUrgency extends AbstractUrgencyCalculator {

    public WorkloadUrgency() {
        super("За навантаженням");
    }

    @Override
    protected double computeScore(Task task) {
        return task.getEstimatedHours() / (task.getDeadlineDays() + 1);
    }

    @Override
    public String describe(Task task) {
        return String.format("[%s] %s -> потрібно %.2f год/день", getName(), task.getTitle(), calculate(task));
    }
}