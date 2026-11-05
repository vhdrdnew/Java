package exceptions;

public class EntityNotFoundException extends RuntimeException {

    private final long id;

    public EntityNotFoundException(long id) {
        super("Сутність із ID = " + id + " не знайдена в сховищі.");
        this.id = id;
    }

    public long getId() {
        return id;
    }
}