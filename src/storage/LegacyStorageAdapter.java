package storage;
import exceptions.DomainException;
import model.Task;
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
        if (parts.length != 5) {
            throw new IllegalArgumentException("Очікувалось 5 полів, знайдено " + parts.length);
        }
        return new Task(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                Integer.parseInt(parts[3]),
                Boolean.parseBoolean(parts[4]));
    }
}
