package org.example.controller;
import org.example.model.Customer;
import org.example.service.CustomerService;
import java.sql.SQLException;
import java.util.List;
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    public void handleViewAllCustomers() {
        try {
            List<Customer> customers = customerService.getCustomersWithNoEmail();
            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                customers.forEach(customer -> System.out.println(customer));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
    }
    public void handleGetMinDiscount() {
        try {
            double minDiscount = customerService.getMinDiscount();
            System.out.println("Minimum discount: " + minDiscount);
        } catch (SQLException e) {
            System.err.println("Error retrieving min discount: " + e.getMessage());
        }
    }
    public void handleGetMaxDiscount() {
        try {
            double maxDiscount = customerService.getMaxDiscount();
            System.out.println("Maximum discount: " + maxDiscount);
        } catch (SQLException e) {
            System.err.println("Error retrieving max discount: " + e.getMessage());
        }
    }
    public void handleGetAvgDiscount() {
        try {
            double avgDiscount = customerService.getAvgDiscount();
            System.out.println("Average discount: " + avgDiscount);
        } catch (SQLException e) {
            System.err.println("Error retrieving average discount: " + e.getMessage());
        }
    }
    public void handleGetYoungestCustomer() {
        try {
            Customer customer = customerService.getYoungestCustomer();
            if (customer != null) {
                System.out.println("Youngest customer: " + customer);
            } else {
                System.out.println("No customer found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving youngest customer: " + e.getMessage());
        }
    }
    public void handleGetOldestCustomer() {
        try {
            Customer customer = customerService.getOldestCustomer();
            if (customer != null) {
                System.out.println("Oldest customer: " + customer);
            } else {
                System.out.println("No customer found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving oldest customer: " + e.getMessage());
        }
    }
    public void handleGetCustomersWithBirthdayToday() {
        try {
            List<Customer> customers = customerService.getCustomersWithBirthdayToday();
            if (customers.isEmpty()) {
                System.out.println("No customers have a birthday today.");
            } else {
                customers.forEach(customer -> System.out.println(customer));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers with birthday today: " + e.getMessage());
        }
    }
    public void handleGetCustomersWithNoEmail() {
        try {
            List<Customer> customers = customerService.getCustomersWithNoEmail();
            if (customers.isEmpty()) {
                System.out.println("No customers without email.");
            } else {
                customers.forEach(customer -> System.out.println(customer));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers without email: " + e.getMessage());
        }
    }
}