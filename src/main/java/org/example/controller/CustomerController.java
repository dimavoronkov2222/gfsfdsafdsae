package org.example.controller;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/no-email")
    public List<Customer> getCustomersWithNoEmail() {
        return customerService.getCustomersWithNoEmail();
    }
    @GetMapping("/min-discount")
    public double getMinDiscount() {
        return customerService.getMinDiscount();
    }
    @GetMapping("/max-discount")
    public double getMaxDiscount() {
        return customerService.getMaxDiscount();
    }
    @GetMapping("/avg-discount")
    public double getAvgDiscount() {
        return customerService.getAvgDiscount();
    }
    @GetMapping("/youngest")
    public Customer getYoungestCustomer() {
        return customerService.getYoungestCustomer();
    }
    @GetMapping("/oldest")
    public Customer getOldestCustomer() {
        return customerService.getOldestCustomer();
    }
    @GetMapping("/birthday-today")
    public List<Customer> getCustomersWithBirthdayToday() {
        return customerService.getCustomersWithBirthdayToday();
    }
}