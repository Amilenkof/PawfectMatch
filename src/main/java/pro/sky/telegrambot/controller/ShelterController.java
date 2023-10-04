package pro.sky.telegrambot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.service.ShelterService;

@RestController
@RequestMapping("/shelter")
@Slf4j
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity<Shelter> createShelter(@RequestBody ShelterDTOIN shelterDTOIN ){
        log.info("Вызван метод ShelterController.createShelter , получен shelterDTOIN ={}");
        return ResponseEntity.ok(shelterService.createShelter(shelterDTOIN));
}

}
