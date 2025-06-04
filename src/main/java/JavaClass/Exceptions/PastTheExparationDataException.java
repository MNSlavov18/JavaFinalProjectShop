package JavaClass.Exceptions;

import JavaClass.Data.Products;

public class PastTheExparationDataException extends RuntimeException {
    public PastTheExparationDataException(Products product) {
        super("Не се продава: " + product.getName() + ". Причина: Изтекъл срок");
    }
}
