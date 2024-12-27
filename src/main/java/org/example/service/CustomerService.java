package org.example.service;
import org.example.dao.CustomerDao;
import org.example.model.Customer;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CustomerService {
    private final CustomerDao customerDao;
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
    public double getMinDiscount() {
        String query = "SELECT MIN(discount) AS min_discount FROM customers";
        return customerDao.getJdbcTemplate().queryForObject(query, Double.class);
    }
    public double getMaxDiscount() {
        String query = "SELECT MAX(discount) AS max_discount FROM customers";
        return customerDao.getJdbcTemplate().queryForObject(query, Double.class);
    }
    public List<Customer> getCustomersWithMinDiscount() {
        String query = "SELECT * FROM customers WHERE discount = (SELECT MIN(discount) FROM customers)";
        return customerDao.getCustomersByQuery(query);
    }
    public List<Customer> getCustomersWithMaxDiscount() {
        String query = "SELECT * FROM customers WHERE discount = (SELECT MAX(discount) FROM customers)";
        return customerDao.getCustomersByQuery(query);
    }
    public double getAvgDiscount() {
        String query = "SELECT AVG(discount) AS avg_discount FROM customers";
        return customerDao.getJdbcTemplate().queryForObject(query, Double.class);
    }
    public Customer getYoungestCustomer() {
        String query = "SELECT * FROM customers ORDER BY birth_date DESC LIMIT 1";
        return customerDao.getCustomerByQuery(query);
    }
    public Customer getOldestCustomer() {
        String query = "SELECT * FROM customers ORDER BY birth_date ASC LIMIT 1";
        return customerDao.getCustomerByQuery(query);
    }
    public List<Customer> getCustomersWithBirthdayToday() {
        String query = "SELECT * FROM customers WHERE DATE(birth_date) = CURRENT_DATE";
        return customerDao.getCustomersByQuery(query);
    }
    public List<Customer> getCustomersWithNoEmail() {
        String query = "SELECT * FROM customers WHERE email IS NULL OR email = ''";
        return customerDao.getCustomersByQuery(query);
    }
}