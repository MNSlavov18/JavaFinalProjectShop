package JavaClass.Exceptions;

public class NotEnoughProductException extends RuntimeException {
    public NotEnoughProductException(String message) {
        super(message);
    }
}
