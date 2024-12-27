package org.example.service;
import org.example.dao.DrinkDao;
import org.example.model.Drink;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DrinkService {
    private final DrinkDao drinkDao;
    public DrinkService(DrinkDao drinkDao) {
        this.drinkDao = drinkDao;
    }
    public void addDrink(Drink drink) {
        drinkDao.addDrink(drink);
    }
    public void updateDrink(Drink drink) {
        drinkDao.updateDrink(drink);
    }
    public void deleteDrink(int drinkId) {
        drinkDao.deleteDrink(drinkId);
    }
    public Drink getDrinkById(int drinkId) {
        return drinkDao.getDrinkById(drinkId);
    }
    public List<Drink> getAllDrinks() {
        return drinkDao.getAllDrinks();
    }
}