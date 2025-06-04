package JavaClass.Exceptions;

public class DuplicateCashRegisterNumberException extends RuntimeException{
    public DuplicateCashRegisterNumberException(int cashRegisterNumber) {
        super("Каса " + cashRegisterNumber + " вече има касиер!");
    }
}
