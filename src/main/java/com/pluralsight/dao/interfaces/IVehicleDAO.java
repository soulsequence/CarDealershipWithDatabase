package com.pluralsight.dao.interfaces;

import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.Contract;

import java.util.List;

public interface IVehicleDAO {
    Vehicle add(Vehicle Vehicle);
    List<Vehicle> getAlLVehicles();
    Vehicle getVehicleByID(int vehicleID);
    void update(int vehicleID, Vehicle vehicle);
    void delete(int vehicleID);
}
