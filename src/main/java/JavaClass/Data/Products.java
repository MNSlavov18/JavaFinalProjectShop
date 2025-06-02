package JavaClass.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Products implements Serializable {
    private UUID product_id;
    private String name;
    private BigDecimal deliveryPrice;
    private ProductType type;
    private LocalDate expiryDate;

    public Products(String name, BigDecimal deliveryPrice, ProductType type, LocalDate expiryDate) {
        this.product_id = UUID.randomUUID();
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.type = type;
        this.expiryDate = expiryDate;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Products goods = (Products) o;
        return Objects.equals(product_id, goods.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product_id);
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + product_id +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", type=" + type +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
