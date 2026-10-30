package strategy;
import model.Task;
import java.util.ArrayList;
import java.util.List;

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

    public List<Task> rank(List<Task> tasks) {
        List<Task> copy = new ArrayList<>(tasks);
        copy.sort((a, b) -> Double.compare(strategy.calculate(b), strategy.calculate(a)));
        return copy;
    }

    public void printRanking(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Немає завдань для ранжування.");
            return;
        }
        System.out.println("Стратегія: " + strategy.getName());
        List<Task> ranked = rank(tasks);
        for (int i = 0; i < ranked.size(); i++) {
            System.out.println((i + 1) + ". " + strategy.describe(ranked.get(i)));
        }
    }
}
