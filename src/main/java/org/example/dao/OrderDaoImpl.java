package org.example.dao;
import org.example.model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class OrderDaoImpl implements OrderDao {
    private final Connection connection;
    public OrderDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void insertOrder(Order order) throws SQLException {
        String insertOrderSQL = "INSERT INTO Orders (client_id, drink_id, dessert_id, order_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertOrderSQL)) {
            ps.setInt(1, order.getClientId());
            ps.setInt(2, order.getDrinkId());
            ps.setInt(3, order.getDessertId());
            ps.setTimestamp(4, Timestamp.valueOf(order.getOrderTime()));
            ps.executeUpdate();
            System.out.println("Order inserted successfully.");
        }
    }
    @Override
    public void updateOrder(Order order) throws SQLException {
        String updateOrderSQL = "UPDATE Orders SET client_id = ?, drink_id = ?, dessert_id = ?, order_time = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateOrderSQL)) {
            ps.setInt(1, order.getClientId());
            ps.setInt(2, order.getDrinkId());
            ps.setInt(3, order.getDessertId());
            ps.setTimestamp(4, Timestamp.valueOf(order.getOrderTime()));
            ps.setInt(5, order.getId());
            ps.executeUpdate();
            System.out.println("Order updated successfully.");
        }
    }
    @Override
    public void deleteOrder(int orderId) throws SQLException {
        String deleteOrderSQL = "DELETE FROM Orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteOrderSQL)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
            System.out.println("Order deleted successfully.");
        }
    }
    @Override
    public Order getOrderById(int orderId) throws SQLException {
        String selectOrderSQL = "SELECT * FROM Orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectOrderSQL)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int clientId = rs.getInt("client_id");
                    int drinkId = rs.getInt("drink_id");
                    int dessertId = rs.getInt("dessert_id");
                    Timestamp orderTime = rs.getTimestamp("order_time");
                    return new Order(orderId, clientId, drinkId, dessertId, orderTime.toLocalDateTime());
                }
            }
        }
        return null;
    }
    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String selectAllOrdersSQL = "SELECT * FROM Orders";
        try (PreparedStatement ps = connection.prepareStatement(selectAllOrdersSQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                int drinkId = rs.getInt("drink_id");
                int dessertId = rs.getInt("dessert_id");
                Timestamp orderTime = rs.getTimestamp("order_time");
                orders.add(new Order(id, clientId, drinkId, dessertId, orderTime.toLocalDateTime()));
            }
        }
        return orders;
    }
}