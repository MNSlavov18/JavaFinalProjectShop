package JavaClass.Service;

import JavaClass.Data.*;
import JavaClass.Exceptions.NotEnoughFundsException;
import JavaClass.Exceptions.NotEnoughProductException;
import JavaClass.Service.CashierServiceImpl;
import JavaClass.Service.ReceiptServiceImpl;
import JavaClass.Service.ShopServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CashierServiceImplTest {

    @Test
    public void testAddReceiptToCashier_ShouldAddReceipt() {
        CashierServiceImpl cashierService = new CashierServiceImpl();
        Cashier cashier = new Cashier("Ivan", BigDecimal.valueOf(1000),1);

        Map<Products, BigDecimal> soldProducts = new HashMap<>();
        Products eggs = new Products("Eggs", new BigDecimal(0.50)
                , ProductType.FOOD, LocalDate.of(2025,6,3));
        soldProducts.put(eggs, new BigDecimal(0.50));

        Receipt receipt = new Receipt(cashier, soldProducts, LocalDate.now(), LocalTime.now() ,BigDecimal.TEN);

        cashierService.addReceiptToCashier(cashier, receipt);

        assertEquals(1, cashier.getReceipts().size());
        assertEquals(receipt, cashier.getReceipts().get(0));
    }

    @Test
    public void testSellProduct_WithSufficientStock_ShouldReduceStock() {
        CashierServiceImpl cashierService = new CashierServiceImpl();
        ShopServiceImpl shopService = new ShopServiceImpl();
        ReceiptServiceImpl receiptService = new ReceiptServiceImpl();

        Shop shop = new Shop("Lidl",7);
        Cashier cashier1 = new Cashier("Mario", new BigDecimal(2000), 1);
        Products eggs = new Products("Eggs", new BigDecimal(0.50) , ProductType.FOOD, LocalDate.of(2025,6,3));
        shop.getCashiers().add(cashier1);
        shopService.addProduct(shop, eggs, BigDecimal.TEN);

        BigDecimal initialStock = shop.getProduct_Quantity().get(eggs);

        cashierService.sellProduct(shop, cashier1, eggs, BigDecimal.ONE, BigDecimal.valueOf(20), LocalDate.now(),LocalTime.now(), shopService, receiptService);

        BigDecimal finalStock = shop.getProduct_Quantity().get(eggs);
        assertEquals(initialStock.subtract(BigDecimal.ONE), finalStock);
    }

    @Test
    public void testSellProduct_WithUnSufficientStock_ShouldThrowException() {
        CashierServiceImpl cashierService = new CashierServiceImpl();
        ShopServiceImpl shopService = new ShopServiceImpl();
        ReceiptServiceImpl receiptService = new ReceiptServiceImpl();

        Shop shop = new Shop("Lidl", 7);
        Cashier cashier1 = new Cashier("Mario", new BigDecimal(2000), 1);
        Products eggs = new Products("Eggs", new BigDecimal(0.5), ProductType.FOOD, LocalDate.of(2025, 6, 3));
        shop.getCashiers().add(cashier1);
        shopService.addProduct(shop, eggs, new BigDecimal(1)); // само 1 брой

        BigDecimal initialStock = shop.getProduct_Quantity().get(eggs);

        // опит за продажба на 2 броя (повече от наличното)
        assertThrows(
                NotEnoughProductException.class,
                () -> cashierService.sellProduct(shop, cashier1, eggs, new BigDecimal(2), BigDecimal.valueOf(20), LocalDate.now(), LocalTime.now(),shopService, receiptService)
        );

        // наличността трябва да е непроменена
        BigDecimal finalStock = shop.getProduct_Quantity().get(eggs);
        assertEquals(initialStock, finalStock);
    }

    @Test
    public void testSellProduct_WithUnSufficientFunds_ShouldThrowException() {
        CashierServiceImpl cashierService = new CashierServiceImpl();
        ShopServiceImpl shopService = new ShopServiceImpl();
        ReceiptServiceImpl receiptService = new ReceiptServiceImpl();

        Shop shop = new Shop("Lidl", 7);
        Cashier cashier1 = new Cashier("Mario", new BigDecimal(2000), 1);
        Products eggs = new Products("Eggs", new BigDecimal(0.5), ProductType.FOOD, LocalDate.of(2025, 6, 3));
        shop.getCashiers().add(cashier1);
        shopService.addProduct(shop, eggs, new BigDecimal(10));

        BigDecimal initialStock = shop.getProduct_Quantity().get(eggs);

        // опит за продажба с недостатъчно пари (примерно: цена е 5, клиент дава само 1)
        assertThrows(
                NotEnoughFundsException.class,
                () -> cashierService.sellProduct(shop, cashier1, eggs,new BigDecimal(10), new BigDecimal(2), LocalDate.now(),LocalTime.now(), shopService, receiptService)
        );

        // наличността трябва да остане непроменена
        BigDecimal finalStock = shop.getProduct_Quantity().get(eggs);
        assertEquals(initialStock, finalStock);
    }

    @Test
    public void testPrintCashierSalesReport_ShouldNotThrowExceptions() {
        CashierServiceImpl cashierService = new CashierServiceImpl();
        ShopServiceImpl shopService = new ShopServiceImpl();
        ReceiptServiceImpl receiptService = new ReceiptServiceImpl();

        Shop shop = new Shop("Lidl", 7);
        Cashier cashier1 = new Cashier("Mario", new BigDecimal(2000), 1);
        Products eggs = new Products("Eggs", new BigDecimal(0.5), ProductType.FOOD, LocalDate.of(2025, 6, 3));

        shop.getCashiers().add(cashier1);
        shopService.addProduct(shop, eggs, BigDecimal.TEN);

        cashierService.sellProduct(shop, cashier1, eggs, BigDecimal.ONE, BigDecimal.valueOf(20), LocalDate.now(), LocalTime.now(),shopService, receiptService);

        assertDoesNotThrow(() -> cashierService.printCashierSalesReport(cashier1));
    }
}