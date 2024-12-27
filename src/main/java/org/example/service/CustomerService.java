package org.example.service;
import org.example.dao.CustomerDao;
import org.example.model.Customer;
import java.sql.*;
import java.util.List;
public class CustomerService {
    private final CustomerDao customerDao;
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
    public double getMinDiscount() throws SQLException {
        String query = "SELECT MIN(discount) AS min_discount FROM customers";
        try (PreparedStatement stmt = customerDao.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("min_discount");
            }
            return 0.0;
        }
    }
    public double getMaxDiscount() throws SQLException {
        String query = "SELECT MAX(discount) AS max_discount FROM customers";
        try (PreparedStatement stmt = customerDao.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("max_discount");
            }
            return 0.0;
        }
    }
    public List<Customer> getCustomersWithMinDiscount() throws SQLException {
        String query = "SELECT name, discount FROM customers WHERE discount = (SELECT MIN(discount) FROM customers)";
        return customerDao.getCustomersByQuery(query);
    }
    public List<Customer> getCustomersWithMaxDiscount() throws SQLException {
        String query = "SELECT name, discount FROM customers WHERE discount = (SELECT MAX(discount) FROM customers)";
        return customerDao.getCustomersByQuery(query);
    }
    public double getAvgDiscount() throws SQLException {
        String query = "SELECT AVG(discount) AS avg_discount FROM customers";
        try (PreparedStatement stmt = customerDao.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_discount");
            }
            return 0.0;
        }
    }
    public Customer getYoungestCustomer() throws SQLException {
        String query = "SELECT name, birth_date FROM customers ORDER BY birth_date DESC LIMIT 1";
        return customerDao.getCustomerByQuery(query);
    }
    public Customer getOldestCustomer() throws SQLException {
        String query = "SELECT name, birth_date FROM customers ORDER BY birth_date ASC LIMIT 1";
        return customerDao.getCustomerByQuery(query);
    }
    public List<Customer> getCustomersWithBirthdayToday() throws SQLException {
        String query = "SELECT name, birth_date FROM customers WHERE DATE(birth_date) = CURRENT_DATE";
        return customerDao.getCustomersByQuery(query);
    }
    public List<Customer> getCustomersWithNoEmail() throws SQLException {
        String query = "SELECT name, email FROM customers WHERE email IS NULL OR email = ''";
        return customerDao.getCustomersByQuery(query);
    }
}

