import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Task[] tasks = createArray(scanner);

            for (int i = 0; i < tasks.length; i++) {
                System.out.println("\n--- Завдання #" + (i + 1) + " ---");
                tasks[i] = readTaskWithRetry(scanner);
            }

            System.out.println("\n--- Усі введені завдання ---");
            printArray(tasks);

            int highCount = 0;
            for (Task t : tasks) {
                if (t.getPriority().equalsIgnoreCase("High")) {
                    highCount++;
                }
            }
            System.out.println("\nКількість завдань з пріоритетом High: " + highCount);

            printAverageDeadline(tasks);

            bubbleSortByHours(tasks);
            System.out.println("\n--- Масив після сортування (за оцінкою часу, зростання) ---");
            printArray(tasks);

            showTaskByNumber(scanner, tasks);

            System.out.println("\n--- Пошук завдання за повним збігом (equals) ---");
            System.out.println("Введіть дані завдання-зразка для пошуку:");
            Task sample = readTaskWithRetry(scanner);
            int index = linearSearch(tasks, sample);
            if (index != -1) {
                System.out.println("Знайдено збіг на позиції " + index + ": " + tasks[index]);
            } else {
                System.out.println("Такого завдання в масиві немає.");
            }

            demonstrateHierarchy();
        } finally {
            scanner.close();
            System.out.println("\nПрограму завершено. Scanner закрито.");
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: потрібно ввести ціле число (наприклад, 3).");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Помилка: потрібно ввести число (наприклад, 2.5).");
            }
        }
    }

    private static Task[] createArray(Scanner scanner) {
        while (true) {
            int n = readInt(scanner, "Скільки завдань ви хочете ввести? ");
            try {
                return new Task[n];
            } catch (NegativeArraySizeException e) {
                System.out.println("Помилка: кількість завдань не може бути від'ємною (" + n + ").");
            }
        }
    }

    private static void printAverageDeadline(Task[] tasks) {
        int totalDays = 0;
        for (Task t : tasks) {
            totalDays += t.getDeadlineDays();
        }
        try {
            int average = totalDays / tasks.length;
            System.out.println("Середній дедлайн: " + average + " днів");
        } catch (ArithmeticException e) {
            System.out.println("Не можна порахувати середній дедлайн: завдань немає.");
        }
    }

    private static void showTaskByNumber(Scanner scanner, Task[] tasks) {
        int number = readInt(scanner, "\nВведіть номер завдання для перегляду (1.." + tasks.length + "): ");
        try {
            System.out.println(tasks[number - 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Помилка: завдання №" + number + " не існує. Доступні номери: 1.." + tasks.length);
        }
    }

    private static Task readTask(Scanner scanner) throws DomainException {
        System.out.print("Назва: ");
        String title = scanner.nextLine().trim();

        System.out.print("Пріоритет (High/Medium/Low): ");
        String priority = scanner.nextLine().trim();

        double hours = readDouble(scanner, "Оцінка часу (год, наприклад 2.5): ");
        int deadline = readInt(scanner, "Дедлайн (через скільки днів, ціле число): ");

        System.out.print("Виконано? (true/false): ");
        boolean done = Boolean.parseBoolean(scanner.nextLine().trim());

        try {
            return new Task(title, priority, hours, deadline, done);
        } catch (DomainException e) {
            System.out.println("[LOG] Завдання не створено: " + e.getMessage());
            throw e;
        }
    }

    private static Task readTaskWithRetry(Scanner scanner) {
        while (true) {
            try {
                return readTask(scanner);
            } catch (InvalidPriorityException e) {
                System.out.println("Помилка пріоритету. Ви ввели: '" + e.getInvalidValue()
                        + "'. Спробуйте High, Medium або Low.\n");
            } catch (InvalidEstimateException e) {
                System.out.println("Помилка в полі '" + e.getFieldName() + "' (значення "
                        + e.getInvalidValue() + "). Введіть завдання ще раз.\n");
            } catch (DomainException e) { 
                System.out.println("Помилка даних: " + e.getMessage() + " Введіть завдання ще раз.\n");
            }
        }
    }

    private static void demonstrateHierarchy() {
        System.out.println("\n--- Демонстрація ієрархії винятків ---");
        tryCreate("Тест", "Urgent", 2.0, 1);   
        tryCreate("Тест", "High", -5.0, 1);    
    }

    private static void tryCreate(String title, String priority, double hours, int deadline) {
        try {
            new Task(title, priority, hours, deadline, false);
            System.out.println("Створено без помилок.");
        } catch (DomainException e) {
            System.out.println("catch(DomainException) спіймав " + e.getClass().getSimpleName()
                    + " -> " + e.getMessage() + " [значення: " + e.getInvalidValue() + "]");
        }
    }

    private static void printArray(Task[] tasks) {
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    private static void bubbleSortByHours(Task[] tasks) {
        int n = tasks.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (tasks[j].getEstimatedHours() > tasks[j + 1].getEstimatedHours()) {
                    Task temp = tasks[j];
                    tasks[j] = tasks[j + 1];
                    tasks[j + 1] = temp;
                }
            }
        }
    }

    private static int linearSearch(Task[] tasks, Task sample) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(sample)) {
                return i;
            }
        }
        return -1;
    }
}