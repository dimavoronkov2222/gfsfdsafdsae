package org.example.controller;
import org.example.model.Order;
import org.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class OrderControllerTest {
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;
    private void simulateUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
    }
    private String captureConsoleOutput(Runnable methodToRun) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        methodToRun.run();
        return outContent.toString();
    }
    @Test
    void handleInsertOrder() throws SQLException {
        String input = "1\n2\n";
        simulateUserInput(input);
        String output = captureConsoleOutput(() -> orderController.handleInsertOrder());
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderService, times(1)).placeOrder(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertEquals(1, capturedOrder.getClientId());
        assertEquals(2, capturedOrder.getDrinkId());
        String expectedOutput = "Enter client ID:\nEnter drink ID:\nOrder placed successfully.\n";
        assertEquals(expectedOutput, output);
    }
}