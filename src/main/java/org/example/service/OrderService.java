package org.example.service;
import org.example.dao.OrderDao;
import org.example.model.Order;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderService {
    private final OrderDao orderDao;
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    public void placeOrder(Order order) {
        orderDao.insertOrder(order);
    }
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }
    public void removeOrder(int orderId) {
        orderDao.deleteOrder(orderId);
    }
    public Order getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }
    public List<Order> getOrdersByDate(LocalDateTime date) {
        return orderDao.getOrdersByDate(date);
    }
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDao.getOrdersByDateRange(startDate, endDate);
    }
    public int countDessertOrdersByDate(LocalDateTime date) {
        return orderDao.countDessertOrdersByDate(date);
    }
    public int countDrinkOrdersByDate(LocalDateTime date) {
        return orderDao.countDrinkOrdersByDate(date);
    }
    public double getAverageOrderAmountByDate(LocalDateTime date) {
        return orderDao.getAverageOrderAmountByDate(date);
    }
    public double getMaxOrderAmountByDate(LocalDateTime date) {
        return orderDao.getMaxOrderAmountByDate(date);
    }
    public int getClientWithMaxOrderAmount(LocalDateTime date) {
        return orderDao.getClientWithMaxOrderAmount(date);
    }
    public List<Order> getOrdersBySpecificDate(LocalDateTime date) {
        return orderDao.getOrdersBySpecificDate(date);
    }
    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDao.getOrdersInDateRange(startDate, endDate);
    }
    public int countDessertsInOrdersOnDate(LocalDateTime date) {
        return orderDao.countDessertsInOrdersOnDate(date);
    }
    public int countDrinksInOrdersOnDate(LocalDateTime date) {
        return orderDao.countDrinksInOrdersOnDate(date);
    }
}