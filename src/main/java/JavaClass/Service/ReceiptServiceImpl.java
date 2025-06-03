package JavaClass.Service;

import JavaClass.Data.Products;
import JavaClass.Data.Receipt;
import JavaClass.Data.Shop;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ReceiptServiceImpl implements ReceiptService {
    private static int totalReceipts = 0;
    private static BigDecimal totalRevenue = BigDecimal.ZERO;
    
        public void printReceiptToFile(Receipt receipt, Shop shop, ShopService shopService, LocalDate today){
            String fileName = "receipt_" + receipt.getReceiptId() + ".txt";
            StringBuilder receiptContent = new StringBuilder();

            receiptContent.append("Касова бележка ID: ").append(receipt.getReceiptId()).append("\n");
            receiptContent.append("Касиер: ").append(receipt.getCashier().getName()).append("\n");
            receiptContent.append("Дата на покупка: ").append(receipt.getDateOfPurchase()).append(" ").append(receipt.getTimeOfPurchase()).append("\n");
            receiptContent.append("--------------------------------------------------\n");

            for (Map.Entry<Products, BigDecimal> entry : receipt.getProductsSold().entrySet()) {
                Products product = entry.getKey();
                BigDecimal quantity = entry.getValue();
                BigDecimal price = shopService.getFinalProductPrice(shop, product, today);

                receiptContent.append(String.format("%s (%s): %.2f бр. Цена за бройка: %.2f\n",
                        product.getName(),
                        product.getType(),
                        quantity,
                        price));
            }

            receiptContent.append("--------------------------------------------------\n");
            receiptContent.append("Обща стойност: ").append(receipt.getTotalPrice()).append(" лв.\n");
            receiptContent.append("--------------------------------------------------\n");
            
            System.out.println(receiptContent.toString());
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(receiptContent.toString());
                System.out.println("Касовата бележка е записана във файл: " + fileName);
            } catch (IOException e) {
                System.err.println("Грешка при запис на касовата бележка: " + e.getMessage());
            }
            
            updateStats(receipt.getTotalPrice());
            
        serializeReceipt(receipt);
            
    }
    
    private void updateStats(BigDecimal amount) {
        totalReceipts++;
        totalRevenue = totalRevenue.add(amount);
    }
    
    private void serializeReceipt(Receipt receipt) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("receipt_" + receipt.getReceiptId() + ".ser"))) {
            oos.writeObject(receipt);
            System.out.println("Бележката е сериализирана в receipt_" + receipt.getReceiptId() + ".ser");
        } catch (IOException e) {
            System.err.println("Грешка при сериализацията: " + e.getMessage());
        }
    }

    public Receipt deserializeReceipt(String receiptId) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(receiptId + ".ser"))) {
            Receipt receipt = (Receipt) ois.readObject();
            System.out.println("Бележката е десериализирана успешно!");
            return receipt;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Грешка при десериализацията: " + e.getMessage());
            return null;
        }
        
    }

    public void printDeserializedReceipt(String receiptId) {
        Receipt receipt = deserializeReceipt(receiptId);

        if (receipt != null) {
            System.out.println("Блежката е заредена!");
            System.out.println("ID: " + receipt.getReceiptId());
            System.out.println("Дата на копуване: " + receipt.getDateOfPurchase() + " "+ receipt.getTimeOfPurchase());
            System.out.println("Цена: " + receipt.getTotalPrice());
            System.out.println("Ксиер: " + receipt.getCashier().getName());

            for (Map.Entry<Products, BigDecimal> entry : receipt.getProductsSold().entrySet()) {
                Products product = entry.getKey();
                BigDecimal quantity = entry.getValue();
                System.out.println("Пеодукт: " + product.getName() + ", Количество: " + quantity);
            }
        } else {
            System.out.println("Не може да се зареди бележка.");
        }
    }
    
    @Override
    public int getTotalReceipts() {
        return totalReceipts;
    }

    @Override
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
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

