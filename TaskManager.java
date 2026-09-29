import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Назва задачі: ");
        String title = scanner.nextLine();

        System.out.print("Пріоритет (1-високий, 2-середній, 3-низький): ");
        int priority = scanner.nextInt();

        System.out.print("Оцінка часу виконання (у годинах): ");
        double estimatedHours = scanner.nextDouble();

        System.out.print("Задача вже виконана? (true/false): ");
        boolean isDone = scanner.nextBoolean();

        System.out.print("Категорія задачі: ");
        scanner.nextLine();
        String category = scanner.nextLine();

        double hourlyRate = 350.0; 
        double estimatedCost = estimatedHours * hourlyRate;

        String priorityLabel;
        if (priority == 1) {
            priorityLabel = "Високий пріоритет";
        } else if (priority == 2) {
            priorityLabel = "Середній пріоритет";
        } else {
            priorityLabel = "Низький пріоритет";
        }

        System.out.printf("%n===Картка задачі===%n");
        System.out.printf("Назва: %s%n", title);
        System.out.printf("Категорія: %s%n", category);
        System.out.printf("Пріоритет: %s%n", priorityLabel);
        System.out.printf("Оцінка часу: %.2f год%n", estimatedHours);
        System.out.printf("Орієнтовна вартість: %.2f грн%n", estimatedCost);
        if (isDone) {
            System.out.println("Статус: Виконано");
        } else {
            System.out.println("Статус: В процесі");
        }
    }
}