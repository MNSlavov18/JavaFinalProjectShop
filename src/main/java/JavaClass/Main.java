package JavaClass;

import JavaClass.Data.Products;
import JavaClass.Data.ProductType;
import JavaClass.Data.Shop;
import JavaClass.Service.ShopService;
import JavaClass.Service.ShopServiceImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Shop Lidl = new Shop("Lidl",  7);
        ShopService service = new ShopServiceImpl();
        
        Products eggs = new Products("Eggs",0.50 , ProductType.FOOD, LocalDate.of(2025,5,18));
        
        service.addProduct(Lidl,eggs);
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
    }
}