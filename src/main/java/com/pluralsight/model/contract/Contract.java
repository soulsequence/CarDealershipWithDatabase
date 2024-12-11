package com.pluralsight.model.contract;

import com.pluralsight.model.Vehicle;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public abstract class Contract {
    private String date;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicleSold;
    private double totalPrice;
    private double monthlyPayment;

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();
}
