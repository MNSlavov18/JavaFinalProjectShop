package JavaClass.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Shop {
    private UUID  id;
    private String name;
    private Map<ProductType, Double> markupPercent;
    private Integer daysLeftUntilExpiryToGiveDiscount;
    private Map<ProductType, Double> discountPercent;
    private Set<Products> products;
    private Map<ProductType, Double> sellingPrice;

    public Shop(String name, Integer daysLeftUntilExpiryToGiveDiscount) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.markupPercent = new EnumMap<>(ProductType.class);
        this.daysLeftUntilExpiryToGiveDiscount = daysLeftUntilExpiryToGiveDiscount;
        this.discountPercent = new EnumMap<>(ProductType.class);
        this.products = new HashSet<>();
        this.sellingPrice = new EnumMap<>(ProductType.class);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<ProductType, Double> getMarkupPercent() {
        return markupPercent;
    }

    public Integer getDaysLeftUntilExpiryToGiveDiscount() {
        return daysLeftUntilExpiryToGiveDiscount;
    }

    public void setDaysLeftUntilExpiryToGiveDiscount(Integer daysLeftUntilExpiryToGiveDiscount) {
        this.daysLeftUntilExpiryToGiveDiscount = daysLeftUntilExpiryToGiveDiscount;
    }

    public Map<ProductType, Double> getDiscountPercent() {
        return discountPercent;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public Map<ProductType, Double> getSellingPrice() {
        return sellingPrice;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", goods=" + products +
                '}';
    }
}
