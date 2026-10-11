import java.util.Arrays;

public class TaskPlanner {

    private UrgencyCalculator strategy;

    public TaskPlanner(UrgencyCalculator strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(UrgencyCalculator strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегія не може бути null.");
        }
        this.strategy = strategy;
    }

    public UrgencyCalculator getStrategy() {
        return strategy;
    }

    public Task[] rank(Task[] tasks) {
        Task[] copy = Arrays.copyOf(tasks, tasks.length);
        Arrays.sort(copy, (a, b) -> Double.compare(strategy.calculate(b), strategy.calculate(a)));
        return copy;
    }

    public void printRanking(Task[] tasks) {
        if (tasks.length == 0) {
            System.out.println("Немає завдань для ранжування.");
            return;
        }
        System.out.println("Стратегія: " + strategy.getName());
        Task[] ranked = rank(tasks);
        for (int i = 0; i < ranked.length; i++) {
            System.out.println((i + 1) + ". " + strategy.describe(ranked[i]));
        }
    }
}
