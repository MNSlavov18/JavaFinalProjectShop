package JavaClass.Service;

import JavaClass.Data.ProductType;
import JavaClass.Data.Products;
import JavaClass.Data.Shop;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ShopServiceImpl implements ShopService {

    @Override
    public boolean addProduct(Shop shop, Products products) {
        return shop.getProducts().add(products);
    }

    @Override
    public void printProduct(Shop shop) {
        shop.getProducts()
                .stream()
                .forEach(System.out::println);
    }

    @Override
    public void addMarkupPercent(Shop shop, ProductType product, double markupPercent) {
        shop.getMarkupPercent().putIfAbsent(product,markupPercent);
    }

    @Override
    public void printMarkupPercent(Shop shop) {
        shop.getMarkupPercent()
                .entrySet()
                .stream()
                .forEach(System.out::println);
    }

    @Override
    public void addDiscountPercent(Shop shop, ProductType product, double discountPercent) {
        shop.getDiscountPercent().putIfAbsent(product,discountPercent);
    }

    @Override
    public void printDiscountPercent(Shop shop) {
        shop.getDiscountPercent()
                .entrySet()
                .stream()
                .forEach(System.out::println);
    }

    @Override
    public Double printProductPricing(Shop shop, Products product, LocalDate today) {
        if (today.isAfter(product.getExpiryDate())) {
            System.out.println(product.getName() + " → НЕ се продава (срок изтекъл)");
            return -1.0;
        }

        ProductType type = product.getType();
        double basePrice = product.getDeliveryPrice();

        double markup = shop.getMarkupPercent().getOrDefault(type, 0.0);
        double price = basePrice * (1.0 + markup / 100.0);

        long daysLeft = ChronoUnit.DAYS.between(today, product.getExpiryDate());
        if (daysLeft <= shop.getDaysLeftUntilExpiryToGiveDiscount()) {
            double discount = shop.getDiscountPercent().getOrDefault(type, 0.0);
            price *= (1.0 - discount / 100.0);
        }

        System.out.printf("%s → %.2f лв.%n", product.getName(), price);
        return price;
    }
    
}
