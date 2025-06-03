package JavaClass.Service;

import JavaClass.Data.Products;
import JavaClass.Data.ProductType;
import JavaClass.Data.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopServiceImplTest {

    private ShopServiceImpl shopService;
    private Shop shop;
    private Products product;

    @BeforeEach public void setUp() {
        shopService = new ShopServiceImpl();
        shop = new Shop("Lidl", 7);
        product = new Products("Eggs", new BigDecimal(0.5), ProductType.FOOD, LocalDate.of(2025, 6, 3));
        shopService.addProduct(shop, product, BigDecimal.TEN);
    }

    @Test
    public void testAddProduct_ShouldIncreaseQuantity() {
        BigDecimal initialQty = shop.getProduct_Quantity().get(product);
        shopService.addProduct(shop, product, BigDecimal.TEN);
        BigDecimal finalQty = shop.getProduct_Quantity().get(product);
        assertEquals(initialQty.add(BigDecimal.TEN), finalQty);
    }

    @Test
    public void testCalculateTotalDeliveryCosts() {
        BigDecimal expected = product.getDeliveryPrice().multiply(BigDecimal.TEN);
        BigDecimal actual = shopService.calculateTotalDeliveryCosts(shop);
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateTotalSalaries_WhenNoCashiers_ShouldReturnZero() {
        BigDecimal actual = shopService.calculateTotalSalaries(shop);
        assertEquals(BigDecimal.ZERO, actual);
    }

    @Test
    public void testIsExpired_ShouldReturnFalseForFutureDate() {
        assertFalse(shopService.isExpired(product, LocalDate.now()));
    }

    @Test
    public void testIsExpired_ShouldReturnTrueForPastDate() {
        Products expiredProduct = new Products("Old Milk", new BigDecimal(1), ProductType.FOOD, LocalDate.of(2020, 1, 1));
        assertTrue(shopService.isExpired(expiredProduct, LocalDate.now()));
    }

    @Test
    public void testAddDiscountPercent_ShouldApplyDiscount() {
        shopService.addDiscountPercent(shop, ProductType.FOOD, 10);

        Double actualDiscount = shop.getDiscountPercent().get(ProductType.FOOD);
        assertEquals(10, actualDiscount);
    }

    @Test
    public void testPrintDiscountPercent_ShouldNotThrow() {
        assertDoesNotThrow(() -> shopService.printDiscountPercent(shop));
    }

    @Test
    public void testPrintProductPricing_ShouldReturnPrice() {
        Double price = shopService.printProductPricing(shop, product, LocalDate.now());
        assertNotNull(price);
    }

    @Test
    public void testCalculateTotalRevenue_ShouldReturnZeroInitially() {
        BigDecimal actual = shopService.calculateTotalRevenue(shop);
        assertEquals(BigDecimal.ZERO, actual);
    }

    @Test
    public void testCalculateProfit_WithNoSales_ShouldReturnNegativeCosts() {
        BigDecimal actual = shopService.calculateProfit(shop);

        // ако имаш само доставки (5), а без продажби - ще е -5
        assertEquals(BigDecimal.valueOf(-5.0), actual);
    }

    @Test
    public void testCalculateMonthlyProfitFromSerializedReceipts_ShouldNotThrow() {
        BigDecimal profit = shopService.calculateMonthlyProfitFromSerializedReceipts(shop.getCashiers(), YearMonth.now());
        assertNotNull(profit);
    }
    }
