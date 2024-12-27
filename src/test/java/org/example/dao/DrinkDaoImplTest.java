package org.example.dao;
import org.example.model.Drink;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.example.dao.DrinkDaoImpl;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DrinkDaoImplTest {
    private JdbcTemplate jdbcTemplate;
    private DrinkDao drinkDao;
    @BeforeAll
    void setUp() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/shop");
        dataSource.setUsername("postgres");
        dataSource.setPassword("8289/00/5654");
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Drinks (" +
                "id SERIAL PRIMARY KEY," +
                "name_en VARCHAR(255)," +
                "name_other VARCHAR(255)," +
                "price DOUBLE PRECISION)");
        drinkDao = new DrinkDaoImpl(jdbcTemplate);
        Connection connection = dataSource.getConnection();
        System.out.println("Connected to: " + connection.getMetaData().getURL());
    }
    @AfterAll
    void tearDown() throws SQLException {
        jdbcTemplate.execute("DROP TABLE IF EXISTS Drinks CASCADE");
    }
    @BeforeEach
    void cleanUp() throws SQLException {
        jdbcTemplate.execute("DELETE FROM Drinks");
    }
    @Test
    void addDrink() throws SQLException {
        Drink drink = new Drink(0, "Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);
        List<Drink> drinks = drinkDao.getAllDrinks();
        assertEquals(1, drinks.size());
        assertEquals("Coffee", drinks.get(0).getName());
        assertEquals("Кофе", drinks.get(0).getNameOther());
        assertEquals(2.5, drinks.get(0).getPrice());
    }
    @Test
    void updateDrink() throws SQLException {
        Drink drink = new Drink(0, "Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);
        Drink updatedDrink = new Drink(1, "Tea", "Чай", 1.5);
        drinkDao.updateDrink(updatedDrink);
        Drink retrievedDrink = drinkDao.getDrinkById(1);
        assertNotNull(retrievedDrink);
        assertEquals("Tea", retrievedDrink.getName());
        assertEquals("Чай", retrievedDrink.getNameOther());
        assertEquals(1.5, retrievedDrink.getPrice());
    }
    @Test
    void deleteDrink() throws SQLException {
        Drink drink = new Drink(0, "Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);
        drinkDao.deleteDrink(1);
        List<Drink> drinks = drinkDao.getAllDrinks();
        assertTrue(drinks.isEmpty());
    }
    @Test
    void getDrinkById() throws SQLException {
        Drink drink = new Drink(0, "Coffee", "Кофе", 2.5);
        drinkDao.addDrink(drink);
        Drink retrievedDrink = drinkDao.getDrinkById(1);
        assertNotNull(retrievedDrink);
        assertEquals("Coffee", retrievedDrink.getName());
        assertEquals("Кофе", retrievedDrink.getNameOther());
        assertEquals(2.5, retrievedDrink.getPrice());
    }
    @Test
    void getAllDrinks() throws SQLException {
        Drink drink1 = new Drink(0, "Coffee", "Кофе", 2.5);
        Drink drink2 = new Drink(0, "Tea", "Чай", 1.5);
        drinkDao.addDrink(drink1);
        drinkDao.addDrink(drink2);
        List<Drink> drinks = drinkDao.getAllDrinks();
        assertEquals(2, drinks.size());
        assertEquals("Coffee", drinks.get(0).getName());
        assertEquals("Tea", drinks.get(1).getName());
    }
}