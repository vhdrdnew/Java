package app;

import exceptions.EntityNotFoundException;
import model.*;
import service.TaskService;
import service.TaskServiceImpl;
import util.EntityPrinter;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Демонстрація ЛР №7-9: CRUDService, Generics та Сортування ===\n");

        TaskService taskService = new TaskServiceImpl();

        try {
            CategoryTag devTag = new CategoryTag("Dev");
            CategoryTag workTag = new CategoryTag("Work");

            Task t1 = new WorkTask(1L, "Review Code", "High", 2.0, 3, false, devTag, "LibraryManager");
            Task t2 = new WorkTask(2L, "Write Tests", "Medium", 4.0, 5, false, devTag, "LibraryManager");
            Task t3 = new WorkTask(3L, "Review Code", "Low", 1.0, 1, false, workTag, "Core");
            Task t4 = new PersonalTask(4L, "Buy Groceries", "Medium", 1.5, 2, false, new CategoryTag("Life"), "Store");

            taskService.save(t1);
            taskService.save(t2);
            taskService.save(t3);
            taskService.save(t4);

            System.out.println("--- 1. Початковий список у сховищі (LinkedHashMap зберігає порядок вставки) ---");
            EntityPrinter.printAll(taskService.findAll());

            System.out.println("\n--- 2. Оновлення сутності з ID = 1 (зміна статусу) ---");
            Task updatedT1 = new WorkTask(1L, "Review Code", "High", 2.0, 3, true, devTag, "LibraryManager");
            taskService.save(updatedT1);
            System.out.println("Кількість записів після save(updatedT1): " + taskService.findAll().size() + " (не змінилася)");

            System.out.println("\n--- 3. Пошук за ID = 2 ---");
            taskService.findById(2L).ifPresent(System.out::println);

            System.out.println("\n--- 4. Сортування за іменем (findAllSortedByName) ---");
            List<Task> sortedByName = taskService.findAllSortedByName();
            EntityPrinter.printAll(sortedByName);

            System.out.println("\n--- 5. Вторинне сортування: Name -> DeadlineDays ---");
            List<Task> sortedByNameAndDeadline = taskService.findAllSortedByNameAndByDeadline();
            EntityPrinter.printAll(sortedByNameAndDeadline);

            System.out.println("\n--- 6. Перевірка незмінності початкового сховища ---");
            EntityPrinter.printAll(taskService.findAll());

            System.out.println("\n--- 7. Видалення записів ---");
            boolean deleted = taskService.delete(4L);
            System.out.println("Видалення ID = 4: " + (deleted ? "Успішно" : "Помилка"));

            System.out.println("Спроба видалення неіснуючого ID = 99:");
            try {
                taskService.delete(99L);
            } catch (EntityNotFoundException e) {
                System.out.println("[СПІЙМАНО ВИНОС] " + e.getMessage() + " (ID: " + e.getId() + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}