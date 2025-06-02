package JavaClass.Service;

import JavaClass.Data.Cashier;
import JavaClass.Data.Products;
import JavaClass.Data.Receipt;
import JavaClass.Data.Shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface CashierService {
    public void releaseRegisterNumber(Cashier cashier);
    public void addProductSold(Cashier cashier, Products product, BigDecimal quantity);
    public void sellProduct(Shop shop, Cashier cashier, Products product, BigDecimal quantityToSell, BigDecimal cash,
                            LocalDate today, ShopService shopService, ReceiptService receiptService);
    public void printCashierSalesReport(Cashier cashier);
    
}
