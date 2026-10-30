package strategy;
import model.Task;

public abstract class AbstractUrgencyCalculator implements UrgencyCalculator {

    private final String name;

    protected AbstractUrgencyCalculator(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public final double calculate(Task task) {
        if (task.isCompleted()) {
            return 0.0;
        }
        return computeScore(task);
    }

    protected abstract double computeScore(Task task);
}
