package org.example.controller;
import org.example.service.OrderService;
import java.time.LocalDateTime;
import java.util.Scanner;
import org.example.model.Order;
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    public void handleInsertOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter client ID:");
        int clientId = scanner.nextInt();
        System.out.println("Enter drink ID:");
        int drinkId = scanner.nextInt();
        Order order = new Order(clientId, drinkId, LocalDateTime.now());
        orderService.placeOrder(order);
        System.out.println("Order placed successfully.");
    }
}
