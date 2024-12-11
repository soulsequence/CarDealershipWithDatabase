package com.pluralsight.model;

import com.pluralsight.dao.impl.JdbcContractDAO;
import com.pluralsight.dao.impl.JdbcCustomerDAO;
import com.pluralsight.dao.impl.JdbcVehicleDAO;
import com.pluralsight.db.DataManager;
import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.LeaseContract;
import com.pluralsight.model.contract.SalesContract;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private JdbcVehicleDAO jdbcVehicleDAO;
    private JdbcContractDAO jdbcContractDAO;
    private JdbcCustomerDAO jdbcCustomerDAO;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell/Lease a vehicle");
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processSellLeaseVehicleRequest();
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processSellLeaseVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle to sell or lease: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = jdbcVehicleDAO.getVehicleByVin(vin);

        if (vehicle == null) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }

        System.out.print("Enter the contract year (YYYY): ");
        int contractDate = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the customer id: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        Customer customer = jdbcCustomerDAO.getCustomerByID(customerId);

        System.out.print("Is it a sale or lease? (sale/lease): ");
        String contractType = scanner.nextLine();

        Contract contract;
        if (contractType.equalsIgnoreCase("sale")) {
            System.out.print("Is financing available? (yes/no): ");
            String financeOption = scanner.nextLine();

            double salesTaxAmount = vehicle.getPrice() * 0.05;
            double recordingFee = 100;
            double processingFee = vehicle.getPrice() < 10000 ? 295 : 495;
            boolean finance = financeOption.equalsIgnoreCase("yes");

            contract = SalesContract.builder()
                    .date(contractDate)
                    .customer(customer)
                    .vehicleSold(vehicle)
                    .salesTaxAmount(salesTaxAmount)
                    .recordingFee(recordingFee)
                    .processingFee(processingFee)
                    .financeOption(finance)
                    .build();
            jdbcContractDAO.insert(contract);
        } else if (contractType.equalsIgnoreCase("lease")) {
            double expectedEndingValue = vehicle.getPrice() / 2;
            double leaseFee = vehicle.getPrice() * 0.07;

            contract = LeaseContract.builder()
                    .date(contractDate)
                    .customer(customer)
                    .vehicleSold(vehicle)
                    .expectedEndingValue(expectedEndingValue)
                    .leaseFee(leaseFee)
                    .build();
            jdbcContractDAO.insert(contract);
        } else {
            System.out.println("Invalid contract type. Please try again.");
            return;
        }

        System.out.println("Contract saved successfully!");
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        List<Vehicle> vehicles = jdbcVehicleDAO.getVehiclesByPriceRange(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = jdbcVehicleDAO.getVehiclesByMakeAndModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = jdbcVehicleDAO.getVehiclesByYearRange(min, max);
        displayVehicles(vehicles);
        scanner.nextLine();
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = jdbcVehicleDAO.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = jdbcVehicleDAO.getVehiclesByMileageRange(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();
        List<Vehicle> vehicles = jdbcVehicleDAO.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = jdbcVehicleDAO.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter vehicle vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();

        System.out.print("Enter vehicle year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();

        System.out.print("Enter vehicle mileage: ");
        int mileage = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle type (Car, Truck, SUV, Motorcycle): ");
        String type = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);

        jdbcVehicleDAO.insert(vehicle);
        System.out.println("Vehicle added successfully!");
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you wish to remove: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        jdbcVehicleDAO.delete(vin);
    }

    private void init() {
        DataManager dataManager = new DataManager("jdbc:mysql://localhost:3306/cardealership", "root", "P@ssw0rd");
        jdbcVehicleDAO = new JdbcVehicleDAO(dataManager.getDataSource());
        jdbcContractDAO = new JdbcContractDAO(dataManager.getDataSource());
        jdbcCustomerDAO = new JdbcCustomerDAO(dataManager.getDataSource());
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }

}
