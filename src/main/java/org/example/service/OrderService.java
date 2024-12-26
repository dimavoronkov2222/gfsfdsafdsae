package org.example.service;
import org.example.dao.OrderDao;
import org.example.model.Order;
import java.sql.SQLException;
public class OrderService {
    private final OrderDao orderDao;
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    public void placeOrder(Order order) {
        if (order.getClientId() <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        try {
            orderDao.insertOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to place order: " + e.getMessage(), e);
        }
    }
}