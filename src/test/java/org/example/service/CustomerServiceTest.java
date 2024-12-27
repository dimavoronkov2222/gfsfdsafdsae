package org.example.service;
import org.example.dao.CustomerDao;
import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class CustomerServiceTest {
    private CustomerDao customerDao;
    private CustomerService customerService;
    @BeforeEach
    void setup() {
        customerDao = mock(CustomerDao.class);
        customerService = new CustomerService(customerDao);
    }
    @Test
    void getMinDiscount() throws SQLException {
        mockQueryResult("SELECT MIN(discount) AS min_discount FROM customers", 5.0);
        double minDiscount = customerService.getMinDiscount();
        assertEquals(5.0, minDiscount);
    }
    @Test
    void getMaxDiscount() throws SQLException {
        mockQueryResult("SELECT MAX(discount) AS max_discount FROM customers", 50.0);
        double maxDiscount = customerService.getMaxDiscount();
        assertEquals(50.0, maxDiscount);
    }
    @Test
    void getCustomersWithMinDiscount() throws SQLException {
        mockCustomerQuery("SELECT name, discount FROM customers WHERE discount = (SELECT MIN(discount) FROM customers)");
        List<Customer> customers = customerService.getCustomersWithMinDiscount();
        assertNotNull(customers);
    }
    @Test
    void getCustomersWithMaxDiscount() throws SQLException {
        mockCustomerQuery("SELECT name, discount FROM customers WHERE discount = (SELECT MAX(discount) FROM customers)");
        List<Customer> customers = customerService.getCustomersWithMaxDiscount();
        assertNotNull(customers);
    }
    @Test
    void getAvgDiscount() throws SQLException {
        mockQueryResult("SELECT AVG(discount) AS avg_discount FROM customers", 25.0);
        double avgDiscount = customerService.getAvgDiscount();
        assertEquals(25.0, avgDiscount);
    }
    @Test
    void getYoungestCustomer() throws SQLException {
        mockCustomerQuery("SELECT name, birth_date FROM customers ORDER BY birth_date DESC LIMIT 1");
        Customer customer = customerService.getYoungestCustomer();
        assertNotNull(customer);
    }
    @Test
    void getOldestCustomer() throws SQLException {
        mockCustomerQuery("SELECT name, birth_date FROM customers ORDER BY birth_date ASC LIMIT 1");
        Customer customer = customerService.getOldestCustomer();
        assertNotNull(customer);
    }
    @Test
    void getCustomersWithBirthdayToday() throws SQLException {
        mockCustomerQuery("SELECT name, birth_date FROM customers WHERE DATE(birth_date) = CURRENT_DATE");
        List<Customer> customers = customerService.getCustomersWithBirthdayToday();
        assertNotNull(customers);
    }
    @Test
    void getCustomersWithNoEmail() throws SQLException {
        mockCustomerQuery("SELECT name, email FROM customers WHERE email IS NULL OR email = ''");
        List<Customer> customers = customerService.getCustomersWithNoEmail();
        assertNotNull(customers);
    }
    private void mockQueryResult(String query, double resultValue) throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(customerDao.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble(1)).thenReturn(resultValue);
    }
    private void mockCustomerQuery(String query) throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(customerDao.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Customer mockCustomer = new Customer(1, "John Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        when(customerDao.getCustomerByQuery(query)).thenReturn(mockCustomer);
    }
}