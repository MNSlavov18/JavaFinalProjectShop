package JavaClass.Service;

import JavaClass.Data.Cashier;
import JavaClass.Data.ProductType;
import JavaClass.Data.Products;
import JavaClass.Data.Shop;
import JavaClass.Exceptions.NotEnoughProductException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CashierServiceImpl implements CashierService {
    public void releaseRegisterNumber(Cashier cashier) {
        Integer currentNumber = cashier.getCash_register_number();

        if (currentNumber != null && Cashier.getUsedRegisterNumbers().contains(currentNumber)) {
            Cashier.getUsedRegisterNumbers().remove(currentNumber);
            cashier.setCash_register_number(null);
        }
    }

    @Override
    public void addProductSold(Cashier cashier, Products product, BigDecimal quantity) {
        Map<Products, BigDecimal> sold = cashier.getProductsSold();
        BigDecimal current = sold.getOrDefault(product, BigDecimal.ZERO);
        sold.put(product, current.add(quantity));
    }

    @Override
    public void sellProduct(Shop shop, Cashier cashier, Products product, BigDecimal quantityToSell) {
        Map<Products, BigDecimal> stock = shop.getProduct_Quantity();
        BigDecimal available = stock.getOrDefault(product, BigDecimal.ZERO);

        if (available.compareTo(quantityToSell) < 0) {
            throw new NotEnoughProductException("Not enough stock for: " + product.getName() +
                    ". Available: " + available + ", Requested: " + quantityToSell);
        }
        
        stock.put(product, available.subtract(quantityToSell));
        
        addProductSold(cashier, product, quantityToSell);
    }
    public void printCashierSalesReport(Cashier cashier) {
        Map<Products, BigDecimal> sold = cashier.getProductsSold();

        if (sold == null || sold.isEmpty()) {
            System.out.println("Касиерът " + cashier.getName() + " не е продал нищо.");
            return;
        }

        System.out.println("Продажби на касиер " + cashier.getName() + ":");

        for (Map.Entry<Products, BigDecimal> entry : sold.entrySet()) {
            Products product = entry.getKey();
            BigDecimal quantity = entry.getValue();
            ProductType type = product.getType();

            System.out.printf("- %s (%s): %.2f бр.%n", product.getName(), type, quantity);
        }
    }
}
