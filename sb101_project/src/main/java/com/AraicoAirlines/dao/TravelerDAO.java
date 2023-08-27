package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.Traveler;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public interface TravelerDAO {
    void saveTraveler(Traveler traveler) throws SomethingWentWrongException;
}
