package JavaClass.Data;

import JavaClass.Exceptions.DuplicateCashRegisterNumberException;

import java.math.BigDecimal;
import java.util.*;

public class Cashier {
    private static final Set<Integer> usedRegisterNumbers = new HashSet<>();
    private Map<Products, BigDecimal> productsSold = new HashMap<>();
    
    public UUID cashier_id;
    public String name;
    public BigDecimal salary;
    public Integer cash_register_number;

    public Cashier(String name, BigDecimal salary, Integer cash_register_number) {
        if(usedRegisterNumbers.contains(cash_register_number)){
            throw new DuplicateCashRegisterNumberException
                ("Cash register number " + cash_register_number + " is already in use.");
        }
        this.cashier_id = UUID.randomUUID();
        this.name = name;
        this.salary = salary;
        this.cash_register_number = cash_register_number;
        usedRegisterNumbers.add(cash_register_number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getCash_register_number() {
        return cash_register_number;
    }

    public void setCash_register_number(Integer newNumber) {
        if (Objects.equals(this.cash_register_number, newNumber)) {
            return; // Няма промяна
        }

        if (usedRegisterNumbers.contains(newNumber)) {
            throw new DuplicateCashRegisterNumberException("Cash register number " + newNumber + " is already in use.");
        }
        if (this.cash_register_number != null) {
            usedRegisterNumbers.remove(this.cash_register_number);
        }
        
        this.cash_register_number = newNumber;
        usedRegisterNumbers.add(newNumber);
    }

    public static Set<Integer> getUsedRegisterNumbers() {
        return usedRegisterNumbers;
    }

    public Map<Products, BigDecimal> getProductsSold() {
        return productsSold;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cashier cashier = (Cashier) o;
        return Objects.equals(cashier_id, cashier.cashier_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cashier_id);
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "cashier_id=" + cashier_id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", cash_register_number=" + cash_register_number +
                '}';
    }
}


