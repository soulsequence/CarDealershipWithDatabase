package com.pluralsight.model;

import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Vehicle {
    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;
}
