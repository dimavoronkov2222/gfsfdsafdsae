package org.example.service;
import org.example.dao.DrinkDao;
import org.example.model.Drink;
import java.sql.SQLException;
import java.util.List;
public class DrinkService {
    private final DrinkDao drinkDao;
    public DrinkService(DrinkDao drinkDao) {
        this.drinkDao = drinkDao;
    }
    public void addDrink(Drink drink) throws SQLException {
        drinkDao.addDrink(drink);
    }
    public void updateDrink(Drink drink) throws SQLException {
        drinkDao.updateDrink(drink);
    }
    public void deleteDrink(int drinkId) throws SQLException {
        drinkDao.deleteDrink(drinkId);
    }
    public Drink getDrinkById(int drinkId) throws SQLException {
        return drinkDao.getDrinkById(drinkId);
    }
    public List<Drink> getAllDrinks() throws SQLException {
        return drinkDao.getAllDrinks();
    }
}