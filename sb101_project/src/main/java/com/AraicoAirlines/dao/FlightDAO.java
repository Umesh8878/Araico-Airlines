package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public interface FlightDAO {
    void addFlight(Flight flight) throws SomethingWentWrongException;
    void updateFlight(Flight flight);
    void removeFlight(String flightNumber);
	Flight getFlightByNumber(String flightNumber);
}