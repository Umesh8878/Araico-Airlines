package com.AraicoAirlines.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customers {

    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public Customers() {
		super();
	}

	public Customers(String customerId, LocalDate dob, String email, String gender, String mobileNumber, String name, String password) {
        this.customerId = customerId;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.password = password;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customers [customerId=" + customerId + ", dob=" + dob + ", email=" + email + ", gender=" + gender
				+ ", mobileNumber=" + mobileNumber + ", name=" + name + ", password=" + password + "]";
	}
    
}
