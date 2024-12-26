package org.example.controller;
import org.example.model.Drink;
import org.example.service.DrinkService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class DrinkController {
    private final DrinkService drinkService;
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }
    public void handleAddDrink() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter drink name (English):");
        String nameEn = scanner.nextLine();
        System.out.println("Enter drink name (Other language):");
        String nameOther = scanner.nextLine();
        System.out.println("Enter drink price:");
        double price = scanner.nextDouble();
        Drink drink = new Drink(nameEn, nameOther, price);
        try {
            drinkService.addDrink(drink);
            System.out.println("Drink added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleUpdateDrink() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter drink ID to update:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new drink name (English):");
        String nameEn = scanner.nextLine();
        System.out.println("Enter new drink name (Other language):");
        String nameOther = scanner.nextLine();
        System.out.println("Enter new drink price:");
        double price = scanner.nextDouble();
        Drink drink = new Drink(id, nameEn, nameOther, price);
        try {
            drinkService.updateDrink(drink);
            System.out.println("Drink updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleDeleteDrink() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter drink ID to delete:");
        int id = scanner.nextInt();
        try {
            drinkService.deleteDrink(id);
            System.out.println("Drink deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleViewAllDrinks() {
        try {
            List<Drink> drinks = drinkService.getAllDrinks();
            for (Drink drink : drinks) {
                System.out.println(drink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}