package org.example.dao;
import org.example.model.Drink;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DrinkDaoImpl implements DrinkDao {
    private final Connection connection;
    public DrinkDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void addDrink(Drink drink) throws SQLException {
        String sql = "INSERT INTO Drinks (name_en, name_other, price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, drink.getNameEn());
            ps.setString(2, drink.getNameOther());
            ps.setDouble(3, drink.getPrice());
            ps.executeUpdate();
        }
    }
    @Override
    public void updateDrink(Drink drink) throws SQLException {
        String sql = "UPDATE Drinks SET name_en = ?, name_other = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, drink.getNameEn());
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
                            rs.getInt("id"),
                            rs.getString("name_en"),
                            rs.getString("name_other"),
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
        String sql = "SELECT * FROM Drinks";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                drinks.add(new Drink(
                        rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_other"),
                        rs.getDouble("price")
                ));
            }
        }
        return drinks;
    }
}