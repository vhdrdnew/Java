package storage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LegacyFileStore {

    private final Path path;

    public LegacyFileStore(String fileName) {
        this.path = Paths.get(fileName);
    }

    public void putRecord(String record) throws IOException {
        Files.writeString(path, record + System.lineSeparator(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public String[] fetchAllRecords() throws IOException {
        if (!Files.exists(path)) {
            return new String[0];
        }
        return Files.readAllLines(path, StandardCharsets.UTF_8).toArray(new String[0]);
    }

    public void clearAll() throws IOException {
        Files.deleteIfExists(path);
    }
}
