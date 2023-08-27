package com.AraicoAirlines.services;

import com.AraicoAirlines.dto.Traveler;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;
import com.AraicoAirlines.dao.*;

public class TravelerService {
    private TravelerDAOImpl travelerDAO = new TravelerDAOImpl();

    public void saveTraveler(Traveler traveler) throws SomethingWentWrongException {
        travelerDAO.saveTraveler(traveler);
    }
}

