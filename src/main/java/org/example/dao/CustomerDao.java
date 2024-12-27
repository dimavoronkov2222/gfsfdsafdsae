package org.example.dao;
import org.example.model.Customer;
import org.springframework.jdbc.core.JdbcOperations;
import java.time.LocalDate;
import java.util.List;
public interface CustomerDao {
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    List<Customer> getCustomersByRegistrationDate(LocalDate date);
    List<Customer> getCustomersByQuery(String query);
    Customer getCustomerByQuery(String query);
    JdbcOperations getJdbcTemplate();
    double getMinDiscount();
    double getMaxDiscount();
    double getAvgDiscount();
    List<Customer> getCustomersWithMinDiscount();
    List<Customer> getCustomersWithMaxDiscount();
    Customer getYoungestCustomer();
    Customer getOldestCustomer();
    List<Customer> getCustomersWithBirthdayToday();
    List<Customer> getCustomersWithNoEmail();
}