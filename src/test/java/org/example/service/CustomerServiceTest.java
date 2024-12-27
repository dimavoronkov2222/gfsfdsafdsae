package org.example.service;
import org.example.dao.CustomerDao;
import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustomerServiceTest {
    @Mock
    private CustomerDao customerDao;
    @InjectMocks
    private CustomerService customerService;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getMinDiscount() throws SQLException {
        when(customerDao.getMinDiscount()).thenReturn(5.0);
        double minDiscount = customerService.getMinDiscount();
        assertEquals(5.0, minDiscount);
        verify(customerDao, times(1)).getMinDiscount();
    }
    @Test
    void getMaxDiscount() throws SQLException {
        when(customerDao.getMaxDiscount()).thenReturn(50.0);
        double maxDiscount = customerService.getMaxDiscount();
        assertEquals(50.0, maxDiscount);
        verify(customerDao, times(1)).getMaxDiscount();
    }
    @Test
    void getCustomersWithMinDiscount() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", "john@example.com", LocalDate.of(1990, 1, 1))
        );
        when(customerDao.getCustomersWithMinDiscount()).thenReturn(mockCustomers);
        List<Customer> customers = customerService.getCustomersWithMinDiscount();
        assertNotNull(customers);
        assertEquals(1, customers.size());
        verify(customerDao, times(1)).getCustomersWithMinDiscount();
    }
    @Test
    void getCustomersWithMaxDiscount() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "Jane Doe", "jane@example.com", LocalDate.of(1991, 2, 2))
        );
        when(customerDao.getCustomersWithMaxDiscount()).thenReturn(mockCustomers);
        List<Customer> customers = customerService.getCustomersWithMaxDiscount();
        assertNotNull(customers);
        assertEquals(1, customers.size());
        verify(customerDao, times(1)).getCustomersWithMaxDiscount();
    }
    @Test
    void getAvgDiscount() throws SQLException {
        when(customerDao.getAvgDiscount()).thenReturn(25.0);
        double avgDiscount = customerService.getAvgDiscount();
        assertEquals(25.0, avgDiscount);
        verify(customerDao, times(1)).getAvgDiscount();
    }
    @Test
    void getYoungestCustomer() throws SQLException {
        Customer mockCustomer = new Customer(1, "Young John", "youngjohn@example.com", LocalDate.of(2023, 1, 1));
        when(customerDao.getYoungestCustomer()).thenReturn(mockCustomer);
        Customer customer = customerService.getYoungestCustomer();
        assertNotNull(customer);
        assertEquals("Young John", customer.getName());
        verify(customerDao, times(1)).getYoungestCustomer();
    }
    @Test
    void getOldestCustomer() throws SQLException {
        Customer mockCustomer = new Customer(1, "Old Jane", "oldjane@example.com", LocalDate.of(2000, 1, 1));
        when(customerDao.getOldestCustomer()).thenReturn(mockCustomer);
        Customer customer = customerService.getOldestCustomer();
        assertNotNull(customer);
        assertEquals("Old Jane", customer.getName());
        verify(customerDao, times(1)).getOldestCustomer();
    }
    @Test
    void getCustomersWithBirthdayToday() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", "john@example.com", LocalDate.of(1990, 12, 27))
        );
        when(customerDao.getCustomersWithBirthdayToday()).thenReturn(mockCustomers);
        List<Customer> customers = customerService.getCustomersWithBirthdayToday();
        assertNotNull(customers);
        assertEquals(1, customers.size());
        verify(customerDao, times(1)).getCustomersWithBirthdayToday();
    }
    @Test
    void getCustomersWithNoEmail() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", null, LocalDate.of(1990, 1, 1))
        );
        when(customerDao.getCustomersWithNoEmail()).thenReturn(mockCustomers);
        List<Customer> customers = customerService.getCustomersWithNoEmail();
        assertNotNull(customers);
        assertEquals(1, customers.size());
        verify(customerDao, times(1)).getCustomersWithNoEmail();
    }
}