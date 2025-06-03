package JavaClass.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Shop  implements Serializable {
    public UUID shop_id;
    public String name;
    public Map<ProductType, Double> markupPercent;
    public Integer daysLeftUntilExpiryToGiveDiscount;
    public Map<ProductType, Double> discountPercent;
    public Set<Cashier> cashiers;
    public Map<Products, BigDecimal> product_Quantity;
    public Map<Products, BigDecimal> deliveredProducts = new HashMap<>();
    public Map<ProductType, Double> sellingPrice;

    public Shop(String name, Integer daysLeftUntilExpiryToGiveDiscount) {
        this.shop_id = UUID.randomUUID();
        this.name = name;
        this.markupPercent = new EnumMap<>(ProductType.class);
        this.daysLeftUntilExpiryToGiveDiscount = daysLeftUntilExpiryToGiveDiscount;
        this.discountPercent = new EnumMap<>(ProductType.class);
        this.cashiers = new HashSet<>();
        this.product_Quantity = new HashMap<>();
        this.deliveredProducts = new HashMap<>();
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

    public Map<Products, BigDecimal> getDeliveredProducts() {
        return deliveredProducts;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Магазин: ").append(name).append("\n");
        sb.append("ID: ").append(shop_id).append("\n");
        sb.append("Надценки: ").append(markupPercent).append("\n");
        sb.append("Отстъпки: ").append(discountPercent).append("\n");
        sb.append("Дни до отстъпка: ").append(daysLeftUntilExpiryToGiveDiscount).append("\n");
        sb.append("Касиери: ").append(cashiers).append("\n");

        sb.append("Продукти в наличност:\n");
        product_Quantity.forEach((product, qty) -> {
            sb.append(" - ").append(product.getName()).append(": ").append(qty).append("\n");
        });

        sb.append("Доставени продукти:\n");
        deliveredProducts.forEach((product, qty) -> {
            sb.append(" - ").append(product.getName()).append(": ").append(qty).append("\n");
        });
        
        return sb.toString();
    }
}
