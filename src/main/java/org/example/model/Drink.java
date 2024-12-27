package org.example.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "drink_type", nullable = false)
    private String drinkType;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "drink_time")
    private LocalDateTime drinkTime;
    @Column(name = "name_other")
    private String nameOther;
    public Drink(String name, String drinkType, double price, String nameOther) {
        this.name = name;
        this.drinkType = drinkType;
        this.price = price;
        this.nameOther = nameOther;
    }
    public Drink(int id, String name, String drinkType, double price) {
        this.id = id;
        this.name = name;
        this.drinkType = drinkType;
        this.price = price;
        this.nameOther = nameOther;
    }
}
