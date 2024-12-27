package org.example.controller;
import org.example.model.Order;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/add")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderTime(LocalDateTime.now());
        orderService.placeOrder(order);
        return "Order placed successfully.";
    }
    @GetMapping("/by-date")
    public List<Order> getOrdersByDate(@RequestParam LocalDateTime date) {
        return orderService.getOrdersByDate(date);
    }
    @GetMapping("/by-date-range")
    public List<Order> getOrdersByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return orderService.getOrdersByDateRange(startDate, endDate);
    }
    @GetMapping("/dessert-count")
    public int countDessertOrdersByDate(@RequestParam LocalDateTime date) {
        return orderService.countDessertOrdersByDate(date);
    }
    @GetMapping("/drink-count")
    public int countDrinkOrdersByDate(@RequestParam LocalDateTime date) {
        return orderService.countDrinkOrdersByDate(date);
    }
    @GetMapping("/avg-amount")
    public double getAverageOrderAmountByDate(@RequestParam LocalDateTime date) {
        return orderService.getAverageOrderAmountByDate(date);
    }
    @GetMapping("/max-amount")
    public double getMaxOrderAmountByDate(@RequestParam LocalDateTime date) {
        return orderService.getMaxOrderAmountByDate(date);
    }
    @GetMapping("/client-max-amount")
    public int getClientWithMaxOrderAmount(@RequestParam LocalDateTime date) {
        return orderService.getClientWithMaxOrderAmount(date);
    }
}