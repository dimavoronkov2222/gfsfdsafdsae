package org.example.dao;
import org.example.model.Drink;
import java.sql.SQLException;
import java.util.List;
public interface DrinkDao {
    void addDrink(Drink drink) throws SQLException;
    void updateDrink(Drink drink) throws SQLException;
    void deleteDrink(int drinkId) throws SQLException;
    Drink getDrinkById(int drinkId) throws SQLException;
    List<Drink> getAllDrinks() throws SQLException;
}