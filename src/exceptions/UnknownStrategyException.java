package exceptions;

public class UnknownStrategyException extends DomainException {

    public UnknownStrategyException(String code) {
        super("Невідома стратегія '" + code + "'.", code);
    }
}
