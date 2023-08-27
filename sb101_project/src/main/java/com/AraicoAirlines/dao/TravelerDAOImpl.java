package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.Traveler;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;
import com.AraicoAirlines.utility.DBUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TravelerDAOImpl implements TravelerDAO {
    @Override
    public void saveTraveler(Traveler traveler) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(traveler);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SomethingWentWrongException("Error saving traveler: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}

