package JavaClass.Service;

import JavaClass.Data.Products;
import JavaClass.Data.Receipt;
import JavaClass.Data.Shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface ReceiptService {

    public void printReceiptToFile(Receipt receipt, Shop shop, ShopService shopService, LocalDate today);
    
    public Receipt deserializeReceipt(String receiptId);
    
    public void printDeserializedReceipt(String receiptId);
    
    public int getTotalReceipts();
    
    public BigDecimal getTotalRevenue();
    
    public BigDecimal calculateTotalPrice(Shop shop, Map<Products, BigDecimal> productsSold, LocalDate today, ShopService shopService);
}
