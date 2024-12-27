package org.example.controller;
import org.example.model.Drink;
import org.example.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/drinks")
public class DrinkController {
    private final DrinkService drinkService;
    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }
    @PostMapping("/add")
    public String addDrink(@RequestBody Drink drink) {
        drinkService.addDrink(drink);
        return "Drink added successfully.";
    }
    @PutMapping("/update")
    public String updateDrink(@RequestBody Drink drink) {
        drinkService.updateDrink(drink);
        return "Drink updated successfully.";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteDrink(@PathVariable int id) {
        drinkService.deleteDrink(id);
        return "Drink deleted successfully.";
    }
    @GetMapping("/all")
    public List<Drink> getAllDrinks() {
        return drinkService.getAllDrinks();
    }
}
