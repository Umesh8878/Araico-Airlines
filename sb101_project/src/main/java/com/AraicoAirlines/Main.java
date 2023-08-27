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

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            	System.out.println("Login successful! Welcome, " + authenticatedCustomer.getName() + "to Araico Airlines.");
            	while (true) {
                    System.out.println("Please select an option:");
                    System.out.println("1. One Way Trip");
                    System.out.println("2. Round Trip");
                    System.out.println("3. Book a Trip");
                    System.out.println("4. Filter Flights");
                    System.out.println("5. View Bookings");
                    System.out.println("6. Update Profile");
                    System.out.println("7. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    FlightService flightService = new FlightService(new FlightDAOImpl());
                    
                    switch (choice) {
                        case 1:
						
						oneWayTrip(scanner, authenticatedCustomer, flightService);
                            break;
                        case 2:
                        oneWayTrip(scanner, authenticatedCustomer, flightService);
                            break;
                        case 4:
                            filterFlights(scanner, flightService);
                            break;
                        case 3:
                            bookTrip(scanner, flightService, authenticatedCustomer);
                          break;
                        case 5:
//                            viewBookings();
                            break;
                        case 7:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }

        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void oneWayTrip(Scanner scanner, Customers authenticatedCustomer, FlightService flightService) {
        try {
            System.out.println("One Way Trip");

            System.out.print("Enter Departure City: ");
            String departureCity = scanner.nextLine();

            System.out.print("Enter Destination City: ");
            String destinationCity = scanner.nextLine();

            System.out.print("Enter Journey Date (YYYY-MM-DD): ");
            String journeyDateString = scanner.nextLine();
            LocalDate journeyDate = LocalDate.parse(journeyDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<Flight> availableFlights = flightService.getAvailableFlights(departureCity, destinationCity, journeyDate);

            if (availableFlights.isEmpty()) {
                System.out.println("No available flights for the given criteria.");
            } else {
                System.out.println("Available Flights:");
                for (Flight flight : availableFlights) {
                    System.out.println(flight);
                }
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD format.");
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void filterFlights(Scanner scanner, FlightService flightService) {
        try {
            while (true) {
                System.out.println("Filter Flights");
                System.out.println("Select a filter option:");
                System.out.println("1. Filter by Date");
                System.out.println("2. Filter by Departure City");
                System.out.println("3. Filter by Destination City");
                System.out.println("4. Filter by Price Range");
                System.out.println("5. Filter by All");
                System.out.println("6. Go Back");
                System.out.print("Enter your choice: ");
                int filterChoice = scanner.nextInt();
                scanner.nextLine();
                
                switch (filterChoice) {
                    case 1:
                        filterByDate(scanner, flightService);
                        break;
                    case 2:
                        filterByDepartureCity(scanner, flightService);
                        break;
                    case 3:
                        filterByDestinationCity(scanner, flightService);
                        break;
                    case 4:
                        filterByPriceRange(scanner, flightService);
                        break;
                    case 5:
                        filterByAll(scanner, flightService);
                        break;
                    case 6:
                        System.out.println("Going back...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    
    private static void filterByDate(Scanner scanner, FlightService flightService) {
        try {
            System.out.print("Enter departure date (YYYY-MM-DD): ");
            String departureDateString = scanner.nextLine();
            LocalDate departureDate = LocalDate.parse(departureDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            List<Flight> flights = flightService.getFlightsByDepartureDate(departureDate);
            
            if (flights.isEmpty()) {
                System.out.println("No flights available for the given date.");
            } else {
                System.out.println("Flights on " + departureDateString + ":");
                for (Flight flight : flights) {
                    System.out.println(flight);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void filterByDepartureCity(Scanner scanner, FlightService flightService) {
        try {
            System.out.print("Enter departure city: ");
            String departureCity = scanner.nextLine();
            
            List<Flight> flights = flightService.getFlightsByDepartureCity(departureCity);
            
            if (flights.isEmpty()) {
                System.out.println("No flights available from the given departure city.");
            } else {
                System.out.println("Flights from " + departureCity + ":");
                for (Flight flight : flights) {
                    System.out.println(flight);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void filterByDestinationCity(Scanner scanner, FlightService flightService) {
        try {
            System.out.print("Enter destination city: ");
            String destinationCity = scanner.nextLine();
            
            List<Flight> flights = flightService.getFlightsByDestinationCity(destinationCity);
            
            if (flights.isEmpty()) {
                System.out.println("No flights available to the given destination city.");
            } else {
                System.out.println("Flights to " + destinationCity + ":");
                for (Flight flight : flights) {
                    System.out.println(flight);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void filterByPriceRange(Scanner scanner, FlightService flightService) {
        try {
            System.out.print("Enter minimum price: ");
            double minPrice = scanner.nextDouble();
            System.out.print("Enter maximum price: ");
            double maxPrice = scanner.nextDouble();
            
            List<Flight> flights = flightService.getFlightsByPriceRange(minPrice, maxPrice);
            
            if (flights.isEmpty()) {
                System.out.println("No flights available within the given price range.");
            } else {
                System.out.println("Flights within price range [" + minPrice + " - " + maxPrice + "]:");
                for (Flight flight : flights) {
                    System.out.println(flight);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void filterByAll(Scanner scanner, FlightService flightService) {
        try {
            System.out.println("Filter Flights by All Criteria");
            
            System.out.print("Enter departure city (leave empty to skip): ");
            String departureCity = scanner.nextLine();
            
            System.out.print("Enter destination city (leave empty to skip): ");
            String destinationCity = scanner.nextLine();
            
            double minPrice = -1;
            double maxPrice = -1;
            try {
                System.out.print("Enter minimum price (leave empty to skip): ");
                String minPriceStr = scanner.nextLine();
                if (!minPriceStr.isEmpty()) {
                    minPrice = Double.parseDouble(minPriceStr);
                }
                
                System.out.print("Enter maximum price (leave empty to skip): ");
                String maxPriceStr = scanner.nextLine();
                if (!maxPriceStr.isEmpty()) {
                    maxPrice = Double.parseDouble(maxPriceStr);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid price format. Please enter a valid number.");
                return;
            }
            
            LocalDate departureDate = null;
            System.out.print("Enter departure date (YYYY-MM-DD, leave empty to skip): ");
            String departureDateString = scanner.nextLine();
            if (!departureDateString.isEmpty()) {
                try {
                    departureDate = LocalDate.parse(departureDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
                    return;
                }
            }

            List<Flight> filteredFlights = flightService.getFilteredFlights(departureCity, destinationCity, minPrice, maxPrice, departureDate);

            if (filteredFlights.isEmpty()) {
                System.out.println("No flights found matching the criteria.");
            } else {
                System.out.println("Filtered Flights:");
                for (Flight flight : filteredFlights) {
                    System.out.println(flight);
                }
            }
        } catch (SomethingWentWrongException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void bookTrip(Scanner scanner, FlightService flightService, Customers loggedInCustomer) throws SomethingWentWrongException {
        List<Flight> availableFlights = flightService.getAllFlights();

        System.out.println("Available Flights:");
        for (int i = 0; i < availableFlights.size(); i++) {
            System.out.println((i + 1) + ". " + availableFlights.get(i));
        }

        System.out.print("Select a flight by entering its number: ");
        String flightNumberChoice = scanner.next();
        scanner.nextLine();

        if (flightService.doesFlightExist(flightNumberChoice)) {
            Flight selectedFlight = flightService.getFlightByNumber(flightNumberChoice);

            System.out.println("Select class:");
            System.out.println("1. Economy class");
            System.out.println("2. Business class");
            System.out.print("Enter your choice: ");
            int classChoice = scanner.nextInt();
            scanner.nextLine();

            double classPriceMultiplier = (classChoice == 2) ? 1.35 : 1.0;

            System.out.print("Enter the number of travelers: ");
            int numTravelers = scanner.nextInt();
            scanner.nextLine();

            double totalFare = calculateTotalFare(selectedFlight, classPriceMultiplier, numTravelers);

            List<Traveler> travelers = new ArrayList<>();
            for (int i = 0; i < numTravelers; i++) {
                System.out.print("Enter traveler " + (i + 1) + " name: ");
                String travelerName = scanner.nextLine();
                System.out.print("Enter traveler " + (i + 1) + " age: ");
                int travelerAge = scanner.nextInt();
                scanner.nextLine();

                Traveler traveler = new Traveler(travelerName, travelerAge);
                travelers.add(traveler);
            }

            System.out.println("Booking Details:");
            System.out.println("Flight: " + selectedFlight);
            System.out.println("Class: " + ((classChoice == 2) ? "Business" : "Economy"));
            System.out.println("Travelers:");
            for (Traveler traveler : travelers) {
                System.out.println("Name: " + traveler.getName() + ", Age: " + traveler.getAge());
            }
            System.out.println("Total Fare: " + totalFare);

            System.out.print("Do you want to Pay Now (YES/NO): ");
            String payChoice = scanner.nextLine();

            if (payChoice.equalsIgnoreCase("YES")) {
                System.out.println("Payment Options:");
                // Payment options menu
                System.out.print("Select a payment option: ");
                int paymentOption = scanner.nextInt();
                scanner.nextLine();

                boolean paymentSuccessful = processPayment(paymentOption, scanner);

                if (paymentSuccessful) {
                    System.out.println("Payment successful! Your booking is confirmed.");
                    System.out.println("Thank you for choosing Araico Airlines. Happy Journey!");
                } else {
                    System.out.println("Payment failed. Please check your payment information and try again.");
                }
            } else {
                System.out.println("Payment not completed. Your booking is not confirmed.");
            }
        } else {
            System.out.println("Invalid flight selection.");
        }
    }


    
    private static double calculateTotalFare(Flight selectedFlight, double classPriceMultiplier, int numTravelers) {
        return selectedFlight.getPrice() * classPriceMultiplier * numTravelers;
    }

    private static boolean processPayment(int paymentOption, Scanner scanner) {
        boolean paymentSuccessful = false;

        switch (paymentOption) {
            case 1:
            case 2:
                paymentSuccessful = cardPayment(scanner);
                break;
            case 3:
                paymentSuccessful = upiPayment("@upi", scanner);
                break;
            case 4:
                paymentSuccessful = upiPayment("@paytm", scanner);
                break;
            case 5:
                paymentSuccessful = upiPayment("@phonepe", scanner);
                break;
            case 6:
                paymentSuccessful = upiPayment("@gpay", scanner);
                break;
            default:
                System.out.println("Invalid payment option.");
        }

        return paymentSuccessful;
    }

    private static boolean cardPayment(Scanner scanner) {
        System.out.print("Enter 16-digit card number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Enter expiry date (yy/mm): ");
        String expiryDate = scanner.nextLine();

        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();

        String otp = generateOTP();
        System.out.println("OTP has been sent to your registered mobile number.");
        System.out.print("Enter the OTP: ");
        String enteredOTP = scanner.nextLine();

        return otp.equals(enteredOTP);
    }

    private static boolean upiPayment(String upiSuffix, Scanner scanner) {
        System.out.print("Enter UPI ID" + upiSuffix + ": ");
        String upiId = scanner.nextLine();

        String otp = generateOTP();
        System.out.println("OTP has been sent to your registered mobile number.");
        System.out.print("Enter the OTP: ");
        String enteredOTP = scanner.nextLine();

        return otp.equals(enteredOTP);
    }

    private static String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
