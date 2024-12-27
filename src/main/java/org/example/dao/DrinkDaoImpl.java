package org.example.dao;
import org.example.model.Drink;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
public class DrinkDaoImpl implements DrinkDao {
    private final Connection connection;
    public DrinkDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void addDrink(Drink drink) throws SQLException {
        String sql = "INSERT INTO Drinks (name, name_other, price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, drink.getName());
            ps.setString(2, drink.getNameOther());
            ps.setDouble(3, drink.getPrice());
            ps.executeUpdate();
        }
    }
    @Override
    public void updateDrink(Drink drink) throws SQLException {
        String sql = "UPDATE Drinks SET name = ?, name_other = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, drink.getName());
            ps.setString(2, drink.getNameOther());
            ps.setDouble(3, drink.getPrice());
            ps.setInt(4, drink.getId());
            ps.executeUpdate();
        }
    }
    @Override
    public void deleteDrink(int drinkId) throws SQLException {
        String sql = "DELETE FROM Drinks WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, drinkId);
            ps.executeUpdate();
        }
    }
    @Override
    public Drink getDrinkById(int drinkId) throws SQLException {
        String sql = "SELECT * FROM Drinks WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, drinkId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Drink(
                            1, rs.getString("name"),
                            rs.getString("drink_type"),
                            rs.getDouble("price")
                    );
                }
            }
        }
        return null;
    }
    @Override
    public List<Drink> getAllDrinks() throws SQLException {
        List<Drink> drinks = new ArrayList<>();
        String selectAllDrinksSQL = "SELECT * FROM Drinks";
        try (PreparedStatement ps = connection.prepareStatement(selectAllDrinksSQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String drinkType = rs.getString("drink_type");
                double price = rs.getDouble("price");
                Timestamp drinkTime = rs.getTimestamp("drink_time");
                drinks.add(new Drink(1, name, drinkType, price));
            }
        }
        return drinks;
    }
    @Override
    public List<Drink> getDrinksByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT * FROM Drinks WHERE DATE(drink_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            List<Drink> drinks = new ArrayList<>();
            while (rs.next()) {
                drinks.add(new Drink(
                        1, rs.getString("name"),
                        rs.getString("drink_type"),
                        rs.getDouble("price")
                ));
            }
            return drinks;
        }
    }
    @Override
    public List<Drink> getDrinksByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String query = "SELECT * FROM Drinks WHERE drink_time BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            List<Drink> drinks = new ArrayList<>();
            while (rs.next()) {
                drinks.add(new Drink(
                        1, rs.getString("name"),
                        rs.getString("drink_type"),
                        rs.getDouble("price")
                ));
            }
            return drinks;
        }
    }
    @Override
    public int countDrinkOrdersByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT COUNT(*) FROM Orders WHERE drink_id IN (SELECT id FROM Drinks WHERE DATE(drink_time) = ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    @Override
    public double getAverageDrinkAmountByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT AVG(price) FROM Drinks WHERE DATE(drink_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }
    @Override
    public double getMaxDrinkAmountByDate(LocalDateTime date) throws SQLException {
        String query = "SELECT MAX(price) FROM Drinks WHERE DATE(drink_time) = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }
    @Override
    public int getClientWithMaxDrinkAmount(LocalDateTime date) throws SQLException {
        String query = "SELECT client_id FROM Orders WHERE drink_id IN (SELECT id FROM Drinks WHERE DATE(drink_time) = ?) GROUP BY client_id ORDER BY SUM(price) DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("client_id");
            }
        }
        return 0;
    }
}