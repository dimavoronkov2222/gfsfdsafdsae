package org.example.controller;
import org.example.model.Drink;
import org.example.service.DrinkService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class DrinkController {
    private final DrinkService drinkService;
    private final Scanner scanner;
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
        this.scanner = new Scanner(System.in);
    }
    public void handleAddDrink() {
        System.out.println("Enter drink name (English):");
        String name = scanner.nextLine();
        System.out.println("Enter drink name (Other language):");
        String nameOther = scanner.nextLine();
        System.out.println("Enter drink price:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Drink drink = new Drink(1, name, nameOther, price);
        try {
            drinkService.addDrink(drink);
            System.out.println("Drink added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleUpdateDrink() {
        System.out.println("Enter drink ID to update:");
        int id = scanner.nextInt();
        scanner.nextLine();  // Чтобы очистить буфер после чтения числа
        System.out.println("Enter new drink name (English):");
        String name = scanner.nextLine();
        System.out.println("Enter new drink name (Other language):");
        String nameOther = scanner.nextLine();
        System.out.println("Enter new drink price:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Drink drink = new Drink(1, name, nameOther, price);
        try {
            drinkService.updateDrink(drink);
            System.out.println("Drink updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleDeleteDrink() {
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
