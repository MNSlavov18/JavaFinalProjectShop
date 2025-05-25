package JavaClass.Service;

import JavaClass.Data.Cashier;
import JavaClass.Data.Products;
import JavaClass.Data.Shop;

import java.math.BigDecimal;
import java.util.Map;

public interface CashierService {
    public void releaseRegisterNumber(Cashier cashier);
    public void addProductSold(Cashier cashier, Products product, BigDecimal quantity);
    public void sellProduct(Shop shop, Cashier cashier, Products product, BigDecimal quantityToSell);
    public void printCashierSalesReport(Cashier cashier);
    
}
