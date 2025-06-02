package JavaClass.Service;

import JavaClass.Data.Products;
import JavaClass.Data.Receipt;
import JavaClass.Data.Shop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ReceiptServiceImpl implements ReceiptService {

    public void printReceiptToFile(Receipt receipt, Shop shop, ShopService shopService, LocalDate today) {
        String fileName = "receipt_" + receipt.getReceiptId() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Касова бележка ID: " + receipt.getReceiptId() + "\n");
            writer.write("Касиер: " + receipt.getCashier().getName() + "\n");
            writer.write("Дата на покупка: " + receipt.getDateOfPurchase() + "\n");
            writer.write("--------------------------------------------------\n");

            for (Map.Entry<Products, BigDecimal> entry : receipt.getProductsSold().entrySet()) {
                Products product = entry.getKey();
                BigDecimal quantity = entry.getValue();

                // Use your ShopServiceImpl to get the final price for the product
                BigDecimal price = shopService.getFinalProductPrice(shop, product, today);

                writer.write(String.format("%s (%s): %.2f бр. Цена за бройка: %.2f\n",
                        product.getName(),
                        product.getType(),
                        quantity,
                        price));
            }

            writer.write("--------------------------------------------------\n");
            writer.write("Обща стойност: " + receipt.getTotalPrice() + " лв.\n");
            writer.write("--------------------------------------------------\n");

            System.out.println("Касовата бележка е записана във файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Грешка при запис на касовата бележка: " + e.getMessage());
        }
    }
    @Override
    public BigDecimal calculateTotalPrice(Shop shop, Map<Products, BigDecimal> productsSold, LocalDate today, ShopService shopService) {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Products, BigDecimal> entry : productsSold.entrySet()) {
            Products product = entry.getKey();
            BigDecimal quantity = entry.getValue();

            // Use the provided shopService instead of creating a new one
            BigDecimal finalPrice = shopService.getFinalProductPrice(shop, product, today);
            total = total.add(finalPrice.multiply(quantity));
        }

        return total;
    }
}
