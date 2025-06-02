package JavaClass.Service;

import JavaClass.Data.Cashier;
import JavaClass.Data.ProductType;
import JavaClass.Data.Products;
import JavaClass.Data.Shop;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ShopServiceImpl implements ShopService {

    @Override
    public void addProduct(Shop shop, Products products, BigDecimal quantity) {
        shop.getProduct_Quantity().put(products, quantity);
    }

    @Override
    public void printProduct(Shop shop) {
        shop.getProduct_Quantity()
                .entrySet()
                .stream()
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
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
    public BigDecimal getFinalProductPrice(Shop shop, Products product, LocalDate today) {
        if (isExpired(product, today)) {
            return BigDecimal.ZERO; // product cannot be sold
        }

        BigDecimal price = calculateBasePriceWithMarkup(shop, product);
        return applyDiscountIfNecessary(shop, product, price, today);
    }

    @Override
    public boolean isExpired(Products product, LocalDate today) {
        LocalDate expiryDate = product.getExpiryDate();
        if (expiryDate == null) {
            return false; // No expiry date means not expired
        }
        return today.isAfter(product.getExpiryDate());
    }

    private BigDecimal calculateBasePriceWithMarkup(Shop shop, Products product) {
        ProductType type = product.getType();
        BigDecimal basePrice = product.getDeliveryPrice();
        double markupPercent = shop.getMarkupPercent().getOrDefault(type, 0.0);
        BigDecimal markupMultiplier = BigDecimal.valueOf(1.0 + markupPercent / 100.0);
        return basePrice.multiply(markupMultiplier);
    }

    private BigDecimal applyDiscountIfNecessary(Shop shop, Products product, BigDecimal currentPrice, LocalDate today) {

        if (product.getExpiryDate() == null) {
            return currentPrice; 
        }
        
        long daysLeft = ChronoUnit.DAYS.between(today, product.getExpiryDate());
        if (daysLeft <= shop.getDaysLeftUntilExpiryToGiveDiscount()) {
            double discountPercent = shop.getDiscountPercent().getOrDefault(product.getType(), 0.0);
            BigDecimal discountMultiplier = BigDecimal.valueOf(1.0 - discountPercent / 100.0);
            return currentPrice.multiply(discountMultiplier);
        }
        
        return currentPrice;
    }
    
    @Override
    public Double printProductPricing(Shop shop, Products product, LocalDate today) {
        if (isExpired(product, today)) {
            System.out.println(product.getName() + " → НЕ се продава (срок изтекъл)");
            return -1.0;
        }

        BigDecimal price = calculateBasePriceWithMarkup(shop, product);
        price = applyDiscountIfNecessary(shop, product, price, today);

        System.out.printf("%s → %.2f лв.%n", product.getName(), price);
        return price.doubleValue();
    }

    @Override
    public boolean addCashier(Shop shop, Cashier cashier) {
        return shop.getCashiers().add(cashier);
    }

    @Override
    public void printCashier(Shop shop) {
        shop.getCashiers()
                .stream()
                .forEach(System.out::println);
    }


}
