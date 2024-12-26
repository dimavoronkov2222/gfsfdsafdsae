package org.example.dao;
import org.example.model.Order;
import java.sql.SQLException;
import java.util.List;
public interface OrderDao {
    void insertOrder(Order order) throws SQLException;
    void updateOrder(Order order) throws SQLException;
    void deleteOrder(int orderId) throws SQLException;
    Order getOrderById(int orderId) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
}
