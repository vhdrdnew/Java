public class InvalidEstimateException extends DomainException {
    private final String fieldName;

    public InvalidEstimateException(String fieldName, double value, String rule) {
        super("Поле '" + fieldName + " має неправильне значення " + value + ": " + rule, value);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
