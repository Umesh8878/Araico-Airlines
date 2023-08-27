package com.AraicoAirlines.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBUtils {
	static EntityManagerFactory emf = null;
	
	static {
		emf = Persistence.createEntityManagerFactory("Araico_Airlines");
	}
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
