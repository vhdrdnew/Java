package observer;
import model.Task;
import service.TaskManager;
import storage.TaskStorage;
import java.io.IOException;

public class AutoSaveObserver implements TaskObserver {

    private final TaskManager manager;
    private final TaskStorage storage;

    public AutoSaveObserver(TaskManager manager, TaskStorage storage) {
        this.manager = manager;
        this.storage = storage;
    }

    @Override
    public void onTaskAdded(Task task) {
        save();
    }

    @Override
    public void onTaskCompleted(Task task) {
        save();
    }

    @Override
    public void onTaskRemoved(Task task) {
        save();
    }

    private void save() {
        try {
            storage.save(manager.getTasks());
        } catch (IOException e) {
            System.out.println("[Помилка] Не вдалося зберегти завдання: " + e.getMessage());
        }
    }
}
