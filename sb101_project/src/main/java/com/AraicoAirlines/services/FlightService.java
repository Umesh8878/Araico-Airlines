package com.AraicoAirlines.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;

import com.AraicoAirlines.dao.FlightDAO;
import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;
import com.AraicoAirlines.utility.DBUtils;

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
    
    public List<Flight> getAvailableFlights(String departureCity, String destinationCity, LocalDate journeyDate) throws SomethingWentWrongException {
        try {
            return flightDAO.getAvailableFlights(departureCity, destinationCity, journeyDate);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching available flights: " + e.getMessage());
        }
    }
    
    public List<Flight> getFlightsByDepartureDate(LocalDate departureDate) throws SomethingWentWrongException {
        try {
            return flightDAO.getFlightsByDepartureDate(departureDate);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by departure date");
        }
    }
    
    
    public List<Flight> getFlightsByDepartureCity(String departureCity) throws SomethingWentWrongException {
        try {
            return flightDAO.getFlightsByDepartureCity(departureCity);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by departure city");
        }
    }

    
    public List<Flight> getFlightsByDestinationCity(String destinationCity) throws SomethingWentWrongException {
        try {
            return flightDAO.getFlightsByDestinationCity(destinationCity);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by destination city");
        }
    }

    
    public List<Flight> getFlightsByPriceRange(double minPrice, double maxPrice) throws SomethingWentWrongException {
        try {
            return flightDAO.getFlightsByPriceRange(minPrice, maxPrice);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by price range");
        }
    }
    
    public List<Flight> getFilteredFlights(String departureCity, String destinationCity, double minPrice, double maxPrice, LocalDate departureDate) throws SomethingWentWrongException {
        try {
            return flightDAO.getFilteredFlights(departureCity, destinationCity, minPrice, maxPrice, departureDate);
        } catch (SomethingWentWrongException e) {
            throw new SomethingWentWrongException("Error fetching filtered flights");
        }
    }
    
    public boolean doesFlightExist(String flightNumberChoice) throws SomethingWentWrongException {
        try {
            Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM flight WHERE flight_number = ?");
            preparedStatement.setString(1, flightNumberChoice);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new SomethingWentWrongException("Error checking flight existence: " + e.getMessage());
        }
        return false;
    }

    public Flight getFlightByNumber(String flightNumberChoice) throws SomethingWentWrongException {
        try {
            return flightDAO.getFlightByNumber(flightNumberChoice);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flight by number: " + e.getMessage());
        }
    }
}

