package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.Customers;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public interface CustomerDAO {
	void registerCustomer(Customers customer) throws SomethingWentWrongException;

	Customers authenticateCustomer(String email, String password) throws SomethingWentWrongException;
	
}
