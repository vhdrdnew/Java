package observer;
import model.Task;

public class DeadlineAlertObserver implements TaskObserver {

    private static final int URGENT_DAYS = 1;

    @Override
    public void onTaskAdded(Task task) {
        if (!task.isCompleted() && task.getDeadlineDays() <= URGENT_DAYS) {
            System.out.println("[УВАГА] Завдання '" + task.getTitle()
                    + "' треба зробити терміново! Дедлайн: " + task.getDeadlineDays() + " дн.");
        }
    }
}
