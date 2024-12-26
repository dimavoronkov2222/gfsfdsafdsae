package org.example;
import java.sql.*;
import java.util.Scanner;
public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/CoffeeShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8289/00/5654";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the database!");
            boolean running = true;
            while (running) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        insertNewOrderCoffee(connection, scanner);
                        break;
                    case 2:
                        insertNewOrderDessert(connection, scanner);
                        break;
                    case 3:
                        insertScheduleForMonday(connection, scanner);
                        break;
                    case 4:
                        insertNewCoffee(connection, scanner);
                        break;
                    case 5:
                        updateScheduleForTuesday(connection, scanner);
                        break;
                    case 6:
                        updateCoffeeName(connection, scanner);
                        break;
                    case 7:
                        updateOrder(connection, scanner);
                        break;
                    case 8:
                        updateDessertName(connection, scanner);
                        break;
                    case 9:
                        deleteOrder(connection, scanner);
                        break;
                    case 10:
                        deleteOrdersByDessert(connection, scanner);
                        break;
                    case 11:
                        deleteScheduleForDay(connection, scanner);
                        break;
                    case 12:
                        deleteScheduleForPeriod(connection, scanner);
                        break;
                    case 13:
                        showOrdersByDessert(connection, scanner);
                        break;
                    case 14:
                        showScheduleForDay(connection, scanner);
                        break;
                    case 15:
                        showOrdersByWaiter(connection, scanner);
                        break;
                    case 16:
                        showOrdersByClient(connection, scanner);
                        break;
                    case 17:
                        System.out.println("Exiting...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void showMenu() {
        System.out.println("\nCoffeeShop Menu:");
        System.out.println("1. Add a new coffee order");
        System.out.println("2. Add a new dessert order");
        System.out.println("3. Add work schedule for Monday");
        System.out.println("4. Add new coffee");
        System.out.println("5. Update work schedule for Tuesday");
        System.out.println("6. Update coffee name");
        System.out.println("7. Update order information");
        System.out.println("8. Update dessert name");
        System.out.println("9. Delete an order");
        System.out.println("10. Delete orders by dessert");
        System.out.println("11. Delete work schedule for a specific day");
        System.out.println("12. Delete work schedule for a specific period");
        System.out.println("13. Show all orders for a specific dessert");
        System.out.println("14. Show work schedule for a specific day");
        System.out.println("15. Show all orders for a specific waiter");
        System.out.println("16. Show all orders for a specific client");
        System.out.println("17. Exit");
        System.out.print("Please choose an option: ");
    }
    private static void insertNewOrderCoffee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter client ID:");
        int clientId = scanner.nextInt();
        System.out.println("Enter drink ID (coffee):");
        int drinkId = scanner.nextInt();
        String insertOrderSQL = "INSERT INTO Orders (client_id, drink_id, order_time) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertOrderSQL)) {
            ps.setInt(1, clientId);
            ps.setInt(2, drinkId);
            ps.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.executeUpdate();
            System.out.println("Coffee order added successfully.");
        }
    }
    private static void insertNewOrderDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter client ID:");
        int clientId = scanner.nextInt();
        System.out.println("Enter dessert ID:");
        int dessertId = scanner.nextInt();
        String insertOrderSQL = "INSERT INTO Orders (client_id, dessert_id, order_time) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertOrderSQL)) {
            ps.setInt(1, clientId);
            ps.setInt(2, dessertId);
            ps.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.executeUpdate();
            System.out.println("Dessert order added successfully.");
        }
    }
    private static void insertScheduleForMonday(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter staff ID:");
        int staffId = scanner.nextInt();
        System.out.println("Enter start time for Monday:");
        String startTime = scanner.next();
        System.out.println("Enter end time for Monday:");
        String endTime = scanner.next();

        String insertScheduleSQL = "INSERT INTO Schedule (staff_id, day_of_week, start_time, end_time) VALUES (?, 'Monday', ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertScheduleSQL)) {
            ps.setInt(1, staffId);
            ps.setString(2, startTime);
            ps.setString(3, endTime);
            ps.executeUpdate();
            System.out.println("Work schedule for Monday added.");
        }
    }
    private static void insertNewCoffee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter coffee name (in Russian):");
        String nameRu = scanner.nextLine();
        System.out.println("Enter coffee name (in English):");
        String nameEn = scanner.nextLine();
        System.out.println("Enter price of the coffee:");
        double price = scanner.nextDouble();
        String insertCoffeeSQL = "INSERT INTO Drinks (name_ru, name_en, price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertCoffeeSQL)) {
            ps.setString(1, nameRu);
            ps.setString(2, nameEn);
            ps.setDouble(3, price);
            ps.executeUpdate();
            System.out.println("New coffee added.");
        }
    }
    private static void updateScheduleForTuesday(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter staff ID to update schedule for Tuesday:");
        int staffId = scanner.nextInt();
        System.out.println("Enter new start time for Tuesday:");
        String startTime = scanner.next();
        System.out.println("Enter new end time for Tuesday:");
        String endTime = scanner.next();
        String updateScheduleSQL = "UPDATE Schedule SET start_time = ?, end_time = ? WHERE staff_id = ? AND day_of_week = 'Tuesday'";
        try (PreparedStatement ps = connection.prepareStatement(updateScheduleSQL)) {
            ps.setString(1, startTime);
            ps.setString(2, endTime);
            ps.setInt(3, staffId);
            ps.executeUpdate();
            System.out.println("Schedule for Tuesday updated.");
        }
    }
    private static void updateCoffeeName(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter the current name of the coffee (in Russian):");
        String currentName = scanner.nextLine();
        System.out.println("Enter new name for the coffee (in Russian):");
        String newName = scanner.nextLine();
        String updateCoffeeSQL = "UPDATE Drinks SET name_ru = ? WHERE name_ru = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateCoffeeSQL)) {
            ps.setString(1, newName);
            ps.setString(2, currentName);
            ps.executeUpdate();
            System.out.println("Coffee name updated.");
        }
    }
    private static void updateOrder(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter order ID to update:");
        int orderId = scanner.nextInt();
        System.out.println("Enter new client ID:");
        int clientId = scanner.nextInt();
        String updateOrderSQL = "UPDATE Orders SET client_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateOrderSQL)) {
            ps.setInt(1, clientId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
            System.out.println("Order updated.");
        }
    }
    private static void updateDessertName(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter current dessert name (in Russian):");
        String currentName = scanner.nextLine();
        System.out.println("Enter new dessert name (in Russian):");
        String newName = scanner.nextLine();
        String updateDessertSQL = "UPDATE Desserts SET name_ru = ? WHERE name_ru = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateDessertSQL)) {
            ps.setString(1, newName);
            ps.setString(2, currentName);
            ps.executeUpdate();
            System.out.println("Dessert name updated.");
        }
    }
    private static void deleteOrder(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter order ID to delete:");
        int orderId = scanner.nextInt();
        String deleteOrderSQL = "DELETE FROM Orders WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteOrderSQL)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
            System.out.println("Order deleted.");
        }
    }
    private static void deleteOrdersByDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter dessert ID to delete all orders with that dessert:");
        int dessertId = scanner.nextInt();
        String deleteOrdersSQL = "DELETE FROM Orders WHERE dessert_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteOrdersSQL)) {
            ps.setInt(1, dessertId);
            ps.executeUpdate();
            System.out.println("All orders for this dessert deleted.");
        }
    }
    private static void deleteScheduleForDay(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter staff ID to delete schedule for specific day:");
        int staffId = scanner.nextInt();
        System.out.println("Enter day of the week:");
        String day = scanner.nextLine().toLowerCase();
        String deleteScheduleSQL = "DELETE FROM Schedule WHERE staff_id = ? AND day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteScheduleSQL)) {
            ps.setInt(1, staffId);
            ps.setString(2, day);
            ps.executeUpdate();
            System.out.println("Schedule deleted.");
        }
    }
    private static void deleteScheduleForPeriod(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter start date of the period (yyyy-MM-dd):");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date of the period (yyyy-MM-dd):");
        String endDate = scanner.nextLine();
        String deleteScheduleSQL = "DELETE FROM Schedule WHERE start_time BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteScheduleSQL)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.executeUpdate();
            System.out.println("Schedule for the specified period deleted.");
        }
    }
    private static void showOrdersByDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter dessert ID to show all orders:");
        int dessertId = scanner.nextInt();
        String showOrdersSQL = "SELECT * FROM Orders WHERE dessert_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(showOrdersSQL)) {
            ps.setInt(1, dessertId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Order ID: " + rs.getInt("id") + ", Client ID: " + rs.getInt("client_id"));
                }
            }
        }
    }
    private static void showScheduleForDay(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter day of the week to show schedule:");
        String day = scanner.nextLine().toLowerCase();
        String showScheduleSQL = "SELECT * FROM Schedule WHERE day_of_week = ?";
        try (PreparedStatement ps = connection.prepareStatement(showScheduleSQL)) {
            ps.setString(1, day);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Staff ID: " + rs.getInt("staff_id") + ", Start Time: " + rs.getString("start_time") +
                            ", End Time: " + rs.getString("end_time"));
                }
            }
        }
    }
    private static void showOrdersByWaiter(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter waiter ID to show all orders:");
        int waiterId = scanner.nextInt();
        String showOrdersSQL = "SELECT * FROM Orders WHERE waiter_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(showOrdersSQL)) {
            ps.setInt(1, waiterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Order ID: " + rs.getInt("id") + ", Client ID: " + rs.getInt("client_id"));
                }
            }
        }
    }
    private static void showOrdersByClient(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter client ID to show all orders:");
        int clientId = scanner.nextInt();
        String showOrdersSQL = "SELECT * FROM Orders WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(showOrdersSQL)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Order ID: " + rs.getInt("id") + ", Drink ID: " + rs.getInt("drink_id"));
                }
            }
        }
    }
}