package org.example.controller;
import org.example.service.OrderService;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.example.model.Order;
import java.sql.SQLException;
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    public void handleInsertOrder() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter client ID:");
            int clientId = scanner.nextInt();
            System.out.println("Enter drink ID:");
            int drinkId = scanner.nextInt();
            Order order = new Order(clientId, drinkId, LocalDateTime.now());
            orderService.placeOrder(order);
            System.out.println("Order placed successfully.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    public void handleViewOrdersByDate(LocalDateTime date) {
        try {
            List<Order> orders = orderService.getOrdersByDate(date);
            orders.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }
    }
    public void handleViewOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);
            orders.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error fetching orders by date range: " + e.getMessage());
        }
    }
    public void handleCountDessertOrdersByDate(LocalDateTime date) {
        try {
            int count = orderService.countDessertOrdersByDate(date);
            System.out.println("Number of dessert orders: " + count);
        } catch (SQLException e) {
            System.out.println("Error fetching dessert orders count: " + e.getMessage());
        }
    }
    public void handleCountDrinkOrdersByDate(LocalDateTime date) {
        try {
            int count = orderService.countDrinkOrdersByDate(date);
            System.out.println("Number of drink orders: " + count);
        } catch (SQLException e) {
            System.out.println("Error fetching drink orders count: " + e.getMessage());
        }
    }
    public void handleAverageOrderAmountByDate(LocalDateTime date) {
        try {
            double avgAmount = orderService.getAverageOrderAmountByDate(date);
            System.out.println("Average order amount: " + avgAmount);
        } catch (SQLException e) {
            System.out.println("Error fetching average order amount: " + e.getMessage());
        }
    }
    public void handleMaxOrderAmountByDate(LocalDateTime date) {
        try {
            double maxAmount = orderService.getMaxOrderAmountByDate(date);
            System.out.println("Max order amount: " + maxAmount);
        } catch (SQLException e) {
            System.out.println("Error fetching max order amount: " + e.getMessage());
        }
    }
    public void handleClientWithMaxOrderAmount(LocalDateTime date) {
        try {
            int clientId = orderService.getClientWithMaxOrderAmount(date);
            System.out.println("Client with max order amount: " + clientId);
        } catch (SQLException e) {
            System.out.println("Error fetching client with max order amount: " + e.getMessage());
        }
    }
}
