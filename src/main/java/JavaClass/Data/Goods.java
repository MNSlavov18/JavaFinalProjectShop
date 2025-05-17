package JavaClass.Data;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

public class Goods {
    private UUID id;
    private String name;
    private Double deliveryPrice;
    private GoodsType type;
    private LocalDate expiryDate;

    public Goods(String name, Double deliveryPrice, GoodsType type, LocalDate expiryDate) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.type = type;
        this.expiryDate = expiryDate;
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

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
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
        Goods goods = (Goods) o;
        return Objects.equals(id, goods.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", type=" + type +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
