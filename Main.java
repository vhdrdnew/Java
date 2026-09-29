import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Скільки завдань ви хочете ввести? ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        Task[] tasks = new Task[n]; 

        for (int i = 0; i < n; i++) {
            System.out.println("\n Завдання #" + (i + 1) + " ");
            System.out.print("Назва: ");
            String title = scanner.nextLine().trim();

            System.out.print("Пріоритет (High/Medium/Low): ");
            String priority = scanner.nextLine().trim();

            System.out.print("Оцінка часу (год, наприклад 2.5): ");
            double hours = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Дедлайн (через скільки днів, ціле число): ");
            int deadline = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Виконано? (true/false): ");
            boolean done = Boolean.parseBoolean(scanner.nextLine().trim());

            tasks[i] = new Task(title, priority, hours, deadline, done);
        }

        System.out.println("\n Усі введені завдання (for-each) ");
        for (Task t : tasks) {
            System.out.println(t);
        }

        int highCount = 0;
        for (Task t : tasks) {
            if (t.getPriority().equalsIgnoreCase("High")) {
                highCount++;
            }
        }
        System.out.println("\nКількість завдань з пріоритетом High: " + highCount);

        System.out.println("\n Масив до сортування ");
        printArray(tasks);

        bubbleSortByHours(tasks);

        System.out.println("\n Масив після сортування (за оцінкою часу, зростання) ");
        printArray(tasks);

        System.out.println("\n Пошук завдання за повним збігом (equals) ");
        System.out.println("Введіть дані завдання-зразка для пошуку:");

        System.out.print("Назва: ");
        String sTitle = scanner.nextLine().trim();
        System.out.print("Пріоритет: ");
        String sPriority = scanner.nextLine().trim();
        System.out.print("Оцінка часу: ");
        double sHours = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Дедлайн: ");
        int sDeadline = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Виконано? (true/false): ");
        boolean sDone = Boolean.parseBoolean(scanner.nextLine().trim());

        Task sample = new Task(sTitle, sPriority, sHours, sDeadline, sDone);

        int index = linearSearch(tasks, sample);
        if (index != -1) {
            System.out.println("Знайдено збіг " + index + ": " + tasks[index]);
        } else {
            System.out.println("Такого завдання в масиві немає.");
        }

        scanner.close();
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
