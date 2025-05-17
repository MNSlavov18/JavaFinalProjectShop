package JavaClass.Service;

import JavaClass.Data.ProductType;
import JavaClass.Data.Products;
import JavaClass.Data.Shop;

import java.time.LocalDate;
import java.util.Set;

public interface ShopService {
    boolean addProduct(Shop shop, Products products);
    void printProduct(Shop shop);
    void addMarkupPercent(Shop shop, ProductType product, double markupPercent);
    void printMarkupPercent(Shop shop);
    void addDiscountPercent(Shop shop, ProductType product, double discountPercent);
    void printDiscountPercent(Shop shop);
    Double printProductPricing(Shop shop, Products product, LocalDate today);
}
