package com.AraicoAirlines.dao;

import com.AraicoAirlines.dto.AdminDTO;

public interface AdminDAO {
	void registerAdmin(AdminDTO admin);
    AdminDTO getAdminByUsername(String username);
    AdminDTO getAdminByUsernameAndPassword(String username, String password);
}
