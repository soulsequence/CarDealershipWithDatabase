package com.pluralsight.dao.interfaces;

import com.pluralsight.model.Customer;
import com.pluralsight.model.contract.Contract;

import java.util.List;

public interface ICustomerDAO {
    Customer insert(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerByID(int customerID);
    void update(int customerID, Customer customer);
    void delete(int customerID);
}
