public class PriorityUrgency extends AbstractUrgencyCalculator {

    public PriorityUrgency() {
        super("За пріоритетом");
    }

    @Override
    protected double computeScore(Task task) {
        switch (task.getPriority().toLowerCase()) {
            case "high":
                return 10.0;
            case "medium":
                return 5.0;
            case "low":
                return 1.0;
            default:
                return 0.0;
        }
    }
}
