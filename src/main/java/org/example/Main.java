package org.example;
import org.example.controller.DrinkController;
import org.example.dao.DrinkDao;
import org.example.dao.DrinkDaoImpl;
import org.example.service.DrinkService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/CoffeeShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8289/00/5654";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DrinkDao drinkDao = new DrinkDaoImpl(connection);
            DrinkService drinkService = new DrinkService(drinkDao);
            DrinkController drinkController = new DrinkController(drinkService);
            boolean running = true;
            Scanner scanner = new Scanner(System.in);
            while (running) {
                System.out.println("1. Add Drink");
                System.out.println("2. Update Drink");
                System.out.println("3. Delete Drink");
                System.out.println("4. View All Drinks");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                switch (choice) {
                    case 1 -> drinkController.handleAddDrink();
                    case 2 -> drinkController.handleUpdateDrink();
                    case 3 -> drinkController.handleDeleteDrink();
                    case 4 -> drinkController.handleViewAllDrinks();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}