package com.pluralsight.dao.impl;

import com.pluralsight.dao.interfaces.IVehicleDAO;
import com.pluralsight.model.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcVehicleDAO implements IVehicleDAO {

    private final DataSource dataSource;

    public JdbcVehicleDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // CRUD operations
    @Override
    public Vehicle insert(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicles (VIN, Year, Make, Model, VehicleType, Color, Odometer, Price, Sold) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicle.getVin());
            statement.setInt(2, vehicle.getYear());
            statement.setString(3, vehicle.getMake());
            statement.setString(4, vehicle.getModel());
            statement.setString(5, vehicle.getVehicleType());
            statement.setString(6, vehicle.getColor());
            statement.setInt(7, vehicle.getOdometer());
            statement.setDouble(8, vehicle.getPrice());
            statement.setBoolean(9, false);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating vehicle failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int vin = rs.getInt("VIN");
                int year = rs.getInt("Year");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                String vehicleType = rs.getString("VehicleType");
                String color = rs.getString("Color");
                int odometer = rs.getInt("Odometer");
                double price = rs.getDouble("Price");

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public Vehicle getVehicleByVin(int vin) {
        String sql = "SELECT * FROM Vehicles WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vin);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int year = rs.getInt("Year");
                    String make = rs.getString("Make");
                    String model = rs.getString("Model");
                    String vehicleType = rs.getString("VehicleType");
                    String color = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int vin, Vehicle vehicle) {
        String sql = "UPDATE Vehicles SET Year = ?, Make = ?, Model = ?, VehicleType = ?, Color = ?, Odometer = ?, Price = ? " +
                "WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicle.getYear());
            statement.setString(2, vehicle.getMake());
            statement.setString(3, vehicle.getModel());
            statement.setString(4, vehicle.getVehicleType());
            statement.setString(5, vehicle.getColor());
            statement.setInt(6, vehicle.getOdometer());
            statement.setDouble(7, vehicle.getPrice());
            statement.setInt(8, vin);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int vin) {
        String sql = "DELETE FROM Vehicles WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Workshop Specified Methods
    public List<Vehicle> getVehiclesByMakeAndModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE Make = ? AND Model = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, make);
            statement.setString(2, model);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int vin = rs.getInt("VIN");
                    int year = rs.getInt("Year");
                    String vehicleMake = rs.getString("Make");
                    String vehicleModel = rs.getString("Model");
                    String vehicleType = rs.getString("VehicleType");
                    String color = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    Vehicle vehicle = new Vehicle(vin, year, vehicleMake, vehicleModel, vehicleType, color, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE Price BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int vin = rs.getInt("VIN");
                    int year = rs.getInt("Year");
                    String make = rs.getString("Make");
                    String model = rs.getString("Model");
                    String vehicleType = rs.getString("VehicleType");
                    String color = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByYearRange(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE Year BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, minYear);
            statement.setInt(2, maxYear);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int vin = rs.getInt("VIN");
                    int year = rs.getInt("Year");
                    String make = rs.getString("Make");
                    String model = rs.getString("Model");
                    String vehicleType = rs.getString("VehicleType");
                    String color = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE Color = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, color);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int vin = rs.getInt("VIN");
                    int year = rs.getInt("Year");
                    String make = rs.getString("Make");
                    String model = rs.getString("Model");
                    String vehicleType = rs.getString("VehicleType");
                    String vehicleColor = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, vehicleColor, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByMileageRange(int minOdometer, int maxOdometer) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE Odometer BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, minOdometer);
            statement.setInt(2, maxOdometer);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int vin = rs.getInt("VIN");
                    int year = rs.getInt("Year");
                    String make = rs.getString("Make");
                    String model = rs.getString("Model");
                    String vehicleType = rs.getString("VehicleType");
                    String color = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE VehicleType = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vehicleType);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int vin = rs.getInt("VIN");
                    int year = rs.getInt("Year");
                    String make = rs.getString("Make");
                    String model = rs.getString("Model");
                    String type = rs.getString("VehicleType");
                    String color = rs.getString("Color");
                    int odometer = rs.getInt("Odometer");
                    double price = rs.getDouble("Price");

                    Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}