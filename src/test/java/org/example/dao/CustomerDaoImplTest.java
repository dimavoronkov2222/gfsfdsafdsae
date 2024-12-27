package org.example.dao;
import org.example.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerDaoImplTest {
    private DriverManagerDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private CustomerDao customerDao;
    @BeforeAll
    void setupDatabaseConnection() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/CoffeeShop");
        dataSource.setUsername("postgres");
        dataSource.setPassword("8289/00/5654");
        jdbcTemplate = new JdbcTemplate(dataSource);
        customerDao = new CustomerDaoImpl(jdbcTemplate);
        try (Connection connection = dataSource.getConnection(); var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Customers (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100), " +
                    "registration_date DATE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @BeforeEach
    void cleanDatabase() throws SQLException {
        try (Connection connection = dataSource.getConnection(); var stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Customers");
        }
    }
    @AfterAll
    void closeDatabaseConnection() throws SQLException {
        dataSource.getConnection().close();
    }
    @Test
    void testInsertCustomer() throws SQLException {
        Customer customer = new Customer(0, "John Doe", "john@example.com", LocalDate.now());
        customerDao.insertCustomer(customer);
        List<Customer> customers = customerDao.getAllCustomers();
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
        assertNotNull(customers.get(0).getRegistrationDate());
    }
    @Test
    void testUpdateCustomer() throws SQLException {
        Customer customer = new Customer(0, "John Doe", "john@example.com", LocalDate.now());
        customerDao.insertCustomer(customer);
        Customer insertedCustomer = customerDao.getAllCustomers().get(0);
        insertedCustomer.setName("Jane Doe");
        customerDao.updateCustomer(insertedCustomer);
        Customer updatedCustomer = customerDao.getCustomerById(insertedCustomer.getId());
        assertNotNull(updatedCustomer);
        assertEquals("Jane Doe", updatedCustomer.getName());
    }
    @Test
    void testDeleteCustomer() throws SQLException {
        Customer customer = new Customer(0, "John Doe", "john@example.com", LocalDate.now());
        customerDao.insertCustomer(customer);
        Customer insertedCustomer = customerDao.getAllCustomers().get(0);
        customerDao.deleteCustomer(insertedCustomer.getId());
        Customer deletedCustomer = customerDao.getCustomerById(insertedCustomer.getId());
        assertNull(deletedCustomer);
    }
    @Test
    void testGetCustomerById() throws SQLException {
        Customer customer = new Customer(0, "John Doe", "john@example.com", LocalDate.now());
        customerDao.insertCustomer(customer);
        Customer insertedCustomer = customerDao.getAllCustomers().get(0);
        Customer fetchedCustomer = customerDao.getCustomerById(insertedCustomer.getId());
        assertNotNull(fetchedCustomer);
        assertEquals("John Doe", fetchedCustomer.getName());
    }
    @Test
    void testGetAllCustomers() throws SQLException {
        customerDao.insertCustomer(new Customer(0, "John Doe", "john@example.com", LocalDate.now()));
        customerDao.insertCustomer(new Customer(0, "Jane Doe", "jane@example.com", LocalDate.now()));
        List<Customer> customers = customerDao.getAllCustomers();
        assertEquals(2, customers.size());
    }
    @Test
    void testGetCustomersByRegistrationDate() throws SQLException {
        LocalDate today = LocalDate.now();
        customerDao.insertCustomer(new Customer(0, "John Doe", "john@example.com", today));
        customerDao.insertCustomer(new Customer(0, "Jane Doe", "jane@example.com", today.minusDays(1)));
        List<Customer> customers = customerDao.getCustomersByRegistrationDate(today);
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
    }
    @Test
    void testGetCustomersByQuery() throws SQLException {
        customerDao.insertCustomer(new Customer(0, "John Doe", "john@example.com", LocalDate.now()));
        customerDao.insertCustomer(new Customer(0, "Jane Doe", "jane@example.com", LocalDate.now()));
        String query = "SELECT * FROM Customers WHERE name = 'Jane Doe'";
        List<Customer> customers = customerDao.getCustomersByQuery(query);
        assertEquals(1, customers.size());
        assertEquals("Jane Doe", customers.get(0).getName());
    }
    @Test
    void testGetCustomerByQuery() throws SQLException {
        customerDao.insertCustomer(new Customer(0, "John Doe", "john@example.com", LocalDate.now()));
        String query = "SELECT * FROM Customers WHERE name = 'John Doe'";
        Customer customer = customerDao.getCustomerByQuery(query);
        assertNotNull(customer);
        assertEquals("John Doe", customer.getName());
    }
}