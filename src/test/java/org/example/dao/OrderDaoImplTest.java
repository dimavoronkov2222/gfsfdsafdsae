package org.example.dao;
import org.example.model.Order;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderDaoImplTest {
    private Connection connection;
    private OrderDao orderDao;
    @BeforeAll
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        connection.createStatement().execute("CREATE TABLE Orders (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "client_id INT," +
                "drink_id INT," +
                "dessert_id INT," +
                "order_time TIMESTAMP)");
        orderDao = new OrderDaoImpl(connection);
    }
    @AfterAll
    void tearDown() throws SQLException {
        connection.createStatement().execute("DROP TABLE Orders");
        connection.close();
    }
    @BeforeEach
    void cleanUp() throws SQLException {
        connection.createStatement().execute("DELETE FROM Orders");
    }
    @Test
    void insertOrder() throws SQLException {
        Order order = new Order(1, 1, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        List<Order> orders = orderDao.getAllOrders();
        assertEquals(1, orders.size());
        assertEquals(1, orders.get(0).getClientId());
        assertEquals(1, orders.get(0).getDrinkId());
    }
    @Test
    void updateOrder() throws SQLException {
        Order order = new Order(1, 1, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        Order updatedOrder = new Order(1, 1, 2, 2, LocalDateTime.of(2024, 12, 27, 13, 0));
        orderDao.updateOrder(updatedOrder);
        Order retrievedOrder = orderDao.getOrderById(1);
        assertEquals(2, retrievedOrder.getDrinkId());
        assertEquals(2, retrievedOrder.getDessertId());
        assertEquals(LocalDateTime.of(2024, 12, 27, 13, 0), retrievedOrder.getOrderTime());
    }
    @Test
    void deleteOrder() throws SQLException {
        Order order = new Order(1, 1, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        orderDao.deleteOrder(1);
        List<Order> orders = orderDao.getAllOrders();
        assertTrue(orders.isEmpty());
    }
    @Test
    void getOrderById() throws SQLException {
        Order order = new Order(1, 1, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        Order retrievedOrder = orderDao.getOrderById(1);
        assertNotNull(retrievedOrder);
        assertEquals(1, retrievedOrder.getClientId());
        assertEquals(1, retrievedOrder.getDrinkId());
        assertEquals(LocalDateTime.of(2024, 12, 27, 12, 0), retrievedOrder.getOrderTime());
    }
    @Test
    void getAllOrders() throws SQLException {
        Order order1 = new Order(1, 1, LocalDateTime.of(2024, 12, 27, 12, 0));
        Order order2 = new Order(2, 2, LocalDateTime.of(2024, 12, 27, 13, 0));
        orderDao.insertOrder(order1);
        orderDao.insertOrder(order2);
        List<Order> orders = orderDao.getAllOrders();
        assertEquals(2, orders.size());
    }
}