package JavaClass;

import JavaClass.Data.Cashier;
import JavaClass.Data.Products;
import JavaClass.Data.ProductType;
import JavaClass.Data.Shop;
import JavaClass.Service.CashierService;
import JavaClass.Service.CashierServiceImpl;
import JavaClass.Service.ShopService;
import JavaClass.Service.ShopServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Shop Lidl = new Shop("Lidl",  7);
        ShopService service = new ShopServiceImpl();
        CashierService cashierService = new CashierServiceImpl();
        
        Products eggs = new Products("Eggs", new BigDecimal(0.50) , ProductType.FOOD, LocalDate.of(2025,5,18));
        
        service.addProduct(Lidl, eggs , new BigDecimal(22));
        service.printProduct(Lidl);
        
        System.out.println();
        
        service.addMarkupPercent(Lidl, ProductType.FOOD, 5.00);
        service.addMarkupPercent(Lidl, ProductType.NON_FOOD, 7.00);
        service.printMarkupPercent(Lidl);
        
        System.out.println();
        
        service.addDiscountPercent(Lidl, ProductType.FOOD, 50.00);
        service.addDiscountPercent(Lidl, ProductType.NON_FOOD, 7.00);
        service.printDiscountPercent(Lidl);
        
        System.out.println();
        
        service.printProductPricing(Lidl,eggs,LocalDate.of(2025,5,17));
        Cashier cashier1 = new Cashier("Mario", new BigDecimal(2000), 1);
        cashierService.releaseRegisterNumber(cashier1);
        Cashier cashier2 = new Cashier("Isma", new BigDecimal(2000), 1);
        
        System.out.println();
        
        service.addCashier(Lidl, cashier1);
        service.addCashier(Lidl, cashier2);
        service.printCashier(Lidl);
        
        System.out.println();
        
        cashierService.sellProduct(Lidl,cashier2,eggs,new BigDecimal(20));
        
        cashierService.printCashierSalesReport(cashier1);
        cashierService.printCashierSalesReport(cashier2);
        
    }
}