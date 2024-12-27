package org.example.controller;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @BeforeEach
    void setup() {
        // setup method is not needed when using @WebMvcTest and @MockBean
    }
    @Test
    void handleViewAllCustomers() throws Exception {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", "john@example.com", LocalDate.of(2023, 1, 1)),
                new Customer(2, "Jane Doe", "jane@example.com", LocalDate.of(2022, 5, 10))
        );
        when(customerService.getCustomersWithNoEmail()).thenReturn(mockCustomers);
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'John Doe','email':'john@example.com','registrationDate':'2023-01-01'},{'id':2,'name':'Jane Doe','email':'jane@example.com','registrationDate':'2022-05-10'}]"));
        verify(customerService, times(1)).getCustomersWithNoEmail();
    }
    @Test
    void handleGetMinDiscount() throws Exception {
        when(customerService.getMinDiscount()).thenReturn(10.0);
        mockMvc.perform(get("/customers/min-discount"))
                .andExpect(status().isOk())
                .andExpect(content().string("10.0"));
        verify(customerService, times(1)).getMinDiscount();
    }
    @Test
    void handleGetMaxDiscount() throws Exception {
        when(customerService.getMaxDiscount()).thenReturn(50.0);
        mockMvc.perform(get("/customers/max-discount"))
                .andExpect(status().isOk())
                .andExpect(content().string("50.0"));
        verify(customerService, times(1)).getMaxDiscount();
    }
    @Test
    void handleGetAvgDiscount() throws Exception {
        when(customerService.getAvgDiscount()).thenReturn(30.0);
        mockMvc.perform(get("/customers/avg-discount"))
                .andExpect(status().isOk())
                .andExpect(content().string("30.0"));

        verify(customerService, times(1)).getAvgDiscount();
    }
    @Test
    void handleGetYoungestCustomer() throws Exception {
        Customer mockCustomer = new Customer(1, "Young John", "youngjohn@example.com", LocalDate.of(2023, 1, 1));
        when(customerService.getYoungestCustomer()).thenReturn(mockCustomer);
        mockMvc.perform(get("/customers/youngest"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Young John','email':'youngjohn@example.com','registrationDate':'2023-01-01'}"));
        verify(customerService, times(1)).getYoungestCustomer();
    }
    @Test
    void handleGetOldestCustomer() throws Exception {
        Customer mockCustomer = new Customer(1, "Old Jane", "oldjane@example.com", LocalDate.of(2000, 1, 1));
        when(customerService.getOldestCustomer()).thenReturn(mockCustomer);
        mockMvc.perform(get("/customers/oldest"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Old Jane','email':'oldjane@example.com','registrationDate':'2000-01-01'}"));
        verify(customerService, times(1)).getOldestCustomer();
    }
    @Test
    void handleGetCustomersWithBirthdayToday() throws Exception {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", "john@example.com", LocalDate.of(2023, 12, 27))
        );
        when(customerService.getCustomersWithBirthdayToday()).thenReturn(mockCustomers);
        mockMvc.perform(get("/customers/birthday-today"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'John Doe','email':'john@example.com','registrationDate':'2023-12-27'}]"));
        verify(customerService, times(1)).getCustomersWithBirthdayToday();
    }
    @Test
    void handleGetCustomersWithNoEmail() throws Exception {
        List<Customer> mockCustomers = List.of(
                new Customer(1, "John Doe", null, LocalDate.of(2023, 1, 1))
        );
        when(customerService.getCustomersWithNoEmail()).thenReturn(mockCustomers);
        mockMvc.perform(get("/customers/no-email"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'John Doe','email':null,'registrationDate':'2023-01-01'}]"));

        verify(customerService, times(1)).getCustomersWithNoEmail();
    }
}