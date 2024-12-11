package com.pluralsight.dao.interfaces;

import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.Contract;

import java.util.List;

public interface IVehicleDAO {
    Vehicle insert(Vehicle Vehicle);
    List<Vehicle> getAllVehicles();
    Vehicle getVehicleByVin(int vehicleID);
    void update(int vehicleID, Vehicle vehicle);
    void delete(int vehicleID);
}
