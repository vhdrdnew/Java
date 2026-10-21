public interface UrgencyCalculator {

    double calculate(Task task);

    String getName();

    default String describe(Task task) {
        return String.format("[%s] %s -> %.2f", getName(), task.getTitle(), calculate(task));
    }
}
