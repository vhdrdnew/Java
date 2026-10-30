package observer;
import model.Task;

public interface TaskObserver {

    default void onTaskAdded(Task task) {
    }

    default void onTaskCompleted(Task task) {
    }

    default void onTaskRemoved(Task task) {
    }
}
