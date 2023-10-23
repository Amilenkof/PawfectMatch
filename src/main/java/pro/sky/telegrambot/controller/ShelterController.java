package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
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
    @Operation(
            summary = "Добавить приют",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Такой приют уже есть",
                            content = @Content(
                            )
                    )
            }
    )
    public ResponseEntity<Shelter> createShelter(@Parameter(description = "JSON приюта") @RequestBody ShelterDTOIN shelterDTOIN) {
        log.info("Вызван метод ShelterController.createShelter , получен shelterDTOIN ={}", shelterDTOIN);
        return ResponseEntity.ok(shelterService.createShelter(shelterDTOIN));
    }

    @GetMapping
    @Operation(
            summary = "Добавить приют",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Такой приют уже есть",
                            content = @Content(
                            )
                    )
            }
    )
    public ResponseEntity<Shelter> createShelter(@Parameter(description = "Описание приюта", example = "Приют для кошек") @RequestParam String description,
                                                 @Parameter(description = "Адрес", example = "ул. Лесная, д.1") @RequestParam String address,
                                                 @Parameter(description = "Время работы", example = "с 8:00 до 20:00") @RequestParam String timing,
                                                 @Parameter(description = "Контакты охраны", example = "89871234567") @RequestParam String contactsSecurity,
                                                 @Parameter(description = "Охрана") @RequestParam String safety,
                                                 @Parameter(description = "Тип животного", example = "cat") @RequestParam String animalType) {
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
