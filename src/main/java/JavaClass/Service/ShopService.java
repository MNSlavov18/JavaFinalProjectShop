package JavaClass.Service;

import JavaClass.Data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ShopService {
    public void printShop(Shop shop);
    
    void addProduct(Shop shop, Products products, BigDecimal quantity);

    void printProduct(Shop shop);

    void addMarkupPercent(Shop shop, ProductType product, double markupPercent);

    void printMarkupPercent(Shop shop);

    void addDiscountPercent(Shop shop, ProductType product, double discountPercent);

    void printDiscountPercent(Shop shop);

    public BigDecimal getFinalProductPrice(Shop shop, Products product, LocalDate today);

    Double printProductPricing(Shop shop, Products product, LocalDate today);

    boolean addCashier(Shop shop, Cashier cashier);

    void printCashier(Shop shop);

    public boolean isExpired(Products product, LocalDate today);

    public BigDecimal calculateTotalSalaries(Shop shop);

    public BigDecimal calculateTotalDeliveryCosts(Shop shop);

    public BigDecimal calculateTotalRevenue(Shop shop);

    public BigDecimal calculateProfit(Shop shop);

    public BigDecimal calculateMonthlyProfitFromSerializedReceipts(Set<Cashier> cashiers, YearMonth month);
}