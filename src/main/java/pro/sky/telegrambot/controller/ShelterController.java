package pro.sky.telegrambot.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.service.ShelterService;

//todo Как класс будет закончен сделать swagger документацию на api

@RestController
@RequestMapping("/shelter")
@Slf4j
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }


    /**
     * Метод для создания приютов через web интерфейс
     * {@link   org.springframework.data.repository.CrudRepository.save()}
     * Params= Json Обьект SHELTER
     */
    @PostMapping
    public ResponseEntity<Shelter> createShelter(@RequestBody ShelterDTOIN shelterDTOIN) {
        log.info("Вызван метод ShelterController.createShelter , получен shelterDTOIN ={}", shelterDTOIN);
        return ResponseEntity.ok(shelterService.createShelter(shelterDTOIN));
    }

    @GetMapping
    public ResponseEntity<Shelter> createShelter(@RequestParam String description,
                                                 @RequestParam String address,
                                                 @RequestParam String timing,
                                                 @RequestParam String contactsSecurity,
                                                 @RequestParam String safety,
                                                 @RequestParam String animalType) {
        log.info("Вызван метод ShelterController.createShelter String description ={}," +
                 "String address ={}," +
                 "String timing ={}," +
                 "String contactsSecurity ={}," +
                 "String animalType ={}", description, address, timing, contactsSecurity, safety, animalType);

        return ResponseEntity.ok(shelterService.createShelter(description, address, timing, contactsSecurity, safety, animalType));
    }

    /**
     * Есть есть попытка создать приют с типом животных, который уже заведен ранее этим методом ловим ошибку и возвращаем статус 406- NOT_ACCEPTABLE
     */
    @ExceptionHandler(ShelterNotFoundException.class)
    public ResponseEntity<String> handleException(ShelterNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Указанные приют уже создан");
    }

}
