package org.example.dao;
import org.example.model.Customer;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
public interface CustomerDao {
    Connection getConnection() throws SQLException;
    void insertCustomer(Customer customer) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException;
    void deleteCustomer(int customerId) throws SQLException;
    Customer getCustomerById(int customerId) throws SQLException;
    List<Customer> getAllCustomers() throws SQLException;
    List<Customer> getCustomersByRegistrationDate(LocalDate date) throws SQLException;
    List<Customer> getCustomersByQuery(String query) throws SQLException;
    Customer getCustomerByQuery(String query) throws SQLException;
}