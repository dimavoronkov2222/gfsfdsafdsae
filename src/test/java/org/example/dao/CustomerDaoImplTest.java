package org.example.dao;
import org.example.model.Customer;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerDaoImplTest {
    private Connection connection;
    private CustomerDao customerDao;
    @BeforeAll
    void setupDatabaseConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/CoffeeShop";
        String user = "postgres";
        String password = "8289/00/5654";
        connection = DriverManager.getConnection(url, user, password);
        customerDao = new CustomerDaoImpl(connection);
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Customers (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100), " +
                    "registration_date DATE)");
        }
    }
    @BeforeEach
    void cleanDatabase() throws SQLException {
        try (var stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Customers");
        }
    }
    @AfterAll
    void closeDatabaseConnection() throws SQLException {
        connection.close();
    }
    @Test
    void testInsertCustomer() throws SQLException {
        Customer customer = new Customer(0, "John Doe", "john@example.com", LocalDate.now());
        customerDao.insertCustomer(customer);
        List<Customer> customers = customerDao.getAllCustomers();
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
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