package JavaClass.Service;

import JavaClass.Data.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShopServiceImpl implements ShopService {

    @Override
    public void printShop(Shop shop) {
        System.out.println(shop.toString());
    }
    
    @Override
    public void addProduct(Shop shop, Products products, BigDecimal quantity) {
        BigDecimal currentQty = shop.getProduct_Quantity().getOrDefault(products, BigDecimal.ZERO);

        BigDecimal newQty = currentQty.add(quantity);
        shop.getProduct_Quantity().put(products, newQty);
        
        shop.getDeliveredProducts().put(products, quantity);
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
        System.out.println("Надценки по категории продукти:");

        shop.getMarkupPercent().forEach((type, percent) -> {
            System.out.printf("Категория: %-10s | Надценка: %.2f%%%n", type, percent);
        });
    }

    @Override
    public void addDiscountPercent(Shop shop, ProductType product, double discountPercent) {
        shop.getDiscountPercent().putIfAbsent(product,discountPercent);
    }

    @Override
    public void printDiscountPercent(Shop shop) {
        System.out.println("Отстъпка по категории продукти:");

        shop.getDiscountPercent().forEach((type, percent) -> {
            System.out.printf("Категория: %-10s | Отстъпка: %.2f%%%n", type, percent);
        });
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

    @Override
    public BigDecimal calculateTotalSalaries(Shop shop) {
        BigDecimal total = BigDecimal.ZERO;
        for (Cashier cashier : shop.getCashiers()) {
            total = total.add(cashier.getSalary());
        }
        return total;
    }

    @Override
    public BigDecimal calculateTotalDeliveryCosts(Shop shop) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Products, BigDecimal> entry : shop.getDeliveredProducts().entrySet()) {
            Products product = entry.getKey();
            BigDecimal quantity = entry.getValue();
            total = total.add(product.getDeliveryPrice().multiply(quantity));
        }
        return total;
    }
    
    @Override
    public BigDecimal calculateTotalRevenue(Shop shop) {
        BigDecimal total = BigDecimal.ZERO;
        for (Cashier cashier : shop.getCashiers()) {
            for (Receipt receipt : cashier.getReceipts()) {
                total = total.add(receipt.getTotalPrice());
            }
        }
        return total;
    }

    @Override
    public BigDecimal calculateProfit(Shop shop) {
        BigDecimal totalRevenue = calculateTotalRevenue(shop);
        BigDecimal totalDeliveryCosts = calculateTotalDeliveryCosts(shop);
        BigDecimal totalSalaries = calculateTotalSalaries(shop);

        BigDecimal profit = totalRevenue.subtract(totalDeliveryCosts.add(totalSalaries));
        
        return profit;
    }
    
    @Override
    public BigDecimal calculateMonthlyProfitFromSerializedReceipts(Set<Cashier> cashiers, YearMonth month) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalDeliveryCosts = BigDecimal.ZERO;

        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.startsWith("receipt_") && name.endsWith(".ser"));

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Receipt receipt = (Receipt) ois.readObject();
                    LocalDate date = receipt.getDateOfPurchase();
                    if (YearMonth.from(date).equals(month)) {
                        totalRevenue = totalRevenue.add(receipt.getTotalPrice());

                        for (Map.Entry<Products, BigDecimal> entry : receipt.getProductsSold().entrySet()) {
                            Products product = entry.getKey();
                            BigDecimal quantity = entry.getValue();

                            if (product.getDeliveryPrice() != null) {
                                totalDeliveryCosts = totalDeliveryCosts.add(product.getDeliveryPrice().multiply(quantity));
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Грешка при четене на бележка: " + file.getName() + " -> " + e.getMessage());
                }
            }
        }

        // Заплатите – вземаме веднъж за всички касиери
        BigDecimal totalSalaries = BigDecimal.ZERO;
        for (Cashier cashier : cashiers) {
            totalSalaries = totalSalaries.add(cashier.getSalary());
        }
        
        BigDecimal profit = totalRevenue.subtract(totalDeliveryCosts.add(totalSalaries));
        
        System.out.println("Приходи за месец " + month + ": " + totalRevenue);
        System.out.println("Разходи за доставки: " + totalDeliveryCosts);
        System.out.println("Разходи за заплати: " + totalSalaries);
        System.out.println("Печалба: " + profit);

        return profit;
    }

}
