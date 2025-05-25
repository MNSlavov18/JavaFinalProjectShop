package JavaClass.Data;

import java.math.BigDecimal;
import java.util.*;

public class Shop {
    private UUID shop_id;
    private String name;
    private Map<ProductType, Double> markupPercent;
    private Integer daysLeftUntilExpiryToGiveDiscount;
    private Map<ProductType, Double> discountPercent;
    private Set<Cashier> cashiers;
    private Map<Products, BigDecimal> product_Quantity;
    private Map<ProductType, Double> sellingPrice;

    public Shop(String name, Integer daysLeftUntilExpiryToGiveDiscount) {
        this.shop_id = UUID.randomUUID();
        this.name = name;
        this.markupPercent = new EnumMap<>(ProductType.class);
        this.daysLeftUntilExpiryToGiveDiscount = daysLeftUntilExpiryToGiveDiscount;
        this.discountPercent = new EnumMap<>(ProductType.class);
        this.cashiers = new HashSet<>();
        this.product_Quantity = new HashMap<>();
        this.sellingPrice = new EnumMap<>(ProductType.class);
    }

    public UUID getShop_id() {
        return shop_id;
    }

    public void setShop_id(UUID shop_id) {
        this.shop_id = shop_id;
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

    public Set<Cashier> getCashiers() {
        return cashiers;
    }

    public Map<Products, BigDecimal> getProduct_Quantity() {
        return product_Quantity;
    }

    public Map<ProductType, Double> getSellingPrice() {
        return sellingPrice;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(shop_id, shop.shop_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shop_id);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + shop_id +
                ", name='" + name + '\'' +
                ", goods=" + product_Quantity +
                '}';
    }
}
