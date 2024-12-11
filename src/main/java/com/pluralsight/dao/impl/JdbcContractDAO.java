package com.pluralsight.dao.impl;

import com.pluralsight.dao.interfaces.IContractDAO;
import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.ContractType;
import com.pluralsight.model.contract.LeaseContract;
import com.pluralsight.model.contract.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcContractDAO implements IContractDAO {

    private final DataSource dataSource;

    public JdbcContractDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Contract add(Contract contract) {
        return null;
    }

    @Override
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM Contracts";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int contractID = rs.getInt("ContractID");
                String date = rs.getString("Date");
                ContractType contractType = ContractType.valueOf(rs.getString("ContractType"));
                boolean isFinanced = rs.getBoolean("Financed");

                if(contractType == ContractType.SALE) {
                    SalesContract salesContract = SalesContract.builder()
                            .contractID(contractID)
                            .date(date)
                            .financeOption(isFinanced)
                            .build();
                    contracts.add(salesContract);
                } else if (contractType == ContractType.LEASE) {
                    LeaseContract leaseContract = LeaseContract.builder()
                            .contractID(contractID)
                            .date(date)
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
    public Contract getContractByID(int contractID) {
        return null;
    }

    @Override
    public void update(int contractID, Contract contract) {

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