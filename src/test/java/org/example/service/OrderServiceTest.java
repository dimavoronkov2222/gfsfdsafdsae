package org.example.service;
import org.example.dao.OrderDao;
import org.example.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class OrderServiceTest {
    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderService orderService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void placeOrder() throws SQLException {
        Order order = new Order(1, 1, 1, LocalDateTime.now());
        orderService.placeOrder(order);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderDao, times(1)).insertOrder(orderCaptor.capture());
        assertEquals(order, orderCaptor.getValue());
    }
    @Test
    void updateOrder() throws SQLException {
        Order order = new Order(1, 1, 1, 1, LocalDateTime.now());
        orderService.updateOrder(order);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderDao, times(1)).updateOrder(orderCaptor.capture());
        assertEquals(order, orderCaptor.getValue());
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
        assertIterableEquals(expectedOrders, actualOrders);
        verify(orderDao, times(1)).getAllOrders();
    }
}