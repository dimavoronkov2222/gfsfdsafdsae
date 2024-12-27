package org.example.dao;
import org.example.model.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class CustomerDaoImpl implements CustomerDao {
    private final Connection connection;
    public CustomerDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }
    @Override
    public void insertCustomer(Customer customer) throws SQLException {
        String insertCustomerSQL = "INSERT INTO Customers (name, email, registration_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertCustomerSQL)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setDate(3, Date.valueOf(customer.getRegistrationDate()));
            ps.executeUpdate();
            System.out.println("Customer inserted successfully.");
        }
    }
    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        String updateCustomerSQL = "UPDATE Customers SET name = ?, email = ?, registration_date = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateCustomerSQL)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setDate(3, Date.valueOf(customer.getRegistrationDate()));
            ps.setInt(4, customer.getId());
            ps.executeUpdate();
            System.out.println("Customer updated successfully.");
        }
    }
    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        String deleteCustomerSQL = "DELETE FROM Customers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteCustomerSQL)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
            System.out.println("Customer deleted successfully.");
        }
    }
    @Override
    public Customer getCustomerById(int customerId) throws SQLException {
        String selectCustomerSQL = "SELECT * FROM Customers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectCustomerSQL)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                    return new Customer(customerId, name, email, registrationDate);
                }
            }
        }
        return null;
    }
    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String selectAllCustomersSQL = "SELECT * FROM Customers";
        try (PreparedStatement ps = connection.prepareStatement(selectAllCustomersSQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                customers.add(new Customer(id, name, email, registrationDate));
            }
        }
        return customers;
    }
    @Override
    public List<Customer> getCustomersByRegistrationDate(LocalDate date) throws SQLException {
        String query = "SELECT * FROM Customers WHERE registration_date = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("registration_date").toLocalDate()
                ));
            }
            return customers;
        }
    }
    @Override
    public List<Customer> getCustomersByQuery(String query) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                customers.add(new Customer(id, name, email, registrationDate));
            }
        }
        return customers;
    }
    @Override
    public Customer getCustomerByQuery(String query) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDate registrationDate = rs.getDate("registration_date").toLocalDate();
                return new Customer(id, name, email, registrationDate);
            }
        }
        return null;
    }
}