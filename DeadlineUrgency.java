public class DeadlineUrgency extends AbstractUrgencyCalculator {

    public DeadlineUrgency() {
        super("За дедлайном");
    }

    @Override
    protected double computeScore(Task task) {
        return 10.0 / (task.getDeadlineDays() + 1);
    }
}
