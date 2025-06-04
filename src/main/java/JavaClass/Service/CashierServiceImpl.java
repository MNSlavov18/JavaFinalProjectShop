package JavaClass.Service;

import JavaClass.Data.*;
import JavaClass.Exceptions.NotEnoughFundsException;
import JavaClass.Exceptions.NotEnoughProductException;
import JavaClass.Exceptions.PastTheExparationDataException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class CashierServiceImpl implements CashierService {
    public void releaseRegisterNumber(Cashier cashier) {
        Integer currentNumber = cashier.getCashRegisterNumber();

        if (currentNumber != null && Cashier.getUsedRegisterNumbers().contains(currentNumber)) {
            Cashier.getUsedRegisterNumbers().remove(currentNumber);
            cashier.setCashRegisterNumber(null);
        }
    }

    @Override
    public void addProductSold(Cashier cashier, Products product, BigDecimal quantity) {
        Map<Products, BigDecimal> sold = cashier.getProductsSold();
        BigDecimal current = sold.getOrDefault(product, BigDecimal.ZERO);
        sold.put(product, current.add(quantity));
    }

    @Override
    public void sellProduct(Shop shop, Cashier cashier, Products product, BigDecimal quantityToSell, BigDecimal cash,
                            LocalDate today, LocalTime time, ShopService shopService, ReceiptService receiptService) {

        if (shopService.isExpired(product, today)) {
            throw new PastTheExparationDataException(product);
        }

        Map<Products, BigDecimal> stock = shop.getProduct_Quantity();
        BigDecimal available = stock.getOrDefault(product, BigDecimal.ZERO);

        if (available.compareTo(quantityToSell) < 0) {
            throw new NotEnoughProductException(product, available, quantityToSell);
                    
        }

        Map<Products, BigDecimal> soldProducts = new HashMap<>();
        soldProducts.put(product, quantityToSell);

        BigDecimal totalPrice = receiptService.calculateTotalPrice(shop, soldProducts, today, shopService);

        if (cash.compareTo(totalPrice) < 0) {
            throw new NotEnoughFundsException(totalPrice, cash);
        }

        stock.put(product, available.subtract(quantityToSell));
        addProductSold(cashier, product, quantityToSell);

        Receipt receipt = new Receipt(cashier, soldProducts, today, time ,totalPrice);
        CashierService cashierService = new CashierServiceImpl();
        
        cashierService.addReceiptToCashier(cashier, receipt);
        receiptService.printReceiptToFile(receipt, shop, shopService, today);
    }
    
    @Override
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
    
    @Override
    public void addReceiptToCashier(Cashier cashier, Receipt receipt) {
        cashier.getReceipts().add(receipt);
    }
}
