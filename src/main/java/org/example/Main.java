package org.example;
import java.sql.*;
public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/CoffeeShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8289/00/5654";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database!");
            createTables(connection);
            insertSampleData(connection);
            updateData(connection);
            deleteData(connection);
            fetchData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createTables(Connection connection) throws SQLException {
        String createTablesSQL = """
            CREATE TABLE IF NOT EXISTS Drinks (
                id SERIAL PRIMARY KEY,
                name_ru VARCHAR(255),
                name_en VARCHAR(255),
                price NUMERIC(10, 2)
            );
            CREATE TABLE IF NOT EXISTS Desserts (
                id SERIAL PRIMARY KEY,
                name_ru VARCHAR(255),
                name_en VARCHAR(255),
                price NUMERIC(10, 2)
            );
            CREATE TABLE IF NOT EXISTS Staff (
                id SERIAL PRIMARY KEY,
                full_name VARCHAR(255),
                phone VARCHAR(15),
                email VARCHAR(255),
                position VARCHAR(50)
            );
            CREATE TABLE IF NOT EXISTS Clients (
                id SERIAL PRIMARY KEY,
                full_name VARCHAR(255),
                birth_date DATE,
                phone VARCHAR(15),
                email VARCHAR(255),
                discount NUMERIC(5, 2)
            );
            CREATE TABLE IF NOT EXISTS Schedule (
                id SERIAL PRIMARY KEY,
                staff_id INT REFERENCES Staff(id) ON DELETE CASCADE,
                day_of_week VARCHAR(20),
                start_time TIME,
                end_time TIME
            );
            CREATE TABLE IF NOT EXISTS Orders (
                id SERIAL PRIMARY KEY,
                client_id INT REFERENCES Clients(id) ON DELETE CASCADE,
                drink_id INT REFERENCES Drinks(id) ON DELETE SET NULL,
                dessert_id INT REFERENCES Desserts(id) ON DELETE SET NULL,
                order_time TIMESTAMP
            );
        """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTablesSQL);
            System.out.println("Tables created successfully.");
        }
    }
    private static void insertSampleData(Connection connection) throws SQLException {
        String insertDrinks = "INSERT INTO Drinks (name_ru, name_en, price) VALUES (?, ?, ?)";
        String insertDesserts = "INSERT INTO Desserts (name_ru, name_en, price) VALUES (?, ?, ?)";
        String insertStaff = "INSERT INTO Staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
        String insertClients = "INSERT INTO Clients (full_name, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psDrinks = connection.prepareStatement(insertDrinks);
             PreparedStatement psDesserts = connection.prepareStatement(insertDesserts);
             PreparedStatement psStaff = connection.prepareStatement(insertStaff);
             PreparedStatement psClients = connection.prepareStatement(insertClients)) {
            psDrinks.setString(1, "Кофе");
            psDrinks.setString(2, "Coffee");
            psDrinks.setBigDecimal(3, new java.math.BigDecimal("150.00"));
            psDrinks.executeUpdate();
            psDesserts.setString(1, "Торт");
            psDesserts.setString(2, "Cake");
            psDesserts.setBigDecimal(3, new java.math.BigDecimal("100.00"));
            psDesserts.executeUpdate();
            psStaff.setString(1, "Иванов Иван Иванович");
            psStaff.setString(2, "123456789");
            psStaff.setString(3, "ivanov@mail.com");
            psStaff.setString(4, "Бариста");
            psStaff.executeUpdate();
            psStaff.setString(1, "Петрова Ольга Александровна");
            psStaff.setString(2, "987654321");
            psStaff.setString(3, "petrova@mail.com");
            psStaff.setString(4, "Кондитер");
            psStaff.executeUpdate();
            psClients.setString(1, "Смирнов Сергей Викторович");
            psClients.setDate(2, Date.valueOf("1990-05-15"));
            psClients.setString(3, "555555555");
            psClients.setString(4, "smirnov@mail.com");
            psClients.setBigDecimal(5, new java.math.BigDecimal("10.00"));
            psClients.executeUpdate();
            System.out.println("Sample data inserted successfully.");
        }
    }
    private static void updateData(Connection connection) throws SQLException {
        String updateDrinkPrice = "UPDATE Drinks SET price = ? WHERE name_ru = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateDrinkPrice)) {
            ps.setBigDecimal(1, new java.math.BigDecimal("200.00"));
            ps.setString(2, "Кофе");
            ps.executeUpdate();
            System.out.println("Drink price updated successfully.");
        }
    }
    private static void deleteData(Connection connection) throws SQLException {
        String deleteDessert = "DELETE FROM Desserts WHERE name_ru = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteDessert)) {
            ps.setString(1, "Торт");
            ps.executeUpdate();
            System.out.println("Dessert deleted successfully.");
        }
    }
    private static void fetchData(Connection connection) throws SQLException {
        String fetchDrinks = "SELECT * FROM Drinks";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(fetchDrinks)) {
            System.out.println("Drinks:");
            while (rs.next()) {
                System.out.printf("%d: %s (%s) - %.2f\n",
                        rs.getInt("id"), rs.getString("name_ru"),
                        rs.getString("name_en"), rs.getBigDecimal("price"));
            }
        }
    }
}