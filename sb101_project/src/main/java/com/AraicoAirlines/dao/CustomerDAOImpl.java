package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.Customers;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;
import com.AraicoAirlines.utility.DBUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CustomerDAOImpl implements CustomerDAO {
	
	
    @Override
    public void registerCustomer(Customers customer) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SomethingWentWrongException("Error registering customer: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Customers authenticateCustomer(String email, String password) throws SomethingWentWrongException {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            TypedQuery<Customers> query = entityManager.createQuery("SELECT c FROM Customers c WHERE c.email = :email AND c.password = :password", Customers.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
}
