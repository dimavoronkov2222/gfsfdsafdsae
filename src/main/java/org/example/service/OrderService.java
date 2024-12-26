package org.example.service;
import org.example.dao.OrderDao;
import org.example.model.Order;
import java.sql.SQLException;
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
}