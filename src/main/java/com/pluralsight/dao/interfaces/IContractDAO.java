package com.pluralsight.dao.interfaces;

import com.pluralsight.model.contract.Contract;

import java.util.List;

public interface IContractDAO {
    Contract insert(Contract contract);
    List<Contract> getAllContracts();
    Contract getContractByID(int contractID);
    void update(int contractID, Contract contract);
    void delete(int contractID);
}