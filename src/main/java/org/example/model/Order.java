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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Column(name = "drink_id", nullable = false)
    private int drinkId;

    @Column(name = "dessert_id")
    private int dessertId;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;
    public Order(int clientId, int drinkId, LocalDateTime orderTime) {
        this.clientId = clientId;
        this.drinkId = drinkId;
        this.orderTime = orderTime;
        this.dessertId = 0;
    }
    public Order(int clientId, int drinkId, int dessertId, LocalDateTime orderTime) {
        this.clientId = clientId;
        this.drinkId = drinkId;
        this.dessertId = dessertId;
        this.orderTime = orderTime;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", drinkId=" + drinkId +
                ", dessertId=" + dessertId +
                ", orderTime=" + orderTime +
                '}';
    }
}