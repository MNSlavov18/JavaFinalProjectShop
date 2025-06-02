package JavaClass.Data;

import JavaClass.Exceptions.DuplicateCashRegisterNumberException;

import java.math.BigDecimal;
import java.util.*;

public class Cashier {
    private static final Set<Integer> usedRegisterNumbers = new HashSet<>();
    private Map<Products, BigDecimal> productsSold = new HashMap<>();
    
    public UUID cashierId;
    public String name;
    public BigDecimal salary;
    public Integer cashRegisterNumber;

    public Cashier(String name, BigDecimal salary, Integer cash_register_number) {
        if(usedRegisterNumbers.contains(cash_register_number)){
            throw new DuplicateCashRegisterNumberException
                ("Cash register number " + cash_register_number + " is already in use.");
        }
        this.cashierId = UUID.randomUUID();
        this.name = name;
        this.salary = salary;
        this.cashRegisterNumber = cash_register_number;
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

    public Integer getCashRegisterNumber() {
        return cashRegisterNumber;
    }

    public void setCashRegisterNumber(Integer newNumber) {
        if (Objects.equals(this.cashRegisterNumber, newNumber)) {
            return; // Няма промяна
        }

        if (usedRegisterNumbers.contains(newNumber)) {
            throw new DuplicateCashRegisterNumberException("Cash register number " + newNumber + " is already in use.");
        }
        if (this.cashRegisterNumber != null) {
            usedRegisterNumbers.remove(this.cashRegisterNumber);
        }
        
        this.cashRegisterNumber = newNumber;
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
        return Objects.equals(cashierId, cashier.cashierId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cashierId);
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "cashier_id=" + cashierId +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", cash_register_number=" + cashRegisterNumber +
                '}';
    }
}


