package com.pluralsight.model.contract;

import com.pluralsight.model.Customer;
import com.pluralsight.model.Vehicle;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@SuperBuilder
public abstract class Contract {
    private int contractID;
    private String date;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicleSold;
    private Customer customer;
    private double totalPrice;
    private double monthlyPayment;

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();
}
