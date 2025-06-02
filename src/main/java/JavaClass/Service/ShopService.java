package JavaClass.Service;

import JavaClass.Data.Cashier;
import JavaClass.Data.ProductType;
import JavaClass.Data.Products;
import JavaClass.Data.Shop;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ShopService {
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
}
