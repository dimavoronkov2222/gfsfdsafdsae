package org.example.controller;
import org.example.model.Drink;
import org.example.service.DrinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class DrinkControllerTest {
    private DrinkService drinkService;
    private DrinkController drinkController;
    @BeforeEach
    void setUp() {
        drinkService = mock(DrinkService.class);
        drinkController = new DrinkController(drinkService);
    }
    @Test
    void handleAddDrink() throws SQLException {
        String input = "Coffee\nКофе\n2.5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        drinkController.handleAddDrink();
        ArgumentCaptor<Drink> drinkCaptor = ArgumentCaptor.forClass(Drink.class);
        verify(drinkService, times(1)).addDrink(drinkCaptor.capture());
        Drink capturedDrink = drinkCaptor.getValue();
        assertEquals("Coffee", capturedDrink.getNameEn());
        assertEquals("Кофе", capturedDrink.getNameOther());
        assertEquals(2.5, capturedDrink.getPrice());
    }
    @Test
    void handleUpdateDrink() throws SQLException {
        String input = "1\nTea\nЧай\n1.5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        drinkController.handleUpdateDrink();
        ArgumentCaptor<Drink> drinkCaptor = ArgumentCaptor.forClass(Drink.class);
        verify(drinkService, times(1)).updateDrink(drinkCaptor.capture());
        Drink capturedDrink = drinkCaptor.getValue();
        assertEquals(1, capturedDrink.getId());
        assertEquals("Tea", capturedDrink.getNameEn());
        assertEquals("Чай", capturedDrink.getNameOther());
        assertEquals(1.5, capturedDrink.getPrice());
    }
    @Test
    void handleDeleteDrink() throws SQLException {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        drinkController.handleDeleteDrink();
        verify(drinkService, times(1)).deleteDrink(1);
    }
    @Test
    void handleViewAllDrinks() throws SQLException {
        Drink drink1 = new Drink(1, "Coffee", "Кофе", 2.5);
        Drink drink2 = new Drink(2, "Tea", "Чай", 1.5);
        when(drinkService.getAllDrinks()).thenReturn(List.of(drink1, drink2));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        drinkController.handleViewAllDrinks();
        String output = outContent.toString();
        assertEquals("Drink{id=1, nameEn='Coffee', nameOther='Кофе', price=2.5}\nDrink{id=2, nameEn='Tea', nameOther='Чай', price=1.5}\n", output);
    }
}