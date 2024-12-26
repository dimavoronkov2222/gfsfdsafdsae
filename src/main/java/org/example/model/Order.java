package org.example.model;
import java.time.LocalDateTime;
public class Order {
    private int id;
    private int clientId;
    private int drinkId;
    private int dessertId;
    private LocalDateTime orderTime;
    public Order(int clientId, int drinkId, LocalDateTime orderTime) {
        this.clientId = clientId;
        this.drinkId = drinkId;
        this.orderTime = orderTime;
    }
    public Order(int id, int clientId, int drinkId, int dessertId, LocalDateTime orderTime) {
        this.id = id;
        this.clientId = clientId;
        this.drinkId = drinkId;
        this.dessertId = dessertId;
        this.orderTime = orderTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getDrinkId() {
        return drinkId;
    }
    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }
    public int getDessertId() {
        return dessertId;
    }
    public void setDessertId(int dessertId) {
        this.dessertId = dessertId;
    }
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(LocalDateTime orderTime) {
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