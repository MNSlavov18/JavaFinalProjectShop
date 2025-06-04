package JavaClass.Exceptions;

import JavaClass.Data.Products;

import java.math.BigDecimal;

public class NotEnoughProductException extends RuntimeException {
    public NotEnoughProductException(Products product, BigDecimal available, BigDecimal requested) {
        super("Няма достатъчно стока: " + product.getName() +
                ". Останала: " + available + ", Нужна: " + requested);
    }
}
