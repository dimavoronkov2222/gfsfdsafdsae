package org.example.controller;
import org.example.model.Drink;
import org.example.service.DrinkService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
@WebMvcTest(DrinkController.class)
class DrinkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DrinkService drinkService;
    @Test
    void handleAddDrink() throws Exception {
        mockMvc.perform(post("/drinks/add")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Coffee\",\"drinkType\":\"Hot\",\"price\":2.5,\"nameOther\":\"Кофе\"}"))
                .andExpect(status().isOk());
        ArgumentCaptor<Drink> drinkCaptor = ArgumentCaptor.forClass(Drink.class);
        verify(drinkService, times(1)).addDrink(drinkCaptor.capture());
        Drink capturedDrink = drinkCaptor.getValue();
        assertEquals("Coffee", capturedDrink.getName());
        assertEquals("Hot", capturedDrink.getDrinkType());
        assertEquals(2.5, capturedDrink.getPrice());
        assertEquals("Кофе", capturedDrink.getNameOther());
    }
    @Test
    void handleUpdateDrink() throws Exception {
        mockMvc.perform(put("/drinks/update")
                        .contentType(APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Tea\",\"drinkType\":\"Hot\",\"price\":1.5,\"nameOther\":\"Чай\"}"))
                .andExpect(status().isOk());
        ArgumentCaptor<Drink> drinkCaptor = ArgumentCaptor.forClass(Drink.class);
        verify(drinkService, times(1)).updateDrink(drinkCaptor.capture());
        Drink capturedDrink = drinkCaptor.getValue();
        assertEquals(1, capturedDrink.getId());
        assertEquals("Tea", capturedDrink.getName());
        assertEquals("Hot", capturedDrink.getDrinkType());
        assertEquals(1.5, capturedDrink.getPrice());
        assertEquals("Чай", capturedDrink.getNameOther());
    }
    @Test
    void handleDeleteDrink() throws Exception {
        mockMvc.perform(delete("/drinks/delete/1"))
                .andExpect(status().isOk());

        verify(drinkService, times(1)).deleteDrink(1);
    }
    @Test
    void handleViewAllDrinks() throws Exception {
        Drink drink1 = new Drink(1, "Coffee", "Hot", 2.5);
        Drink drink2 = new Drink(2, "Tea", "Hot", 1.5);
        when(drinkService.getAllDrinks()).thenReturn(List.of(drink1, drink2));

        mockMvc.perform(get("/drinks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Coffee','drinkType':'Hot','price':2.5,'nameOther':'Кофе'},{'id':2,'name':'Tea','drinkType':'Hot','price':1.5,'nameOther':'Чай'}]"));
    }
}