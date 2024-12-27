package org.example.dao;
import org.example.model.Drink;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;
public interface DrinkDao {
    void addDrink(Drink drink) throws SQLException;
    void updateDrink(Drink drink) throws SQLException;
    void deleteDrink(int drinkId) throws SQLException;
    Drink getDrinkById(int drinkId) throws SQLException;
    List<Drink> getAllDrinks() throws SQLException;
    List<Drink> getDrinksByDate(LocalDateTime date) throws SQLException;
    List<Drink> getDrinksByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
    int countDrinkOrdersByDate(LocalDateTime date) throws SQLException;
    double getAverageDrinkAmountByDate(LocalDateTime date) throws SQLException;
    double getMaxDrinkAmountByDate(LocalDateTime date) throws SQLException;
    int getClientWithMaxDrinkAmount(LocalDateTime date) throws SQLException;
}