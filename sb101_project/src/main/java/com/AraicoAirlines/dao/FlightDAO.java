package com.AraicoAirlines.dao;

import java.time.LocalDate;
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
    List<Flight> getAvailableFlights(String departureCity, String destinationCity, LocalDate journeyDate) throws SomethingWentWrongException;
    List<Flight> getFlightsByDepartureDate(LocalDate departureDate) throws SomethingWentWrongException;
    List<Flight> getFlightsByDepartureCity(String departureCity) throws SomethingWentWrongException;
    List<Flight> getFlightsByDestinationCity(String destinationCity) throws SomethingWentWrongException;
    List<Flight> getFlightsByPriceRange(double minPrice, double maxPrice) throws SomethingWentWrongException;
	List<Flight> getFilteredFlights(String departureCity, String destinationCity, double minPrice, double maxPrice, LocalDate departureDate)
			throws SomethingWentWrongException;
}
