package com.pluralsight.model.contract;

import com.pluralsight.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Data
@AllArgsConstructor
public class Dealership {
    private int dealershipID;
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return inventory.stream().filter(
                vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max).toList();
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return inventory.stream().filter(
                vehicle -> vehicle.getMake().equalsIgnoreCase(make)
                && vehicle.getModel().equalsIgnoreCase(model)).toList();
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        return inventory.stream().filter(
                vehicle -> vehicle.getYear() >= min && vehicle.getYear() <= max).toList();
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        return inventory.stream().filter(
                vehicle -> vehicle.getColor().equalsIgnoreCase(color)).toList();
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        return inventory.stream().filter(
                vehicle -> vehicle.getOdometer() >= min && vehicle.getOdometer() <= max).toList();
    }

    public List<Vehicle> getVehicleByType(String vehicleType) {
        return inventory.stream().filter(
                vehicle -> vehicle.getVehicleType().equalsIgnoreCase(vehicleType)).toList();
    }

    public List<Vehicle> getAllVehicles() { return inventory; }

    public void addVehicle(Vehicle vehicle) {inventory.add(vehicle); }

    public void removeVehicle(Vehicle vehicle) {
        inventory.remove(vehicle);
    }
}
