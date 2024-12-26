package org.example.model;
public class Drink {
    private int id;
    private String nameEn;
    private String nameOther;
    private double price;
    public Drink(int id, String nameEn, String nameOther, double price) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameOther = nameOther;
        this.price = price;
    }
    public Drink(String nameEn, String nameOther, double price) {
        this.nameEn = nameEn;
        this.nameOther = nameOther;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getNameOther() {
        return nameOther;
    }
    public void setNameOther(String nameOther) {
        this.nameOther = nameOther;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameOther='" + nameOther + '\'' +
                ", price=" + price +
                '}';
    }
}