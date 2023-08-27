package com.AraicoAirlines.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.AraicoAirlines.dto.Customers;
import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.utility.DBUtils;
import com.AraicoAirlines.exceptions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
	
	@Override
	public List<Flight> getAvailableFlights(String departureCity, String destinationCity, LocalDate journeyDate) throws SomethingWentWrongException {
	    EntityManager entityManager = DBUtils.getEntityManager();
	    try {
	        TypedQuery<Flight> query = entityManager.createQuery(
	            "SELECT f FROM Flight f WHERE f.departureCity = :departureCity " +
	            "AND f.destinationCity = :destinationCity AND DATE(f.departureTime) = :journeyDate",
	            Flight.class
	        );
	        query.setParameter("departureCity", departureCity);
	        query.setParameter("destinationCity", destinationCity);
	        query.setParameter("journeyDate", journeyDate);
	        return query.getResultList();
	    } finally {
	        entityManager.close();
	    }
	}
	
	@Override
	public List<Flight> getFlightsByDepartureDate(LocalDate departureDate) throws SomethingWentWrongException {
	    EntityManager entityManager = DBUtils.getEntityManager();

	    try {
	        TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE DATE(f.departureTime) = :departureDate", Flight.class);
	        query.setParameter("departureDate", departureDate);
	        return query.getResultList();
	    } finally {
	        entityManager.close();
	    }
	}
	
	@Override
    public List<Flight> getFlightsByDepartureCity(String departureCity) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE f.departureCity = :departureCity", Flight.class);
            query.setParameter("departureCity", departureCity);
            return query.getResultList();
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by departure city");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Flight> getFlightsByDestinationCity(String destinationCity) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE f.destinationCity = :destinationCity", Flight.class);
            query.setParameter("destinationCity", destinationCity);
            return query.getResultList();
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by destination city");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Flight> getFlightsByPriceRange(double minPrice, double maxPrice) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            TypedQuery<Flight> query = entityManager.createQuery("SELECT f FROM Flight f WHERE f.price BETWEEN :minPrice AND :maxPrice", Flight.class);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            return query.getResultList();
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching flights by price range");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Flight> getFilteredFlights(String departureCity, String destinationCity, double minPrice, double maxPrice, LocalDate departureDate) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
            Root<Flight> flightRoot = criteriaQuery.from(Flight.class);

            List<Predicate> predicates = new ArrayList<>();

            if (departureCity != null && !departureCity.isEmpty()) {
                predicates.add(criteriaBuilder.equal(flightRoot.get("departureCity"), departureCity));
            }

            if (destinationCity != null && !destinationCity.isEmpty()) {
                predicates.add(criteriaBuilder.equal(flightRoot.get("destinationCity"), destinationCity));
            }

            if (minPrice > 0) {
                predicates.add(criteriaBuilder.ge(flightRoot.get("price"), minPrice));
            }

            if (maxPrice > 0) {
                predicates.add(criteriaBuilder.le(flightRoot.get("price"), maxPrice));
            }

            if (departureDate != null) {
                // Extract date part from departureTime column and compare
                predicates.add(criteriaBuilder.equal(criteriaBuilder.function("date", LocalDate.class, flightRoot.get("departureTime")), departureDate));
            }

            criteriaQuery.select(flightRoot).where(predicates.toArray(new Predicate[0]));

            TypedQuery<Flight> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error fetching filtered flights");
        } finally {
            entityManager.close();
        }
    }
}
