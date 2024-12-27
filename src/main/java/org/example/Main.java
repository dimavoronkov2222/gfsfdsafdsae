package org.example;
import org.example.controller.CustomerController;
import org.example.dao.CustomerDao;
import org.example.dao.CustomerDaoImpl;
import org.example.service.CustomerService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/CoffeeShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8289/00/5654";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            CustomerDao customerDao = new CustomerDaoImpl(connection);
            CustomerService customerService = new CustomerService(customerDao);
            CustomerController customerController = new CustomerController(customerService);
            boolean running = true;
            Scanner scanner = new Scanner(System.in);
            while (running) {
                displayMenu();
                int choice = getUserChoice(scanner);
                switch (choice) {
                    case 1 -> customerController.handleViewAllCustomers();
                    case 2 -> customerController.handleGetMinDiscount();
                    case 3 -> customerController.handleGetMaxDiscount();
                    case 4 -> customerController.handleGetAvgDiscount();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice, please enter a valid number.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void displayMenu() {
        System.out.println("1. View All Customers");
        System.out.println("2. Get Minimum Discount");
        System.out.println("3. Get Maximum Discount");
        System.out.println("4. Get Average Discount");
        System.out.println("5. Exit");
    }
    private static int getUserChoice(Scanner scanner) {
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return choice;
    }
}