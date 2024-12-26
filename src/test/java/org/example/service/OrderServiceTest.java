package org.example.service;
import org.example.dao.OrderDao;
import org.example.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class OrderServiceTest {
    private OrderDao orderDao;
    private OrderService orderService;
    @BeforeEach
    void setUp() {
        orderDao = mock(OrderDao.class);
        orderService = new OrderService(orderDao);
    }
    @Test
    void placeOrder() throws SQLException {
        Order order = new Order(1, 1, LocalDateTime.now());
        orderService.placeOrder(order);
        verify(orderDao, times(1)).insertOrder(order);
    }
    @Test
    void updateOrder() throws SQLException {
        Order order = new Order(1, 1, 1, 1, LocalDateTime.now());
        orderService.updateOrder(order);
        verify(orderDao, times(1)).updateOrder(order);
    }
    @Test
    void removeOrder() throws SQLException {
        int orderId = 1;
        orderService.removeOrder(orderId);
        verify(orderDao, times(1)).deleteOrder(orderId);
    }
    @Test
    void getOrderById() throws SQLException {
        int orderId = 1;
        Order expectedOrder = new Order(orderId, 1, 1, 1, LocalDateTime.now());
        when(orderDao.getOrderById(orderId)).thenReturn(expectedOrder);
        Order actualOrder = orderService.getOrderById(orderId);
        assertEquals(expectedOrder, actualOrder);
        verify(orderDao, times(1)).getOrderById(orderId);
    }
    @Test
    void getAllOrders() throws SQLException {
        List<Order> expectedOrders = Arrays.asList(
                new Order(1, 1, 1, 1, LocalDateTime.now()),
                new Order(2, 2, 2, 2, LocalDateTime.now())
        );
        when(orderDao.getAllOrders()).thenReturn(expectedOrders);
        List<Order> actualOrders = orderService.getAllOrders();
        assertEquals(expectedOrders, actualOrders);
        verify(orderDao, times(1)).getAllOrders();
    }
}