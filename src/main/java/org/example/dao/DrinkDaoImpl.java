package org.example.dao;
import org.example.model.Drink;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public class DrinkDaoImpl implements DrinkDao {
    private final JdbcTemplate jdbcTemplate;
    public DrinkDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Drink> drinkRowMapper = (rs, rowNum) -> new Drink(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("drink_type"),
            rs.getDouble("price"),
            rs.getTimestamp("drink_time").toLocalDateTime(),
            rs.getString("name_other")
    );
    @Override
    public void addDrink(Drink drink) {
        String sql = "INSERT INTO Drinks (name, drink_type, price, drink_time, name_other) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, drink.getName(), drink.getDrinkType(), drink.getPrice(), drink.getDrinkTime(), drink.getNameOther());
    }
    @Override
    public void updateDrink(Drink drink) {
        String sql = "UPDATE Drinks SET name = ?, drink_type = ?, price = ?, drink_time = ?, name_other = ? WHERE id = ?";
        jdbcTemplate.update(sql, drink.getName(), drink.getDrinkType(), drink.getPrice(), drink.getDrinkTime(), drink.getNameOther(), drink.getId());
    }
    @Override
    public void deleteDrink(int drinkId) {
        String sql = "DELETE FROM Drinks WHERE id = ?";
        jdbcTemplate.update(sql, drinkId);
    }
    @Override
    public Drink getDrinkById(int drinkId) {
        String sql = "SELECT * FROM Drinks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, drinkRowMapper, drinkId);
    }
    @Override
    public List<Drink> getAllDrinks() {
        String sql = "SELECT * FROM Drinks";
        return jdbcTemplate.query(sql, drinkRowMapper);
    }
    @Override
    public List<Drink> getDrinksByDate(LocalDateTime date) {
        String sql = "SELECT * FROM Drinks WHERE DATE(drink_time) = ?";
        return jdbcTemplate.query(sql, drinkRowMapper, date);
    }
    @Override
    public List<Drink> getDrinksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM Drinks WHERE drink_time BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, drinkRowMapper, startDate, endDate);
    }
    @Override
    public int countDrinkOrdersByDate(LocalDateTime date) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE drink_id IN (SELECT id FROM Drinks WHERE DATE(drink_time) = ?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, date);
    }
    @Override
    public double getAverageDrinkAmountByDate(LocalDateTime date) {
        String sql = "SELECT AVG(price) FROM Drinks WHERE DATE(drink_time) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, date);
    }
    @Override
    public double getMaxDrinkAmountByDate(LocalDateTime date) {
        String sql = "SELECT MAX(price) FROM Drinks WHERE DATE(drink_time) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, date);
    }
    @Override
    public int getClientWithMaxDrinkAmount(LocalDateTime date) {
        String sql = "SELECT client_id FROM Orders WHERE drink_id IN (SELECT id FROM Drinks WHERE DATE(drink_time) = ?) GROUP BY client_id ORDER BY SUM(price) DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, date);
    }
}