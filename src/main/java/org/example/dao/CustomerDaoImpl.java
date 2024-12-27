package org.example.dao;
import org.example.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
@Repository
public class CustomerDaoImpl implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        LocalDate birthDate = rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null;
        LocalDate registrationDate = rs.getDate("registration_date") != null ? rs.getDate("registration_date").toLocalDate() : null;
        double discount = rs.getDouble("discount");
        return new Customer(id, name, email, birthDate, registrationDate, discount);
    };
    @Override
    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (name, email, birth_date, registration_date, discount) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getBirthDate(), customer.getRegistrationDate(), customer.getDiscount());
    }
    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customers SET name = ?, email = ?, birth_date = ?, registration_date = ?, discount = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getBirthDate(), customer.getRegistrationDate(), customer.getDiscount(), customer.getId());
    }
    @Override
    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customers WHERE id = ?";
        jdbcTemplate.update(sql, customerId);
    }
    @Override
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM Customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, customerId);
    }
    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM Customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
    @Override
    public List<Customer> getCustomersByRegistrationDate(LocalDate date) {
        String sql = "SELECT * FROM Customers WHERE registration_date = ?";
        return jdbcTemplate.query(sql, customerRowMapper, date);
    }
    @Override
    public List<Customer> getCustomersByQuery(String query) {
        return jdbcTemplate.query(query, customerRowMapper);
    }
    @Override
    public Customer getCustomerByQuery(String query) {
        return jdbcTemplate.queryForObject(query, customerRowMapper);
    }
    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    @Override
    public double getMinDiscount() {
        String sql = "SELECT MIN(discount) FROM Customers";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }
    @Override
    public double getMaxDiscount() {
        String sql = "SELECT MAX(discount) FROM Customers";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }
    @Override
    public double getAvgDiscount() {
        String sql = "SELECT AVG(discount) FROM Customers";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }
    @Override
    public List<Customer> getCustomersWithMinDiscount() {
        String sql = "SELECT * FROM Customers WHERE discount = (SELECT MIN(discount) FROM Customers)";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
    @Override
    public List<Customer> getCustomersWithMaxDiscount() {
        String sql = "SELECT * FROM Customers WHERE discount = (SELECT MAX(discount) FROM Customers)";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
    @Override
    public Customer getYoungestCustomer() {
        String sql = "SELECT * FROM Customers ORDER BY birth_date DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, customerRowMapper);
    }
    @Override
    public Customer getOldestCustomer() {
        String sql = "SELECT * FROM Customers ORDER BY birth_date ASC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, customerRowMapper);
    }
    @Override
    public List<Customer> getCustomersWithBirthdayToday() {
        String sql = "SELECT * FROM Customers WHERE EXTRACT(MONTH FROM birth_date) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(DAY FROM birth_date) = EXTRACT(DAY FROM CURRENT_DATE)";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
    @Override
    public List<Customer> getCustomersWithNoEmail() {
        String sql = "SELECT * FROM Customers WHERE email IS NULL OR email = ''";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
}