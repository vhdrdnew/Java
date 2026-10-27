package observer;
import model.Task;

public class LogObserver implements TaskObserver {

    @Override
    public void onTaskAdded(Task task) {
        System.out.println("[LOG] Додано завдання: " + task.getTitle());
    }

    @Override
    public void onTaskCompleted(Task task) {
        System.out.println("[LOG] Виконано завдання: " + task.getTitle());
    }

    @Override
    public void onTaskRemoved(Task task) {
        System.out.println("[LOG] Видалено завдання: " + task.getTitle());
    }
}
