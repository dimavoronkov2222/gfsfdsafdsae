package org.example.controller;
import org.example.model.Order;
import org.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Test
    void handleInsertOrder() throws Exception {
        String orderJson = "{\"clientId\":1,\"drinkId\":2,\"dessertId\":3}";
        mockMvc.perform(post("/orders/add")
                        .contentType(APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed successfully."));
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderService, times(1)).placeOrder(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertEquals(1, capturedOrder.getClientId());
        assertEquals(2, capturedOrder.getDrinkId());
        assertEquals(3, capturedOrder.getDessertId());
    }
    @Test
    void handleGetOrdersByDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023, 12, 27, 0, 0);
        Order order1 = new Order(1, 1, 2, 3, date);
        Order order2 = new Order(2, 2, 3, 4, date);
        when(orderService.getOrdersByDate(date)).thenReturn(List.of(order1, order2));
        mockMvc.perform(get("/orders/by-date")
                        .param("date", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'clientId':1,'drinkId':2,'dessertId':3,'orderDate':'2023-12-27T00:00:00'},{'id':2,'clientId':2,'drinkId':3,'dessertId':4,'orderDate':'2023-12-27T00:00:00'}]"));
    }
    @Test
    void handleGetOrdersByDateRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 25, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 27, 0, 0);
        Order order1 = new Order(1, 1, 2, 3, startDate);
        Order order2 = new Order(2, 2, 3, 4, endDate);
        when(orderService.getOrdersByDateRange(startDate, endDate)).thenReturn(List.of(order1, order2));
        mockMvc.perform(get("/orders/by-date-range")
                        .param("startDate", "2023-12-25T00:00:00")
                        .param("endDate", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'clientId':1,'drinkId':2,'dessertId':3,'orderDate':'2023-12-25T00:00:00'},{'id':2,'clientId':2,'drinkId':3,'dessertId':4,'orderDate':'2023-12-27T00:00:00'}]"));
    }
    @Test
    void handleCountDessertOrdersByDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023, 12, 27, 0, 0);
        when(orderService.countDessertOrdersByDate(date)).thenReturn(5);
        mockMvc.perform(get("/orders/dessert-count")
                        .param("date", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
    @Test
    void handleCountDrinkOrdersByDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023, 12, 27, 0, 0);
        when(orderService.countDrinkOrdersByDate(date)).thenReturn(10);
        mockMvc.perform(get("/orders/drink-count")
                        .param("date", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
    @Test
    void handleGetAverageOrderAmountByDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023, 12, 27, 0, 0);
        when(orderService.getAverageOrderAmountByDate(date)).thenReturn(25.0);
        mockMvc.perform(get("/orders/avg-amount")
                        .param("date", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("25.0"));
    }
    @Test
    void handleGetMaxOrderAmountByDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023, 12, 27, 0, 0);
        when(orderService.getMaxOrderAmountByDate(date)).thenReturn(50.0);
        mockMvc.perform(get("/orders/max-amount")
                        .param("date", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("50.0"));
    }
    @Test
    void handleGetClientWithMaxOrderAmount() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023, 12, 27, 0, 0);
        when(orderService.getClientWithMaxOrderAmount(date)).thenReturn(1);
        mockMvc.perform(get("/orders/client-max-amount")
                        .param("date", "2023-12-27T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}