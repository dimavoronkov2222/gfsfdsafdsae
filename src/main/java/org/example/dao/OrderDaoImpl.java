package org.example.dao;
import org.example.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Order> orderRowMapper = (rs, rowNum) -> new Order(
            rs.getInt("id"),
            rs.getInt("client_id"),
            rs.getInt("drink_id"),
            rs.getInt("dessert_id"),
            rs.getTimestamp("order_time").toLocalDateTime()
    );
    @Override
    public void insertOrder(Order order) {
        String sql = "INSERT INTO Orders (client_id, drink_id, dessert_id, order_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getClientId(), order.getDrinkId(), order.getDessertId(), Timestamp.valueOf(order.getOrderTime()));
    }
    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE Orders SET client_id = ?, drink_id = ?, dessert_id = ?, order_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, order.getClientId(), order.getDrinkId(), order.getDessertId(), Timestamp.valueOf(order.getOrderTime()), order.getId());
    }
    @Override
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM Orders WHERE id = ?";
        jdbcTemplate.update(sql, orderId);
    }
    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, orderRowMapper, orderId);
    }
    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM Orders";
        return jdbcTemplate.query(sql, orderRowMapper);
    }
    @Override
    public List<Order> getOrdersByDate(LocalDateTime date) {
        String sql = "SELECT * FROM Orders WHERE DATE(order_time) = ?";
        return jdbcTemplate.query(sql, orderRowMapper, Timestamp.valueOf(date));
    }
    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM Orders WHERE order_time BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, orderRowMapper, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }
    @Override
    public int countDessertOrdersByDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND dessert_id IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class, Timestamp.valueOf(date));
    }
    @Override
    public int countDrinkOrdersByDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND drink_id IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class, Timestamp.valueOf(date));
    }
    @Override
    public double getAverageOrderAmountByDate(LocalDateTime date) {
        String sql = "SELECT AVG(amount) FROM Orders WHERE DATE(order_time) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, Timestamp.valueOf(date));
    }
    @Override
    public double getMaxOrderAmountByDate(LocalDateTime date) {
        String sql = "SELECT MAX(amount) FROM Orders WHERE DATE(order_time) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, Timestamp.valueOf(date));
    }
    @Override
    public int getClientWithMaxOrderAmount(LocalDateTime date) {
        String sql = "SELECT client_id FROM Orders WHERE DATE(order_time) = ? ORDER BY amount DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, Timestamp.valueOf(date));
    }
    @Override
    public List<Order> getOrdersBySpecificDate(LocalDateTime date) {
        String sql = "SELECT * FROM Orders WHERE DATE(order_time) = ?";
        return jdbcTemplate.query(sql, orderRowMapper, Timestamp.valueOf(date));
    }
    @Override
    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM Orders WHERE order_time BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, orderRowMapper, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }
    @Override
    public int countDessertsInOrdersOnDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND dessert_id IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class, Timestamp.valueOf(date));
    }
    @Override
    public int countDrinksInOrdersOnDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE DATE(order_time) = ? AND drink_id IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class, Timestamp.valueOf(date));
    }
}