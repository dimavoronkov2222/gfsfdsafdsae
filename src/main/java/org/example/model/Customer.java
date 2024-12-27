package org.example.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "discount")
    private double discount;

    public Customer(int id, String name, String email, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public Customer(int id, String name, String email, LocalDate birthDate, LocalDate registrationDate, double discount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.discount = discount;
    }
}