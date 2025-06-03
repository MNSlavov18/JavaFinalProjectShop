package JavaClass.Service;

import JavaClass.Data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceImplTest {

    private ReceiptServiceImpl receiptService;
    private ShopServiceImpl shopService;
    private Shop shop;
    private Cashier cashier;
    private Products product;
    private Map<Products, BigDecimal> productsSold;

    @BeforeEach
    public void setUp() {
        receiptService = new ReceiptServiceImpl();
        shopService = new ShopServiceImpl();
        shop = new Shop("Lidl", 7);
        cashier = new Cashier("Mario", new BigDecimal(2000), 1);

        product = new Products("Eggs", new BigDecimal(0.5), ProductType.FOOD, LocalDate.of(2025, 6, 3));
        shopService.addProduct(shop, product, BigDecimal.TEN);

        productsSold = new HashMap<>();
        productsSold.put(product, BigDecimal.ONE);
    }

    @Test
    public void testPrintReceiptToFile_ShouldNotThrow() {
        Receipt receipt = new Receipt(cashier, productsSold, LocalDate.now(), LocalTime.now(),BigDecimal.valueOf(5));
        assertDoesNotThrow(() -> receiptService.printReceiptToFile(receipt, shop, shopService, LocalDate.now()));
    }
    
    @Test
    public void testGetTotalReceipts_ShouldReturnCorrectNumber() {
        Receipt receipt1 = new Receipt(cashier, productsSold, LocalDate.now(),LocalTime.now() ,BigDecimal.valueOf(5));
        Receipt receipt2 = new Receipt(cashier, productsSold, LocalDate.now(),LocalTime.now() ,BigDecimal.valueOf(10));

        receiptService.printReceiptToFile(receipt1, shop, shopService, LocalDate.now());
        receiptService.printReceiptToFile(receipt2, shop, shopService, LocalDate.now());

        int totalReceipts = receiptService.getTotalReceipts();
        assertTrue(totalReceipts >= 2); // ако има стари може да са повече за това е >=
    }

    @Test
    public void testCalculateTotalPrice_ShouldReturnCorrectTotal() {
        BigDecimal total = receiptService.calculateTotalPrice(shop, productsSold, LocalDate.now(), shopService);
        assertNotNull(total);
        assertTrue(total.compareTo(BigDecimal.ZERO) >= 0);
    }
}