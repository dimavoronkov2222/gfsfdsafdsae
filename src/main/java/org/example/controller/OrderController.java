package org.example.controller;
import org.example.service.OrderService;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
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
}
