package com.AraicoAirlines.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	
	public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Araico_Airlines";
        String username = "root";
        String password = "root";

        return DriverManager.getConnection(url, username, password);
    }
}
