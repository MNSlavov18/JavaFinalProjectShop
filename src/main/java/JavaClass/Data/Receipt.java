package JavaClass.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Receipt {
    public UUID receiptId;
    public Cashier cashier;
    public LocalDate dateOfPurchase;
    private final Map<Products, BigDecimal> productsSold;
    private BigDecimal totalPrice;

    public Receipt(Cashier cashier, Map<Products, BigDecimal> productsSold,LocalDate dateOfPurchase, BigDecimal totalPrice) {
        this.receiptId = UUID.randomUUID();
        this.cashier = cashier;
        this.dateOfPurchase = dateOfPurchase;
        this.productsSold = productsSold;
        this.totalPrice = totalPrice;
        
    }

    public UUID getReceiptId() {
        return receiptId;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public Map<Products, BigDecimal> getProductsSold() {
        return productsSold;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
