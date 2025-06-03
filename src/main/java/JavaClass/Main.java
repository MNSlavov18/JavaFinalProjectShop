package JavaClass;

import JavaClass.Data.Cashier;
import JavaClass.Data.Products;
import JavaClass.Data.ProductType;
import JavaClass.Data.Shop;
import JavaClass.Service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

public class Main {
    public static void main(String[] args) {
        ShopService shopService = new ShopServiceImpl();
        CashierService cashierService = new CashierServiceImpl();
        ReceiptService receiptService = new ReceiptServiceImpl();

        Shop Lidl = new Shop("Lidl",  7);
        
        Products eggs = new Products("Яйца", new BigDecimal(0.50) 
                , ProductType.FOOD, LocalDate.of(2025,6,3));
        Products stol = new Products("Стол", new BigDecimal(50) 
                , ProductType.NON_FOOD,null);
        
        shopService.addProduct(Lidl, eggs , new BigDecimal(22));
        shopService.addProduct(Lidl, stol , new BigDecimal(2));
        shopService.printProduct(Lidl);
        
        System.out.println();
        
        shopService.addMarkupPercent(Lidl, ProductType.FOOD, 5.00);
        shopService.addMarkupPercent(Lidl, ProductType.NON_FOOD, 7.00);
        shopService.printMarkupPercent(Lidl);
        
        System.out.println();
        
        shopService.addDiscountPercent(Lidl, ProductType.FOOD, 50.00);
        shopService.addDiscountPercent(Lidl, ProductType.NON_FOOD, 7.00);
        shopService.printDiscountPercent(Lidl);
        
        System.out.println();
        
        shopService.printProductPricing(Lidl,eggs,LocalDate.now());
        shopService.printProductPricing(Lidl,stol,LocalDate.now());
        shopService.getFinalProductPrice(Lidl,eggs,LocalDate.now());
        shopService.getFinalProductPrice(Lidl,stol,LocalDate.now());
        
        Cashier cashier1 = new Cashier("Mario", new BigDecimal(2000), 1);
        Cashier cashier2 = new Cashier("Isma", new BigDecimal(2000), 2);
        
        System.out.println();
        
        shopService.addCashier(Lidl, cashier1);
        shopService.addCashier(Lidl, cashier2);
        shopService.printCashier(Lidl);
        
        System.out.println();
        
        cashierService.sellProduct(Lidl,cashier2,eggs,new BigDecimal(20),new BigDecimal(20)
                ,LocalDate.now(), LocalTime.now(),shopService,receiptService);
        
        cashierService.sellProduct(Lidl,cashier1,stol,new BigDecimal(2),new BigDecimal(150)
                ,LocalDate.now(),LocalTime.now(),shopService,receiptService);
        
        
        cashierService.printCashierSalesReport(cashier1);
        cashierService.printCashierSalesReport(cashier2);

        System.out.println();
        
        shopService.printShop(Lidl);
        
        System.out.println();
        
        System.out.println("Общ брой бележки: " + receiptService.getTotalReceipts());
        System.out.println("Общ оборот: " + receiptService.getTotalRevenue());

        System.out.println();
        
        BigDecimal totalSalaries = shopService.calculateTotalSalaries(Lidl);
        BigDecimal totalDeliveryCosts = shopService.calculateTotalDeliveryCosts(Lidl);
        BigDecimal totalRevenue = shopService.calculateTotalRevenue(Lidl);
        BigDecimal profit = shopService.calculateProfit(Lidl);
        YearMonth currentMonth = YearMonth.now();

        System.out.println("Общи разходи за заплати: " + totalSalaries);
        System.out.println("Общи разходи за доставка: " + totalDeliveryCosts);
        System.out.println("Общи приходи от продажби: " + totalRevenue);
        System.out.println("Печалба: " + profit);
        
        System.out.println();
        
        BigDecimal monthlyProfitFromSerializedReceipts = shopService.calculateMonthlyProfitFromSerializedReceipts(Lidl.getCashiers(), currentMonth);
        
        System.out.println();
        
        String receiptId = "receipt_dd7f9600-c21e-4cd2-8d8d-a1821c91750d";
        receiptService.deserializeReceipt(receiptId);
        receiptService.printDeserializedReceipt(receiptId);
    }
}