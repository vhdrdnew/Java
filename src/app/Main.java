package app;
import exceptions.DomainException;
import exceptions.InvalidEstimateException;
import exceptions.InvalidPriorityException;
import exceptions.UnknownStrategyException;
import model.Task;
import observer.AutoSaveObserver;
import observer.DeadlineAlertObserver;
import observer.LogObserver;
import service.TaskManager;
import storage.LegacyFileStore;
import storage.LegacyStorageAdapter;
import storage.TaskStorage;
import strategy.TaskPlanner;
import strategy.UrgencyCalculatorFactory;
import java.io.IOException;
import java.util.List;


public class Main {

    private static final String DATA_FILE = "tasks.csv";

    private final ConsoleInput in = new ConsoleInput();
    private final TaskManager manager = new TaskManager();
    private final TaskPlanner planner = new TaskPlanner(UrgencyCalculatorFactory.createDefault());
    private final TaskStorage storage = new LegacyStorageAdapter(new LegacyFileStore(DATA_FILE));

    public static void main(String[] args) {
        Main app = new Main();
        try {
            app.start();
        } finally {
            app.in.close();
            System.out.println("До побачення!");
        }
    }

    private void start() {
        loadSavedTasks();
        manager.addObserver(new LogObserver());
        manager.addObserver(new DeadlineAlertObserver());
        manager.addObserver(new AutoSaveObserver(manager, storage));

        runMenu();
    }

    private void loadSavedTasks() {
        try {
            List<Task> saved = storage.load();
            manager.restore(saved);
            if (!saved.isEmpty()) {
                System.out.println("Завантажено завдань із файлу " + DATA_FILE + ": " + saved.size());
            }
        } catch (IOException e) {
            System.out.println("Не вдалося прочитати файл " + DATA_FILE + ": " + e.getMessage());
        }
    }

    private void runMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = in.readInt("Ваш вибір: ");
            switch (choice) {
                case 1:
                    showTasks();
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    completeTask();
                    break;
                case 4:
                    removeTask();
                    break;
                case 5:
                    planner.printRanking(manager.getTasks());
                    break;
                case 6:
                    changeStrategy();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Немає такого пункту меню.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==== Менеджер завдань ====");
        System.out.println("1 - Показати всі завдання");
        System.out.println("2 - Додати завдання");
        System.out.println("3 - Позначити завдання виконаним");
        System.out.println("4 - Видалити завдання");
        System.out.println("5 - Що робити першим? (рейтинг)");
        System.out.println("6 - Змінити стратегію оцінки (зараз: " + planner.getStrategy().getName() + ")");
        System.out.println("0 - Вийти");
    }

    private void showTasks() {
        if (manager.size() == 0) {
            System.out.println("Список порожній.");
            return;
        }
        List<Task> tasks = manager.getTasks();
        System.out.println();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println("Усього: " + manager.size() + ", виконано: " + manager.countCompleted());
    }

    private void addTask() {
        System.out.println("\n--- Нове завдання ---");
        while (true) {
            String title = in.readLine("Назва: ");
            String priority = in.readLine("Пріоритет (High/Medium/Low): ");
            double hours = in.readDouble("Оцінка часу (год, наприклад 2.5): ");
            int deadline = in.readInt("Дедлайн (через скільки днів): ");
            boolean done = in.readYesNo("Уже виконано? (y/n): ");

            try {
                manager.addTask(new Task(title, priority, hours, deadline, done));
                return;
            } catch (InvalidPriorityException e) {
                System.out.println("Помилка пріоритету: введено '" + e.getInvalidValue()
                        + "'. Допустимо: High, Medium, Low.");
            } catch (InvalidEstimateException e) {
                System.out.println("Помилка в полі '" + e.getFieldName()
                        + "' (значення " + e.getInvalidValue() + ").");
            } catch (DomainException e) {
                System.out.println("Помилка: " + e.getMessage());
            }

            if (!in.readYesNo("Спробувати ще раз? (y/n): ")) {
                return;
            }
        }
    }

    private void completeTask() {
        if (manager.size() == 0) {
            System.out.println("Список порожній.");
            return;
        }
        showTasks();
        int number = in.readInt("Номер завдання, яке виконано: ");
        try {
            manager.completeTask(number);
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void removeTask() {
        if (manager.size() == 0) {
            System.out.println("Список порожній.");
            return;
        }
        showTasks();
        int number = in.readInt("Номер завдання для видалення: ");
        try {
            manager.removeTask(number);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void changeStrategy() {
        System.out.println("Поточна стратегія: " + planner.getStrategy().getName());
        String codes = String.join(" / ", UrgencyCalculatorFactory.availableCodes());
        String code = in.readLine("Код нової стратегії (" + codes + "): ");
        try {
            planner.setStrategy(UrgencyCalculatorFactory.create(code));
            System.out.println("Стратегію змінено на: " + planner.getStrategy().getName());
        } catch (UnknownStrategyException e) {
            System.out.println("Помилка: " + e.getMessage() + " Доступні: " + codes);
        }
    }
}
