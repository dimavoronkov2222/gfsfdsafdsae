package org.example.dao;
import org.example.model.Order;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
public interface OrderDao {
    void insertOrder(Order order) throws SQLException;
    void updateOrder(Order order) throws SQLException;
    void deleteOrder(int orderId) throws SQLException;
    Order getOrderById(int orderId) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
    List<Order> getOrdersByDate(LocalDateTime date) throws SQLException;
    List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
    int countDessertOrdersByDate(LocalDateTime date) throws SQLException;
    int countDrinkOrdersByDate(LocalDateTime date) throws SQLException;
    double getAverageOrderAmountByDate(LocalDateTime date) throws SQLException;
    double getMaxOrderAmountByDate(LocalDateTime date) throws SQLException;
    int getClientWithMaxOrderAmount(LocalDateTime date) throws SQLException;
    List<Order> getOrdersBySpecificDate(LocalDateTime date) throws SQLException;
    List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
    int countDessertsInOrdersOnDate(LocalDateTime date) throws SQLException;
    int countDrinksInOrdersOnDate(LocalDateTime date) throws SQLException;
}


