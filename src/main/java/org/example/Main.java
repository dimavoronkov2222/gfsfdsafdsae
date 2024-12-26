package org.example;
import org.example.dao.*;
import org.example.service.OrderService;
import org.example.controller.OrderController;
import java.sql.*;
import java.util.Scanner;
public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/CoffeeShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8289/00/5654";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            OrderDao orderDao = new OrderDaoImpl(connection);
            OrderService orderService = new OrderService(orderDao);
            OrderController orderController = new OrderController(orderService);
            boolean running = true;
            Scanner scanner = new Scanner(System.in);
            while (running) {
                System.out.println("1. Place Order");
                System.out.println("2. Exit");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> orderController.handleInsertOrder();
                    case 2 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}