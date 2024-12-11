package com.pluralsight.dao.impl;

import com.pluralsight.dao.interfaces.IContractDAO;
import com.pluralsight.model.Customer;
import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.LeaseContract;
import com.pluralsight.model.contract.SalesContract;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcContractDAO implements IContractDAO {

    private final DataSource dataSource;

    public JdbcContractDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Contract insert(Contract contract) {
        String sql = "INSERT INTO Contracts (Date, isSalesContract, Financed, VIN, CustomerID)";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            boolean isSalesContract = contract instanceof SalesContract;

            statement.setString(1, contract.getDate());
            statement.setBoolean(2, isSalesContract);
            if(isSalesContract) {
                statement.setBoolean(3,
                        ((SalesContract) contract).isFinanceOption());
            }
            statement.setInt(4, contract.getVehicleSold().getVin());
            statement.setInt(5, contract.getCustomer().getCustomerID());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating contract failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    contract.setContractID(generatedID);
                } else {
                    throw new SQLException("Creating contract failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contract;
    }

    @Override
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM Contracts c "
                + "JOIN Vehicles v ON c.VIN = v.VIN "
                + "JOIN Customers cu ON c.CustomerID = cu.CustomerID ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                // Create Vehicle
                Vehicle vehicle = new Vehicle(
                        rs.getInt("VIN"),
                        rs.getInt("Year"),
                        rs.getString("Make"),
                        rs.getString("Model"),
                        rs.getString("VehicleType"),
                        rs.getString("Color"),
                        rs.getInt("Odometer"),
                        rs.getDouble("Price")
                );

                // Create Customer
                Customer customer = new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("Name"),
                        rs.getString("Email")
                );

                int contractID = rs.getInt("ContractID");
                String date = rs.getString("Date");
                boolean isSalesContract = rs.getBoolean("isSalesContract");
                boolean isFinanced = rs.getBoolean("isFinanced");
                int vin = rs.getInt("VIN");
                int customerID = rs.getInt("CustomerID");

                if(isSalesContract) {
                    SalesContract salesContract = SalesContract.builder()
                            .contractID(contractID)
                            .date(date)
                            .financeOption(isFinanced)
                            .vehicleSold(vehicle)
                            .customer(customer)
                            .build();
                    contracts.add(salesContract);
                } else if (!isSalesContract) {
                    LeaseContract leaseContract = LeaseContract.builder()
                            .contractID(contractID)
                            .date(date)
                            .vehicleSold(vehicle)
                            .customer(customer)
                            .build();
                    contracts.add(leaseContract);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    @Override
    public Contract getContractByID(int id) {
        String sql = "SELECT * FROM Contracts WHERE ContractID = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int contractID = rs.getInt("ContractID");
                    int Date = rs.getInt("Date");
                    boolean financeOption = rs.getBoolean("isSalesContract");
                    boolean financed 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int contractID, Contract contract) {
        String sql = "UPDATE Contracts SET Date = ?, isSalesContract = ?, Financed = ?," +
                " VIN = ?, CustomerID = ? WHERE ContractID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            boolean isSalesContract = contract instanceof SalesContract;

            statement.setString(1, contract.getDate());
            statement.setBoolean(2, isSalesContract);

            if(isSalesContract) {
                statement.setBoolean(3,
                        ((SalesContract) contract).isFinanceOption());
            }

            statement.setInt(4, contract.getVehicleSold().getVin());
            statement.setInt(5, contract.getCustomer().getCustomerID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int contractID) {
        String sql = "DELETE FROM Contracts WHERE ContractID = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, contractID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}