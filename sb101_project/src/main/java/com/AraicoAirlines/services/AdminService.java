package com.AraicoAirlines.services;

import com.AraicoAirlines.dao.AdminDAO;
import com.AraicoAirlines.dto.AdminDTO;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;

public class AdminService {
    private final AdminDAO adminDAO;

    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public void registerAdmin(String username, String password) throws SomethingWentWrongException {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty");
        }

        AdminDTO existingAdmin = adminDAO.getAdminByUsername(username);
        if (existingAdmin != null) {
            throw new SomethingWentWrongException("Admin with the given username already exists");
        }

        AdminDTO admin = new AdminDTO();
        admin.setUsername(username);
        admin.setPassword(password);

        try {
            adminDAO.registerAdmin(admin);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error occurred while registering admin");
        }
    }

    public void registerAdmin(AdminDTO newAdmin) throws SomethingWentWrongException {
        if (newAdmin == null || newAdmin.getUsername() == null || newAdmin.getUsername().trim().isEmpty() ||
            newAdmin.getPassword() == null || newAdmin.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid admin data");
        }

        AdminDTO existingAdmin = adminDAO.getAdminByUsername(newAdmin.getUsername());
        if (existingAdmin != null) {
            throw new SomethingWentWrongException("Admin with the given username already exists");
        }

        try {
            adminDAO.registerAdmin(newAdmin);
        } catch (Exception e) {
            throw new SomethingWentWrongException("Error occurred while registering admin");
        }
    }
    
    public AdminDTO loginAdmin(String username, String password) throws SomethingWentWrongException {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty");
        }
        AdminDTO admin = adminDAO.getAdminByUsernameAndPassword(username, password);

        if (admin == null) {
            throw new SomethingWentWrongException("Invalid admin credentials");
        }

        return admin;
    }

}
