package com.AraicoAirlines.dao;

import java.util.List;

import com.AraicoAirlines.dto.Customers;
import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public interface FlightDAO {
    void addFlight(Flight flight) throws SomethingWentWrongException;
    void updateFlight(Flight flight) throws SomethingWentWrongException;
    void removeFlight(String flightNumber) throws SomethingWentWrongException;
    Flight getFlightByNumber(String flightNumber);
    List<Flight> getAllFlights() throws SomethingWentWrongException;
}
