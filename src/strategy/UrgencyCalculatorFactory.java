package strategy;
import exceptions.UnknownStrategyException;
import java.util.List;

public final class UrgencyCalculatorFactory {

    private UrgencyCalculatorFactory() {
    }

    public static UrgencyCalculator create(String code) throws UnknownStrategyException {
        String normalized = (code == null) ? "" : code.trim().toLowerCase();
        switch (normalized) {
            case "deadline":
                return new DeadlineUrgency();
            case "priority":
                return new PriorityUrgency();
            case "workload":
                return new WorkloadUrgency();
            default:
                throw new UnknownStrategyException(code);
        }
    }

    public static UrgencyCalculator createDefault() {
        return new DeadlineUrgency();
    }

    public static List<String> availableCodes() {
        return List.of("deadline", "priority", "workload");
    }
}
