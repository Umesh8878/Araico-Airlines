package com.AraicoAirlines.dao;

import java.util.List;

import com.AraicoAirlines.dto.Customers;
import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.utility.DBUtils;
import com.AraicoAirlines.exceptions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class FlightDAOImpl implements FlightDAO {
	@Override
	public void addFlight(Flight flight) throws SomethingWentWrongException {
	    EntityManager entityManager = DBUtils.getEntityManager();
	    EntityTransaction transaction = entityManager.getTransaction();

	    try {
	        transaction.begin();
	        entityManager.persist(flight);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();
	        }
	        throw new SomethingWentWrongException("Error adding flight: " + e.getMessage());
	    } finally {
	        entityManager.close();
	    }
	}



	@Override
    public void updateFlight(Flight flight) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(flight);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SomethingWentWrongException("Error updating flight: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
	
	@Override
	public void removeFlight(String flightNumber) throws SomethingWentWrongException {
	    EntityManager entityManager = DBUtils.getEntityManager();
	    EntityTransaction transaction = entityManager.getTransaction();

	    try {
	        transaction.begin();
	        Flight flight = entityManager.find(Flight.class, flightNumber);
	        if (flight != null) {
	            entityManager.remove(flight);
	        } else {
	            throw new NoRecordFoundException("Flight with flight number " + flightNumber + " not found.");
	        }
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();
	        }
	        throw new SomethingWentWrongException("Error removing flight: " + e.getMessage());
	    } finally {
	        entityManager.close();
	    }
	}

	@Override
    public Flight getFlightByNumber(String flightNumber) {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            return entityManager.find(Flight.class, flightNumber);
        } finally {
            entityManager.close();
        }
    }
	
	@Override
    public List<Flight> getAllFlights() throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT f FROM Flight f", Flight.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching all flights");
        } finally {
            entityManager.close();
        }
    }
}
