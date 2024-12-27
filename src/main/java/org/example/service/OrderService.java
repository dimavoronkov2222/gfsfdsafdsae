package org.example.service;
import org.example.dao.OrderDao;
import org.example.model.Order;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
public class OrderService {
    private final OrderDao orderDao;
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    public void placeOrder(Order order) throws SQLException {
        orderDao.insertOrder(order);
    }
    public void updateOrder(Order order) throws SQLException {
        orderDao.updateOrder(order);
    }
    public void removeOrder(int orderId) throws SQLException {
        orderDao.deleteOrder(orderId);
    }
    public Order getOrderById(int orderId) throws SQLException {
        return orderDao.getOrderById(orderId);
    }
    public List<Order> getAllOrders() throws SQLException {
        return orderDao.getAllOrders();
    }
    public List<Order> getOrdersByDate(LocalDateTime date) throws SQLException {
        return orderDao.getOrdersByDate(date);
    }
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        return orderDao.getOrdersByDateRange(startDate, endDate);
    }
    public int countDessertOrdersByDate(LocalDateTime date) throws SQLException {
        return orderDao.countDessertOrdersByDate(date);
    }
    public int countDrinkOrdersByDate(LocalDateTime date) throws SQLException {
        return orderDao.countDrinkOrdersByDate(date);
    }
    public double getAverageOrderAmountByDate(LocalDateTime date) throws SQLException {
        return orderDao.getAverageOrderAmountByDate(date);
    }
    public double getMaxOrderAmountByDate(LocalDateTime date) throws SQLException {
        return orderDao.getMaxOrderAmountByDate(date);
    }
    public int getClientWithMaxOrderAmount(LocalDateTime date) throws SQLException {
        return orderDao.getClientWithMaxOrderAmount(date);
    }
    public List<Order> getOrdersBySpecificDate(LocalDateTime date) throws SQLException {
        return orderDao.getOrdersBySpecificDate(date);
    }
    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        return orderDao.getOrdersInDateRange(startDate, endDate);
    }
    public int countDessertsInOrdersOnDate(LocalDateTime date) throws SQLException {
        return orderDao.countDessertsInOrdersOnDate(date);
    }
    public int countDrinksInOrdersOnDate(LocalDateTime date) throws SQLException {
        return orderDao.countDrinksInOrdersOnDate(date);
    }
}