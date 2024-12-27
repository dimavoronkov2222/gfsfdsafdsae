package org.example.service;
import org.example.dao.DrinkDao;
import org.example.model.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class DrinkServiceTest {
    @Mock
    private DrinkDao drinkDao;
    @InjectMocks
    private DrinkService drinkService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addDrink() throws SQLException {
        Drink drink = new Drink(1, "Coffee", "Кофе", 2.5);
        drinkService.addDrink(drink);
        ArgumentCaptor<Drink> drinkCaptor = ArgumentCaptor.forClass(Drink.class);
        verify(drinkDao, times(1)).addDrink(drinkCaptor.capture());
        assertEquals(drink, drinkCaptor.getValue());
    }
    @Test
    void updateDrink() throws SQLException {
        Drink drink = new Drink(1, "Tea", "Чай", 1.5);
        drinkService.updateDrink(drink);
        ArgumentCaptor<Drink> drinkCaptor = ArgumentCaptor.forClass(Drink.class);
        verify(drinkDao, times(1)).updateDrink(drinkCaptor.capture());
        assertEquals(drink, drinkCaptor.getValue());
    }
    @Test
    void deleteDrink() throws SQLException {
        int drinkId = 1;
        drinkService.deleteDrink(drinkId);
        verify(drinkDao, times(1)).deleteDrink(drinkId);
    }
    @Test
    void getDrinkById() throws SQLException {
        int drinkId = 1;
        Drink expectedDrink = new Drink(1, "Coffee", "Кофе", 2.5);
        when(drinkDao.getDrinkById(drinkId)).thenReturn(expectedDrink);
        Drink actualDrink = drinkService.getDrinkById(drinkId);
        assertEquals(expectedDrink, actualDrink);
        verify(drinkDao, times(1)).getDrinkById(drinkId);
    }
    @Test
    void getAllDrinks() throws SQLException {
        List<Drink> expectedDrinks = Arrays.asList(
                new Drink(1, "Coffee", "Кофе", 2.5),
                new Drink(2, "Tea", "Чай", 1.5)
        );
        when(drinkDao.getAllDrinks()).thenReturn(expectedDrinks);
        List<Drink> actualDrinks = drinkService.getAllDrinks();
        assertIterableEquals(expectedDrinks, actualDrinks);
        verify(drinkDao, times(1)).getAllDrinks();
    }
}