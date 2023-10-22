package pro.sky.telegrambot.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
import pro.sky.telegrambot.service.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<AnimalDTO> addAnimal(@RequestParam String type,
                                               @RequestParam String name,
                                               @RequestParam boolean isSick,
                                               @RequestParam boolean isLittle,
                                               @RequestParam boolean status) {
        return ResponseEntity.ok(animalService.addAnimal(type, name, isSick, isLittle, status));
    }

    @ExceptionHandler(ShelterNotFoundException.class)
    public ResponseEntity<String> handleException( ShelterNotFoundException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }
}
