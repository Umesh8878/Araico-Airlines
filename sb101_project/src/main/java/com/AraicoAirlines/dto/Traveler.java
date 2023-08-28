package com.AraicoAirlines.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Traveler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    
	public Traveler(String travelerName, int travelerAge) {
		super();
		this.name = travelerName;
		this.age = travelerAge;
	}

//	public Traveler(Long id, String name, int age) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.age = age;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Traveler [id=" + id + ", name=" + name + ", age=" + age + ", flight=" + flight + ", departureDate="
				+ departureDate + "]";
	}
	
	@ManyToOne
    @JoinColumn(name = "flight_number")
    private Flight flight;

    private LocalDate departureDate;

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public Traveler(Long id, String name, int age, Flight flight, LocalDate departureDate) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.flight = flight;
		this.departureDate = departureDate;
	}
    
    
}
