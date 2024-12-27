package org.example.model;
import java.time.LocalDateTime;
public class Drink {
    private int id;
    private String name;
    private String drinkType;
    private double price;
    private LocalDateTime drinkTime;
    private String nameOther;
    public Drink(int i, String name, String drinkType, double price) {
        this.id = id;
        this.name = name;
        this.drinkType = drinkType;
        this.price = price;
        this.drinkTime = drinkTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDrinkType() {
        return drinkType;
    }
    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public LocalDateTime getDrinkTime() {
        return drinkTime;
    }
    public void setDrinkTime(LocalDateTime drinkTime) {
        this.drinkTime = drinkTime;
    }
    public String getNameOther() {
        return nameOther;
    }
    public void setNameOther(String nameOther) {
        this.nameOther = nameOther;
    }
}