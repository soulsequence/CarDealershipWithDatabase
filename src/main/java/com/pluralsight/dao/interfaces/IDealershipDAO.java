package com.pluralsight.dao.interfaces;

import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.Dealership;

import java.util.List;

public interface IDealershipDAO {
    Dealership add(Dealership dealership);
    List<Dealership> getAllDealerships();
    Dealership getDealershipByID(int dealershipID);
    void update(int dealershipID, Dealership dealership);
    void delete(int dealershipID);
}
