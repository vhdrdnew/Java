package storage;
import exceptions.DomainException;
import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LegacyStorageAdapter implements TaskStorage {

    private final LegacyFileStore legacy;

    public LegacyStorageAdapter(LegacyFileStore legacy) {
        this.legacy = legacy;
    }

    @Override
    public void save(List<Task> tasks) throws IOException {
        legacy.clearAll();
        for (Task task : tasks) {
            legacy.putRecord(task.toCsvLine());
        }
    }

    @Override
    public List<Task> load() throws IOException {
        List<Task> result = new ArrayList<>();
        for (String record : legacy.fetchAllRecords()) {
            if (record.isBlank()) continue;
            try {
                result.add(parse(record));
            } catch (DomainException | IllegalArgumentException e) {
                System.out.println("[УВАГА] Пропущено пошкоджений запис: " + record);
            }
        }
        return result;
    }

    private Task parse(String record) throws DomainException {
        String[] parts = record.split(";", -1);
        if (parts.length < 7) {
            throw new IllegalArgumentException("Очікувалось як мінімум 7 полів");
        }
        String type = parts[0];
        String title = parts[1];
        String priority = parts[2];
        double hours = Double.parseDouble(parts[3]);
        int deadline = Integer.parseInt(parts[4]);
        boolean completed = Boolean.parseBoolean(parts[5]);
        CategoryTag tag = new CategoryTag(parts[6]);

        return switch (type) {
            case "TECH_WORK" -> new TechWorkTask(title, priority, hours, deadline, completed, tag, 
                    parts.length > 7 ? parts[7] : "General", 
                    parts.length > 8 ? parts[8] : "Java");
            case "WORK" -> new WorkTask(title, priority, hours, deadline, completed, tag, 
                    parts.length > 7 ? parts[7] : "General");
            case "PERSONAL" -> new PersonalTask(title, priority, hours, deadline, completed, tag, 
                    parts.length > 7 ? parts[7] : "Home");
            default -> throw new IllegalArgumentException("Невідомий тип завдання: " + type);
        };
    }
}