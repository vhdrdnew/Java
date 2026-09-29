public class InvalidPriorityException extends DomainException {

    public InvalidPriorityException(String value) {
        super("Недопустимий пріоритет '" + value + "'. Дозволено: High, Medium, Low.", value);
    }
}
