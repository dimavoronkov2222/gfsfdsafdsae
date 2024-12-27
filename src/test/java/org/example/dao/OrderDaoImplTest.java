package org.example.dao;
import org.example.model.Order;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderDaoImplTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderDao orderDao;
    @BeforeAll
    void setUp() {
        jdbcTemplate.execute("CREATE TABLE Orders (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "client_id INT," +
                "drink_id INT," +
                "dessert_id INT," +
                "order_time TIMESTAMP)");
    }
    @AfterAll
    void tearDown() {
        jdbcTemplate.execute("DROP TABLE Orders");
    }
    @BeforeEach
    void cleanUp() {
        jdbcTemplate.execute("DELETE FROM Orders");
    }
    @Test
    void insertOrder() {
        Order order = new Order(1, 1, 2, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        List<Order> orders = orderDao.getAllOrders();
        assertEquals(1, orders.size());
        assertEquals(1, orders.get(0).getClientId());
        assertEquals(1, orders.get(0).getDrinkId());
        assertEquals(2, orders.get(0).getDessertId());
        assertEquals(LocalDateTime.of(2024, 12, 27, 12, 0), orders.get(0).getOrderTime());
    }
    @Test
    void updateOrder() {
        Order order = new Order(1, 1, 2, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        Order updatedOrder = new Order(1, 1, 2, 3, LocalDateTime.of(2024, 12, 27, 13, 0));
        orderDao.updateOrder(updatedOrder);
        Order retrievedOrder = orderDao.getOrderById(1);
        assertEquals(1, retrievedOrder.getClientId());
        assertEquals(2, retrievedOrder.getDrinkId());
        assertEquals(3, retrievedOrder.getDessertId());
        assertEquals(LocalDateTime.of(2024, 12, 27, 13, 0), retrievedOrder.getOrderTime());
    }
    @Test
    void deleteOrder() {
        Order order = new Order(1, 1, 2, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        orderDao.deleteOrder(1);
        List<Order> orders = orderDao.getAllOrders();
        assertTrue(orders.isEmpty());
    }
    @Test
    void getOrderById() {
        Order order = new Order(1, 1, 2, LocalDateTime.of(2024, 12, 27, 12, 0));
        orderDao.insertOrder(order);
        Order retrievedOrder = orderDao.getOrderById(1);
        assertNotNull(retrievedOrder);
        assertEquals(1, retrievedOrder.getClientId());
        assertEquals(1, retrievedOrder.getDrinkId());
        assertEquals(2, retrievedOrder.getDessertId());
        assertEquals(LocalDateTime.of(2024, 12, 27, 12, 0), retrievedOrder.getOrderTime());
    }
    @Test
    void getAllOrders() {
        Order order1 = new Order(1, 1, 2, LocalDateTime.of(2024, 12, 27, 12, 0));
        Order order2 = new Order(2, 2, 3, LocalDateTime.of(2024, 12, 27, 13, 0));
        orderDao.insertOrder(order1);
        orderDao.insertOrder(order2);
        List<Order> orders = orderDao.getAllOrders();
        assertEquals(2, orders.size());
    }
}