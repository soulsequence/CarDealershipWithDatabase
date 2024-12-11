package com.pluralsight.model.contract;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SalesContract extends Contract{
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean financeOption;

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        int numberOfPayments = 0;
        double interestRate = 0;
        if (financeOption) {
            if (getVehicleSold().getPrice() >= 10000) {
                numberOfPayments = 48;
                interestRate = 4.25 / 1200;
            } else {
                numberOfPayments = 24;
                interestRate = 5.25 / 1200;
            }

            double monthlyPayment = getTotalPrice() * (interestRate * Math.pow(1 + interestRate, numberOfPayments)) / (Math.pow(1 + interestRate, numberOfPayments) - 1);
            monthlyPayment = Math.round(monthlyPayment * 100);
            monthlyPayment /= 100;
            return monthlyPayment;
        } else {
            return 0.0;
        }
    }
}
