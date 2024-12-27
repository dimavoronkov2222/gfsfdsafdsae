package org.example.dao;
import org.example.model.Order;
import java.sql.*;
import java.time.LocalDateTime;
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
    @Override
    public List<Order> getOrdersByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT * FROM Orders WHERE DATE(order_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("drink_id"),
                        rs.getInt("dessert_id"),
                        rs.getTimestamp("order_time").toLocalDateTime()
                ));
            }
            return orders;
        }
    }
    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String query = "SELECT * FROM Orders WHERE order_time BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("drink_id"),
                        rs.getInt("dessert_id"),
                        rs.getTimestamp("order_time").toLocalDateTime()
                ));
            }
            return orders;
        }
    }
    @Override
    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String query = "SELECT * FROM Orders WHERE order_time BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("drink_id"),
                        rs.getInt("dessert_id"),
                        rs.getTimestamp("order_time").toLocalDateTime()
                ));
            }
            return orders;
        }
    }
    @Override
    public List<Order> getOrdersBySpecificDate(LocalDateTime date) throws SQLException {
        String query = "SELECT * FROM Orders WHERE DATE(order_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("drink_id"),
                        rs.getInt("dessert_id"),
                        rs.getTimestamp("order_time").toLocalDateTime()
                ));
            }
            return orders;
        }
    }
    @Override
    public int countDessertOrdersByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND dessert_id IS NOT NULL";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    @Override
    public int countDrinkOrdersByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND drink_id IS NOT NULL";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    @Override
    public double getAverageOrderAmountByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT AVG(amount) FROM Orders WHERE DATE(order_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }
    @Override
    public double getMaxOrderAmountByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT MAX(amount) FROM Orders WHERE DATE(order_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }
    @Override
    public int getClientWithMaxOrderAmount(LocalDateTime date) throws SQLException {
        String query = "SELECT client_id FROM Orders WHERE DATE(order_time) = ? ORDER BY amount DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    @Override
    public int countDessertsInOrdersOnDate(LocalDateTime date) throws SQLException {
        String query = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND dessert_id IS NOT NULL";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    @Override
    public int countDrinksInOrdersOnDate(LocalDateTime date) throws SQLException {
        String query = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND drink_id IS NOT NULL";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}