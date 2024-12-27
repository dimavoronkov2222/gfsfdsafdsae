package org.example.dao;
import org.example.model.Order;
import java.time.LocalDateTime;
import java.util.List;
public interface OrderDao {
    void insertOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    List<Order> getOrdersByDate(LocalDateTime date);
    List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    int countDessertOrdersByDate(LocalDateTime date);
    int countDrinkOrdersByDate(LocalDateTime date);
    double getAverageOrderAmountByDate(LocalDateTime date);
    double getMaxOrderAmountByDate(LocalDateTime date);
    int getClientWithMaxOrderAmount(LocalDateTime date);
    List<Order> getOrdersBySpecificDate(LocalDateTime date);
    List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate);
    int countDessertsInOrdersOnDate(LocalDateTime date);
    int countDrinksInOrdersOnDate(LocalDateTime date);
}