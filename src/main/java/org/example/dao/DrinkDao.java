package org.example.dao;
import org.example.model.Drink;
import java.util.List;
import java.time.LocalDateTime;
public interface DrinkDao {
    void addDrink(Drink drink);
    void updateDrink(Drink drink);
    void deleteDrink(int drinkId);
    Drink getDrinkById(int drinkId);
    List<Drink> getAllDrinks();
    List<Drink> getDrinksByDate(LocalDateTime date);
    List<Drink> getDrinksByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    int countDrinkOrdersByDate(LocalDateTime date);
    double getAverageDrinkAmountByDate(LocalDateTime date);
    double getMaxDrinkAmountByDate(LocalDateTime date);
    int getClientWithMaxDrinkAmount(LocalDateTime date);
}