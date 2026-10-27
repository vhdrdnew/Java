package service;
import model.Task;
import observer.TaskObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();
    private final List<TaskObserver> observers = new ArrayList<>();

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    public void restore(List<Task> loaded) {
        tasks.clear();
        tasks.addAll(loaded);
    }

    public void addTask(Task task) {
        tasks.add(task);
        for (TaskObserver observer : observers) {
            observer.onTaskAdded(task);
        }
    }

    public Task completeTask(int number) {
        Task task = getTask(number);
        if (task.isCompleted()) {
            throw new IllegalStateException("Завдання №" + number + " уже виконане.");
        }
        task.markCompleted();
        for (TaskObserver observer : observers) {
            observer.onTaskCompleted(task);
        }
        return task;
    }

    public Task removeTask(int number) {
        Task task = getTask(number);
        tasks.remove(number - 1);
        for (TaskObserver observer : observers) {
            observer.onTaskRemoved(task);
        }
        return task;
    }

    public Task getTask(int number) {
        if (number < 1 || number > tasks.size()) {
            throw new IndexOutOfBoundsException(
                    "Завдання №" + number + " не існує. Доступні номери: 1.." + tasks.size());
        }
        return tasks.get(number - 1);
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public int countCompleted() {
        int count = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
}
