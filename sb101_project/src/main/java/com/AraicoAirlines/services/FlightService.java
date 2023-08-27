package com.AraicoAirlines.services;

import java.util.List;

import com.AraicoAirlines.dao.FlightDAO;
import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public class FlightService {
    private final FlightDAO flightDAO;

    public FlightService(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    public void addFlight(Flight flight) throws SomethingWentWrongException {
        try {
            flightDAO.addFlight(flight);
        } catch (SomethingWentWrongException e) {
            throw e;
        }
    }

    public void updateFlight(Flight flight) throws SomethingWentWrongException {
        try {
            flightDAO.updateFlight(flight);
        } catch (SomethingWentWrongException e) {
            throw e;
        }
    }

    public void removeFlight(String flightNumber) throws SomethingWentWrongException {
        try {
            flightDAO.removeFlight(flightNumber);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error removing flight");
        }
    }
    
    public Flight fetchFlightDetailsFromDatabase(String flightNumber) {
        try {
            return flightDAO.getFlightByNumber(flightNumber);
        } catch (Exception e) {
            // Handle the exception or rethrow it as needed
            throw e;
        }
    }
    
    public List<Flight> getAllFlights() throws SomethingWentWrongException {
        try {
            return flightDAO.getAllFlights();
        } catch (SomethingWentWrongException e) {
            throw e;
        }
    }
}

