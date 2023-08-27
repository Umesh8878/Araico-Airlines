package com.AraicoAirlines.services;

import com.AraicoAirlines.dao.CustomerDAO;
import com.AraicoAirlines.dto.Customers;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void registerCustomer(Customers customer) throws SomethingWentWrongException {
        try {
            customerDAO.registerCustomer(customer);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error registering customer");
        }
    }
    
    public Customers authenticateCustomer(String email, String password) throws SomethingWentWrongException {
        try {
            return customerDAO.authenticateCustomer(email, password);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error authenticating customer");
        }
    }
}

