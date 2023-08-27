package com.AraicoAirlines;

import com.AraicoAirlines.dto.AdminDTO;
import com.AraicoAirlines.exceptions.NoRecordFoundException;
import com.AraicoAirlines.exceptions.SomethingWentWrongException;
import com.AraicoAirlines.services.AdminService;
import com.AraicoAirlines.services.CustomerService;
import com.AraicoAirlines.services.FlightService;
import com.AraicoAirlines.utility.DBUtils;
import com.AraicoAirlines.dto.Flight;
import com.AraicoAirlines.dao.*;
import com.AraicoAirlines.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import com.AraicoAirlines.dto.*;
import java.util.Random;
public class Main {

	public static void main(String[] args) {
	    try {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("Welcome to Araico Airlines");
	        while (true) {
	            System.out.println("Select an option:");
	            System.out.println("1. Admin");
	            System.out.println("2. Customer");
	            System.out.print("Enter your choice: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            switch (choice) {
	                case 1:
	                    adminSection(scanner);
	                    break;
	                case 2:
	                    customerSection(scanner);
	                    break;
	                default:
	                    System.out.println("Invalid choice");
	            }

	            System.out.print("Do you want to continue? (yes/no): ");
	            String continueOption = scanner.nextLine().toLowerCase();
	            if (!continueOption.equals("yes")) {
	                System.out.println("Goodbye!");
	                break;
	            }
	        }

	        scanner.close();
	        DBUtils.getEntityManager().close();
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	    }
	}
/////////////////////////-------Admin Section----------////////////////////////////////////////
    private static void adminSection(Scanner scanner) {
        try {
            AdminDAOImpl adminDAO = new AdminDAOImpl();
            AdminService adminService = new AdminService(adminDAO);

            while (true) {
                System.out.println("Please select an option:");
                System.out.println("1. New admin - signup first");
                System.out.println("2. Already have an account - login please");
                System.out.println("3. Go Back");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        adminRegistration(scanner, adminService);
                        break;
                    case 2:
                        adminLogin(scanner, adminService);
                        break;
                    case 3:
                        System.out.println("Going back to main menu...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }



    private static void adminRegistration(Scanner scanner, AdminService adminService) {
        try {
            System.out.println("New Admin Registration");
            System.out.print("Enter admin username: ");
            String username = scanner.nextLine();

            System.out.print("Enter admin password: ");
            String password = scanner.nextLine();

            adminService.registerAdmin(username, password);
            System.out.println("Admin registered successfully!");

            System.out.print("Do you want to login now? (yes/no): ");
            String loginOption = scanner.nextLine().toLowerCase();

            if (loginOption.equals("yes")) {
                adminLogin(scanner, adminService);
            } else if (loginOption.equals("no")) {
                System.out.println("Thank you for registering!");
                System.out.print("Do you want to register another admin? (yes/no): ");
                String registerAnotherOption = scanner.nextLine().toLowerCase();
                
                if (registerAnotherOption.equals("yes")) {
                    adminRegistration(scanner, adminService);
                } else {
                    System.out.println("Goodbye!");
                }
            } else {
                System.out.println("Invalid choice");
            }
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    private static void adminLogin(Scanner scanner, AdminService adminService) {
        try {
            System.out.println("Admin Login");
            System.out.print("Enter admin username: ");
            String username = scanner.nextLine();

            System.out.print("Enter admin password: ");
            String password = scanner.nextLine();

            AdminDTO admin = adminService.loginAdmin(username, password);
            if (admin != null) {
                System.out.println("Admin login successful!");
                System.out.println("Welcome to Araico Airlines "+username);
                
                FlightDAO flightDAO = new FlightDAOImpl();
                FlightService flightService = new FlightService(flightDAO);
                
                while (true) {
                    System.out.println("Select an option:");
                    System.out.println("1. Add new flight");
                    System.out.println("2. Update flight information");
                    System.out.println("3. Remove flight");
                    System.out.println("4. Get all flights");
                    System.out.println("5. Log out");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            addFlight(scanner, flightService);
                            break;
                        case 2:
                            updateFlight(scanner, flightService);
                            break;
                        case 3:
                            removeFlight(scanner, flightService);
                            break;
                        case 4:
                            getAllFlights();
                            break;
                        case 5:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }
                } 

            } else {
                System.out.println("Invalid admin credentials");
            }
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
////////////////////////////-------Flight Section------------//////////////////////////////////
    
    private static void addFlight(Scanner scanner, FlightService flightService) {
        System.out.println("Add New Flight");

        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();

        System.out.print("Enter departure city: ");
        String departureCity = scanner.nextLine();

        System.out.print("Enter destination city: ");
        String destinationCity = scanner.nextLine();

        System.out.print("Enter departure date and time (YYYY-MM-DD HH:MM): ");
        String departureDateTimeString = scanner.nextLine();
        LocalDateTime departureDateTime = LocalDateTime.parse(departureDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.print("Enter arrival date and time (YYYY-MM-DD HH:MM): ");
        String arrivalDateTimeString = scanner.nextLine();
        LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Flight newFlight = new Flight(flightNumber, departureCity, destinationCity, departureDateTime, arrivalDateTime, price);

        try {
            flightService.addFlight(newFlight);
            System.out.println("Flight added successfully!");
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void updateFlight(Scanner scanner, FlightService flightService) {
        System.out.println("Update Flight Information");
        
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();

        Flight existingFlight = fetchFlightDetailsFromDatabase(flightNumber);

        if (existingFlight == null) {
            System.out.println("Flight not found");
            return;
        }

        System.out.println("Enter new flight details:");

        System.out.print("Enter departure city: ");
        String departureCity = scanner.nextLine();
        existingFlight.setDepartureCity(departureCity);

        System.out.print("Enter destination city: ");
        String destinationCity = scanner.nextLine();
        existingFlight.setDestinationCity(destinationCity);

        System.out.print("Enter departure date and time (YYYY-MM-DD HH:MM): ");
        String departureDateTimeString = scanner.nextLine();
        LocalDateTime departureDateTime = LocalDateTime.parse(departureDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        existingFlight.setDepartureTime(departureDateTime);

        System.out.print("Enter arrival date and time (YYYY-MM-DD HH:MM): ");
        String arrivalDateTimeString = scanner.nextLine();
        LocalDateTime arrivalDateTime = LocalDateTime.parse(arrivalDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        existingFlight.setArrivalTime(arrivalDateTime);

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        existingFlight.setPrice(price);

        try {
            flightService.updateFlight(existingFlight);
            System.out.println("Flight information updated successfully!");
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    
    private static Flight fetchFlightDetailsFromDatabase(String flightNumber) {
        FlightDAO flightDAO = new FlightDAOImpl();
        return flightDAO.getFlightByNumber(flightNumber);
    }



    private static void removeFlight(Scanner scanner, FlightService flightService) {
        System.out.println("Remove Flight");
        
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        
        try {
            flightService.removeFlight(flightNumber);
            System.out.println("Flight removed successfully!");
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void getAllFlights() {
        FlightDAO flightDAO = new FlightDAOImpl();
        FlightService flightService = new FlightService(flightDAO);

        try {
            List<Flight> allFlights = flightService.getAllFlights();

            System.out.println("All Flights:");
            for (Flight flight : allFlights) {
                System.out.println(flight);
            }
        } catch (SomethingWentWrongException e) {
            System.err.println("Error fetching all flights: " + e.getMessage());
        }
    }

////////////////////////////--------Customer Section---------///////////////////////////////////
    private static void customerSection(Scanner scanner) {
        try {
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            CustomerService customerService = new CustomerService(customerDAO);

            while (true) {
                System.out.println("Please select an option:");
                System.out.println("1. New customer - signup first");
                System.out.println("2. Already have an account - login please");
                System.out.println("3. Go Back");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        customerRegistration(scanner, customerService);
                        break;
                    case 2:
                        customerLogin(scanner, customerService);
                        break;
                    case 3:
                        System.out.println("Going back to main menu...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void customerRegistration(Scanner scanner, CustomerService customerService) {
        try {
            System.out.println("New Customer Registration");
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine();
            
            String customerId = generateRandomCustomerId();
            
            System.out.print("Enter date of birth (YYYY-MM-DD): ");
            String dobString = scanner.nextLine();
            LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            System.out.print("Enter gender (MALE/FEMALE/OTHERS): ");
            String gender = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter mobile number: ");
            String mobileNumber = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            Customers newCustomer = new Customers(customerId, dob, email, gender, mobileNumber, name, password);

            customerService.registerCustomer(newCustomer);
            System.out.println("Customer registered successfully!");

            System.out.print("Do you want to login now? (yes/no): ");
            String loginOption = scanner.nextLine().toLowerCase();

            if (loginOption.equals("yes")) {
                customerLogin(scanner, customerService);
            } else if (loginOption.equals("no")) {
                System.out.println("Thank you for registering!");
            } else {
                System.out.println("Invalid choice");
            }
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String generateRandomCustomerId() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int alphabetLength = alphabet.length();

        StringBuilder customerIdBuilder = new StringBuilder();

        customerIdBuilder.append(alphabet.charAt(random.nextInt(alphabetLength)));
        customerIdBuilder.append(String.format("%03d", random.nextInt(1000)));
        customerIdBuilder.append(alphabet.charAt(random.nextInt(alphabetLength)));

        return customerIdBuilder.toString();
    }
    
    private static void customerLogin(Scanner scanner, CustomerService customerService) {
        try {
            System.out.println("Customer Login");
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            Customers authenticatedCustomer = customerService.authenticateCustomer(email, password);
            if (authenticatedCustomer != null) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid email or password");
            }
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
