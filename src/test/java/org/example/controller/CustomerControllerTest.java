package org.example.controller;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class CustomerControllerTest {
    private CustomerService customerService;
    private CustomerController customerController;
    @BeforeEach
    void setup() {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService);
    }
    @Test
    void handleViewAllCustomers() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", "john@example.com", LocalDate.of(2023, 1, 1)),
                new Customer(2, "Jane Doe", "jane@example.com", LocalDate.of(2022, 5, 10))
        );
        when(customerService.getCustomersWithNoEmail()).thenReturn(mockCustomers);
        customerController.handleViewAllCustomers();
        verify(customerService, times(1)).getCustomersWithNoEmail();
    }
    @Test
    void handleGetMinDiscount() throws SQLException {
        when(customerService.getMinDiscount()).thenReturn(10.0);
        customerController.handleGetMinDiscount();
        verify(customerService, times(1)).getMinDiscount();
    }
    @Test
    void handleGetMaxDiscount() throws SQLException {
        when(customerService.getMaxDiscount()).thenReturn(50.0);
        customerController.handleGetMaxDiscount();
        verify(customerService, times(1)).getMaxDiscount();
    }
    @Test
    void handleGetAvgDiscount() throws SQLException {
        when(customerService.getAvgDiscount()).thenReturn(30.0);
        customerController.handleGetAvgDiscount();
        verify(customerService, times(1)).getAvgDiscount();
    }
    @Test
    void handleGetYoungestCustomer() throws SQLException {
        Customer mockCustomer = new Customer(1, "Young John", "youngjohn@example.com", LocalDate.of(2023, 1, 1));
        when(customerService.getYoungestCustomer()).thenReturn(mockCustomer);
        customerController.handleGetYoungestCustomer();
        verify(customerService, times(1)).getYoungestCustomer();
    }
    @Test
    void handleGetOldestCustomer() throws SQLException {
        Customer mockCustomer = new Customer(1, "Old Jane", "oldjane@example.com", LocalDate.of(2000, 1, 1));
        when(customerService.getOldestCustomer()).thenReturn(mockCustomer);
        customerController.handleGetOldestCustomer();
        verify(customerService, times(1)).getOldestCustomer();
    }
    @Test
    void handleGetCustomersWithBirthdayToday() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", "john@example.com", LocalDate.of(2023, 12, 27))
        );
        when(customerService.getCustomersWithBirthdayToday()).thenReturn(mockCustomers);
        customerController.handleGetCustomersWithBirthdayToday();
        verify(customerService, times(1)).getCustomersWithBirthdayToday();
    }
    @Test
    void handleGetCustomersWithNoEmail() throws SQLException {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", null, LocalDate.of(2023, 1, 1))
        );
        when(customerService.getCustomersWithNoEmail()).thenReturn(mockCustomers);
        customerController.handleGetCustomersWithNoEmail();
        verify(customerService, times(1)).getCustomersWithNoEmail();
    }
}