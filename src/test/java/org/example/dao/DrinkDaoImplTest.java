package org.example.dao;
import org.example.model.Drink;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DrinkDaoImplTest {
    private Connection connection;
    private DrinkDao drinkDao;
    @BeforeAll
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.createStatement().execute("CREATE TABLE Drinks (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name_en VARCHAR(255)," +
                "name_other VARCHAR(255)," +
                "price DOUBLE)");
        drinkDao = new DrinkDaoImpl(connection);
    }
    @AfterAll
    void tearDown() throws SQLException {
        connection.createStatement().execute("DROP TABLE Drinks");
        connection.close();
    }
    @BeforeEach
    void cleanUp() throws SQLException {
        connection.createStatement().execute("DELETE FROM Drinks");
    }
    @Test
    void addDrink() throws SQLException {
        Drink drink = new Drink("Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);

        List<Drink> drinks = drinkDao.getAllDrinks();
        assertEquals(1, drinks.size());
        assertEquals("Coffee", drinks.get(0).getNameEn());
    }
    @Test
    void updateDrink() throws SQLException {
        Drink drink = new Drink("Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);

        Drink updatedDrink = new Drink(1, "Tea", "Чай", 1.5);
        drinkDao.updateDrink(updatedDrink);

        Drink retrievedDrink = drinkDao.getDrinkById(1);
        assertEquals("Tea", retrievedDrink.getNameEn());
    }
    @Test
    void deleteDrink() throws SQLException {
        Drink drink = new Drink("Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);

        drinkDao.deleteDrink(1);
        List<Drink> drinks = drinkDao.getAllDrinks();
        assertTrue(drinks.isEmpty());
    }
    @Test
    void getDrinkById() throws SQLException {
        Drink drink = new Drink("Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);

        Drink retrievedDrink = drinkDao.getDrinkById(1);
        assertNotNull(retrievedDrink);
        assertEquals("Coffee", retrievedDrink.getNameEn());
    }
    @Test
    void getAllDrinks() throws SQLException {
        Drink drink1 = new Drink("Coffee", "Кофе", 2.5);
        Drink drink2 = new Drink("Tea", "Чай", 1.5);

        drinkDao.addDrink(drink1);
        drinkDao.addDrink(drink2);

        List<Drink> drinks = drinkDao.getAllDrinks();
        assertEquals(2, drinks.size());
    }
}