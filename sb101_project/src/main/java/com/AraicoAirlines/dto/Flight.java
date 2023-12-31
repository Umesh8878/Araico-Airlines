package com.AraicoAirlines.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Flight {
    @Id
    @Column(name = "flight_number")
    private String flightNumber;
    
    @Column(name = "departure_city")
    private String departureCity;
    
    @Column(name = "destination_city")
    private String destinationCity;
    
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    
    private double price;

    public Flight() {
        super();
    }

	public Flight(String flightNumber, String departureCity, String destinationCity,
                  LocalDateTime departureTime, LocalDateTime arrivalTime, double price) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    // Getters and setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	@Override 
	public String toString() {
		return "Flight [flightNumber=" + flightNumber + ", departureCity=" + departureCity + ", destinationCity="
				+ destinationCity + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", price="
				+ price + "]";
	}
}
