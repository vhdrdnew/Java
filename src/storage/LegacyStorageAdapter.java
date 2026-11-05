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
            if (record.isBlank()) {
                continue;
            }
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
        if (parts.length < 8) {
            throw new IllegalArgumentException("Очікувалось як мінімум 8 полів у CSV (з урахуванням ID)");
        }

        long id = Long.parseLong(parts[0]);
        String type = parts[1];
        String title = parts[2];
        String priority = parts[3];
        double hours = Double.parseDouble(parts[4]);
        int deadline = Integer.parseInt(parts[5]);
        boolean completed = Boolean.parseBoolean(parts[6]);
        CategoryTag tag = new CategoryTag(parts[7]);

        return switch (type) {
            case "TECH_WORK" -> new TechWorkTask(
                    id, title, priority, hours, deadline, completed, tag,
                    parts.length > 8 ? parts[8] : "General",
                    parts.length > 9 ? parts[9] : "Java");
            case "WORK" -> new WorkTask(
                    id, title, priority, hours, deadline, completed, tag,
                    parts.length > 8 ? parts[8] : "General");
            case "PERSONAL" -> new PersonalTask(
                    id, title, priority, hours, deadline, completed, tag,
                    parts.length > 8 ? parts[8] : "Home");
            default -> throw new IllegalArgumentException("Невідомий тип завдання: " + type);
        };
    }
}