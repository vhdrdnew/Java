package storage;
import model.Task;
import java.io.IOException;
import java.util.List;

public interface TaskStorage {

    void save(List<Task> tasks) throws IOException;

    List<Task> load() throws IOException;
}
