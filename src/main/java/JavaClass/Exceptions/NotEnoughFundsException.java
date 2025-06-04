package JavaClass.Exceptions;

import java.math.BigDecimal;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(BigDecimal required, BigDecimal given) {
        super("Недостатъчно пари. Нужни за успешна транзакция: " + required + ", Дадени: " + given);
    }
}
