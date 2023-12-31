package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.AdminDTO;
import com.AraicoAirlines.utility.DBUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public void registerAdmin(AdminDTO admin) {
        EntityManager entityManager = DBUtils.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(admin);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AdminDTO getAdminByUsername(String username) {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM AdminDTO a WHERE a.username = :username", AdminDTO.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
    
    @Override
    public AdminDTO getAdminByUsernameAndPassword(String username, String password) {
        EntityManager entityManager = DBUtils.getEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM AdminDTO a WHERE a.username = :username AND a.password = :password", AdminDTO.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
}
